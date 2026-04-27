import java.util.*;

// Main controller — manages all operations and the console menu
public class UniversitySystem {
    private ArrayList<Student>    students    = new ArrayList<>();
    private ArrayList<Teacher>    teachers    = new ArrayList<>();
    private ArrayList<Course>     courses     = new ArrayList<>();
    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private ArrayList<Attendance> attendances = new ArrayList<>();

    private FileManager fileManager = new FileManager();
    private GPAService  gpaService  = new GPAService();
    private Scanner     sc          = new Scanner(System.in);

    private int nextStudentId = 1;
    private int nextTeacherId = 1;
    private int nextCourseId  = 1;

    // ========== STARTUP ==========

    public void start() {
        loadData();
        // Sync ID counters so new IDs don't clash with loaded data
        for (Student s : students) if (s.getId() >= nextStudentId) nextStudentId = s.getId() + 1;
        for (Teacher t : teachers) if (t.getId() >= nextTeacherId) nextTeacherId = t.getId() + 1;
        for (Course c  : courses)  if (c.getCourseId() >= nextCourseId) nextCourseId = c.getCourseId() + 1;

        System.out.println("=== Smart University Management System ===");
        mainMenu();
    }

    // ========== MENUS ==========

    private void mainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Students");
            System.out.println("2. Teachers");
            System.out.println("3. Courses");
            System.out.println("4. Enrollment");
            System.out.println("5. Attendance");
            System.out.println("6. Reports");
            System.out.println("7. Save & Exit");
            System.out.print("Choose: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> teacherMenu();
                case 3 -> courseMenu();
                case 4 -> enrollmentMenu();
                case 5 -> attendanceMenu();
                case 6 -> reportMenu();
                case 7 -> { saveData(); System.out.println("Data saved. Goodbye!"); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void studentMenu() {
        System.out.println("\n--- STUDENTS ---");
        System.out.println("1. Add  2. Update  3. Delete  4. Search  5. List All  6. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> addStudent();
            case 2 -> updateStudent();
            case 3 -> deleteStudent();
            case 4 -> searchStudent();
            case 5 -> listAll(students);
            case 6 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    private void teacherMenu() {
        System.out.println("\n--- TEACHERS ---");
        System.out.println("1. Add  2. Update  3. List All  4. Assign Course  5. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> addTeacher();
            case 2 -> updateTeacher();
            case 3 -> listAll(teachers);
            case 4 -> assignTeacherToCourse();
            case 5 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    private void courseMenu() {
        System.out.println("\n--- COURSES ---");
        System.out.println("1. Add  2. Update  3. List All  4. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> addCourse();
            case 2 -> updateCourse();
            case 3 -> listAll(courses);
            case 4 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    private void enrollmentMenu() {
        System.out.println("\n--- ENROLLMENT ---");
        System.out.println("1. Enroll Student  2. Drop Student  3. Update Grade  4. List  5. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> enrollStudent();
            case 2 -> dropStudent();
            case 3 -> updateGrade();
            case 4 -> listAll(enrollments);
            case 5 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    private void attendanceMenu() {
        System.out.println("\n--- ATTENDANCE ---");
        System.out.println("1. Add Attendance Record  2. Mark Attendance  3. List  4. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> addAttendanceRecord();
            case 2 -> markAttendance();
            case 3 -> listAll(attendances);
            case 4 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    private void reportMenu() {
        System.out.println("\n--- REPORTS ---");
        System.out.println("1. Student Report  2. Teacher Report  3. Course Report");
        System.out.println("4. Calculate GPA  5. Sort Students by GPA  6. Sort Courses by Name  7. Back");
        System.out.print("Choose: ");
        switch (readInt()) {
            case 1 -> generateStudentReport();
            case 2 -> generateTeacherReport();
            case 3 -> generateCourseReport();
            case 4 -> calculateGPA();
            case 5 -> sortStudentsByGPA();
            case 6 -> sortCoursesByName();
            case 7 -> {}
            default -> System.out.println("Invalid.");
        }
    }

    // ========== STUDENT OPS ==========

    private void addStudent() {
        System.out.print("Name: ");          String name  = sc.nextLine().trim();
        System.out.print("Email: ");         String email = sc.nextLine().trim();
        System.out.print("Department: ");    String dept  = sc.nextLine().trim();
        students.add(new Student(nextStudentId++, name, email, dept));
        System.out.println("Student added.");
    }

    private void updateStudent() {
        Student s = findStudent();
        if (s == null) return;
        System.out.print("New name [" + s.getName() + "]: ");   String name  = sc.nextLine().trim();
        System.out.print("New email [" + s.getEmail() + "]: "); String email = sc.nextLine().trim();
        System.out.print("New dept [" + s.getDepartment() + "]: "); String dept = sc.nextLine().trim();
        s.updateStudent(
            name.isEmpty()  ? s.getName()       : name,
            email.isEmpty() ? s.getEmail()      : email,
            dept.isEmpty()  ? s.getDepartment() : dept
        );
        System.out.println("Student updated.");
    }

    private void deleteStudent() {
        Student s = findStudent();
        if (s == null) return;
        // Remove from courses and enrollments too
        for (Course c : s.getEnrolledCourses()) c.removeStudent(s);
        enrollments.removeIf(e -> e.getStudent().getId() == s.getId());
        attendances.removeIf(a -> a.getStudent().getId() == s.getId());
        students.remove(s);
        System.out.println("Student deleted.");
    }

    private void searchStudent() {
        System.out.print("Enter name or ID: "); String q = sc.nextLine().trim().toLowerCase();
        for (Student s : students) {
            if (String.valueOf(s.getId()).equals(q) || s.getName().toLowerCase().contains(q)) {
                System.out.println(s.getDetails());
            }
        }
    }

    // ========== TEACHER OPS ==========

    private void addTeacher() {
        System.out.print("Name: ");       String name   = sc.nextLine().trim();
        System.out.print("Email: ");      String email  = sc.nextLine().trim();
        System.out.print("Department: "); String dept   = sc.nextLine().trim();
        System.out.print("Salary: ");     double salary = readDouble();
        teachers.add(new Teacher(nextTeacherId++, name, email, dept, salary));
        System.out.println("Teacher added.");
    }

    private void updateTeacher() {
        Teacher t = findTeacher();
        if (t == null) return;
        System.out.print("New name [" + t.getName() + "]: ");       String name   = sc.nextLine().trim();
        System.out.print("New email [" + t.getEmail() + "]: ");     String email  = sc.nextLine().trim();
        System.out.print("New dept [" + t.getDepartment() + "]: "); String dept   = sc.nextLine().trim();
        System.out.print("New salary [" + t.getSalary() + "]: ");   String salStr = sc.nextLine().trim();
        t.updateTeacher(
            name.isEmpty()   ? t.getName()       : name,
            email.isEmpty()  ? t.getEmail()      : email,
            dept.isEmpty()   ? t.getDepartment() : dept,
            salStr.isEmpty() ? t.getSalary()     : Double.parseDouble(salStr)
        );
        System.out.println("Teacher updated.");
    }

    private void assignTeacherToCourse() {
        Teacher t = findTeacher();
        if (t == null) return;
        Course c = findCourse();
        if (c == null) return;
        t.assignCourse(c);
        System.out.println(t.getName() + " assigned to " + c.getCourseName());
    }

    // ========== COURSE OPS ==========

    private void addCourse() {
        System.out.print("Course name: "); String name   = sc.nextLine().trim();
        System.out.print("Credits: ");     int credit    = readInt();
        courses.add(new Course(nextCourseId++, name, credit));
        System.out.println("Course added.");
    }

    private void updateCourse() {
        Course c = findCourse();
        if (c == null) return;
        System.out.print("New name [" + c.getCourseName() + "]: "); String name = sc.nextLine().trim();
        System.out.print("New credits [" + c.getCredit() + "]: ");  String cr   = sc.nextLine().trim();
        c.updateCourse(
            name.isEmpty() ? c.getCourseName() : name,
            cr.isEmpty()   ? c.getCredit()     : Integer.parseInt(cr)
        );
        System.out.println("Course updated.");
    }

    // ========== ENROLLMENT OPS ==========

    private void enrollStudent() {
        Student s = findStudent();
        if (s == null) return;
        Course c = findCourse();
        if (c == null) return;
        // Check not already enrolled
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == s.getId() && e.getCourse().getCourseId() == c.getCourseId()) {
                System.out.println("Already enrolled."); return;
            }
        }
        s.enrollCourse(c);
        enrollments.add(new Enrollment(s, c));
        System.out.println(s.getName() + " enrolled in " + c.getCourseName());
    }

    private void dropStudent() {
        Student s = findStudent();
        if (s == null) return;
        Course c = findCourse();
        if (c == null) return;
        s.dropCourse(c);
        enrollments.removeIf(e -> e.getStudent().getId() == s.getId()
                               && e.getCourse().getCourseId() == c.getCourseId());
        System.out.println("Dropped.");
    }

    private void updateGrade() {
        Student s = findStudent();
        if (s == null) return;
        Course c = findCourse();
        if (c == null) return;
        System.out.print("Grade (0-100): "); double g = readDouble();
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == s.getId() && e.getCourse().getCourseId() == c.getCourseId()) {
                e.updateGrade(g);
                System.out.println("Grade updated.");
                return;
            }
        }
        System.out.println("Enrollment not found.");
    }

    // ========== ATTENDANCE OPS ==========

    private void addAttendanceRecord() {
        Student s = findStudent();
        if (s == null) return;
        Course c = findCourse();
        if (c == null) return;
        System.out.print("Total classes in semester: "); int total = readInt();
        attendances.add(new Attendance(s, c, total));
        System.out.println("Attendance record created.");
    }

    private void markAttendance() {
        Student s = findStudent();
        if (s == null) return;
        Course c = findCourse();
        if (c == null) return;
        for (Attendance a : attendances) {
            if (a.getStudent().getId() == s.getId() && a.getCourse().getCourseId() == c.getCourseId()) {
                a.markAttendance();
                System.out.println("Marked. " + a);
                return;
            }
        }
        System.out.println("Attendance record not found. Add one first.");
    }

    // ========== REPORTS ==========

    private void generateStudentReport() {
        System.out.println("\n===== STUDENT REPORT =====");
        for (Student s : students) {
            System.out.println(s.getDetails());
            // Print attendance summary for this student
            for (Attendance a : attendances) {
                if (a.getStudent().getId() == s.getId())
                    System.out.printf("  Attendance [%s]: %.1f%%%n",
                            a.getCourse().getCourseName(), a.attendancePercentage());
            }
        }
    }

    private void generateTeacherReport() {
        System.out.println("\n===== TEACHER REPORT =====");
        for (Teacher t : teachers) System.out.println(t.getDetails());
    }

    private void generateCourseReport() {
        System.out.println("\n===== COURSE REPORT =====");
        for (Course c : courses) {
            System.out.println(c.getDetails());
            for (Student s : c.getStudents()) {
                System.out.println("  -> " + s.getName() + " (GPA: " + s.getGpa() + ")");
            }
        }
    }

    private void calculateGPA() {
        Student s = findStudent();
        if (s == null) return;
        double gpa = gpaService.calculate(s, enrollments);
        System.out.printf("GPA for %s: %.2f%n", s.getName(), gpa);
    }

    private void sortStudentsByGPA() {
        // Recalculate before sorting
        for (Student s : students) gpaService.calculate(s, enrollments);
        students.sort((a, b) -> Double.compare(b.getGpa(), a.getGpa()));
        System.out.println("\n--- Students sorted by GPA (desc) ---");
        listAll(students);
    }

    private void sortCoursesByName() {
        courses.sort(Comparator.comparing(Course::getCourseName));
        System.out.println("\n--- Courses sorted by name ---");
        listAll(courses);
    }

    // ========== FILE OPS ==========

    private void saveData() {
        fileManager.saveStudents(students);
        fileManager.saveTeachers(teachers);
        fileManager.saveCourses(courses);
        fileManager.saveEnrollments(enrollments);
        fileManager.saveAttendance(attendances);
    }

    private void loadData() {
        students    = fileManager.loadStudents();
        teachers    = fileManager.loadTeachers();
        courses     = fileManager.loadCourses(teachers);
        enrollments = fileManager.loadEnrollments(students, courses);
        attendances = fileManager.loadAttendance(students, courses);
        System.out.println("Data loaded: " + students.size() + " students, " +
                teachers.size() + " teachers, " + courses.size() + " courses.");
    }

    // ========== HELPER FINDERS ==========

    private Student findStudent() {
        System.out.print("Enter student ID: ");
        int id = readInt();
        for (Student s : students) if (s.getId() == id) return s;
        System.out.println("Student not found.");
        return null;
    }

    private Teacher findTeacher() {
        System.out.print("Enter teacher ID: ");
        int id = readInt();
        for (Teacher t : teachers) if (t.getId() == id) return t;
        System.out.println("Teacher not found.");
        return null;
    }

    private Course findCourse() {
        System.out.print("Enter course ID: ");
        int id = readInt();
        for (Course c : courses) if (c.getCourseId() == id) return c;
        System.out.println("Course not found.");
        return null;
    }

    private <T> void listAll(ArrayList<T> list) {
        if (list.isEmpty()) { System.out.println("None found."); return; }
        for (T item : list) System.out.println(item.toString());
    }

    // Safe int/double read with try-catch
    private int readInt() {
        try {
            int val = Integer.parseInt(sc.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, defaulting to 0.");
            return 0;
        }
    }

    private double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, defaulting to 0.");
            return 0;
        }
    }

    // ========== ENTRY POINT ==========

    public static void main(String[] args) {
        new UniversitySystem().start();
    }
}
