import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UniversitySystem uni = new UniversitySystem();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Smart University Management System");

        boolean loggedIn = false;
        UserAccount current = null;

        while (!loggedIn) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int opt = readInt(sc);

            if (opt == 1) {
                System.out.print("Username: "); String uname = sc.nextLine().trim();
                if (!uni.isUsernameValid(uname)) {
                    System.out.println("Invalid username. Must be at least 4 characters and contain no spaces.");
                    continue;
                }
                if (uni.isUsernameTaken(uname)) {
                    System.out.println("Username already exists. Pick another username.");
                    continue;
                }
                System.out.print("Password: "); String pass = sc.nextLine().trim();
                System.out.print("Role (student/teacher/admin): "); String role = sc.nextLine().trim().toLowerCase();
                if (role.equals("student")) {
                    int id = readUniqueId(sc, uni, "student");
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Blood Group: "); String blood = sc.nextLine();
                    Student s = new Student(id, name, email, blood);
                    boolean ok = uni.addStudentWithAccount(s, uname, pass);
                    if (ok) System.out.println("Student account created.");
                    else System.out.println("Failed to create account (username may exist).");
                } else if (role.equals("teacher")) {
                    int id = readUniqueId(sc, uni, "teacher");
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Blood Group: "); String blood = sc.nextLine();
                    System.out.print("Salary: "); double sal = readDouble(sc);
                    System.out.print("Department: "); String dept = sc.nextLine();
                    Teacher t = new Teacher(id, name, email, blood, sal, dept);
                    boolean ok = uni.addTeacherWithAccount(t, uname, pass);
                    if (ok) System.out.println("Teacher account created.");
                    else System.out.println("Failed to create account (username may exist).");
                } else if (role.equals("admin")) {
                    boolean ok = uni.addAdminAccount(uname, pass);
                    if (ok) System.out.println("Admin account created.");
                    else System.out.println("Failed to create account (username may exist).");
                } else {
                    System.out.println("Invalid role.");
                }
            } else if (opt == 2) {
                System.out.print("Username: "); String uname = sc.nextLine().trim();
                System.out.print("Password: "); String pass = sc.nextLine().trim();
                UserAccount acc = uni.getAccountByUsername(uname);
                if (acc != null && acc.getPassword().equals(pass)) {
                    loggedIn = true;
                    current = acc;
                    System.out.println("Login successful. Role: " + acc.getRole());
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else if (opt == 3) {
                System.out.println("Goodbye.");
                sc.close();
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        boolean running = true;
        while (running) {
            String role = current.getRole();
            if (role.equals("admin")) {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1 Add Student  2 Add Teacher  3 Add Course  4 Enroll Student");
                System.out.println("5 Update Student  6 Update Teacher  7 Update Course");
                System.out.println("8 Delete Student  9 Delete Teacher  10 Delete Course");
                System.out.println("11 Search Student  12 Search Teacher  13 Search Course");
                System.out.println("14 Generate Report  15 Logout");
                System.out.print("Choose: ");
                int c = readInt(sc);
                if (c == 1) {
                    int id = readUniqueId(sc, uni, "student");
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Blood: "); String blood = sc.nextLine();
                    System.out.print("Username for student: "); String uname = sc.nextLine();
                    if (!uni.isUsernameValid(uname)) { System.out.println("Invalid username. Must be at least 4 characters and contain no spaces."); continue; }
                    if (uni.isUsernameTaken(uname)) { System.out.println("Username exists."); continue; }
                    System.out.print("Password: "); String pass = sc.nextLine();
                    Student s = new Student(id, name, email, blood);
                    if (uni.addStudentWithAccount(s, uname, pass)) System.out.println("Student added.");
                    else System.out.println("Username exists.");
                } else if (c == 2) {
                    int id = readUniqueId(sc, uni, "teacher");
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Blood: "); String blood = sc.nextLine();
                    System.out.print("Salary: "); double sal = readDouble(sc);
                    System.out.print("Dept: "); String dept = sc.nextLine();
                    System.out.print("Username for teacher: "); String uname = sc.nextLine();
                    if (!uni.isUsernameValid(uname)) { System.out.println("Invalid username. Must be at least 4 characters and contain no spaces."); continue; }
                    if (uni.isUsernameTaken(uname)) { System.out.println("Username exists."); continue; }
                    System.out.print("Password: "); String pass = sc.nextLine();
                    Teacher t = new Teacher(id, name, email, blood, sal, dept);
                    if (uni.addTeacherWithAccount(t, uname, pass)) System.out.println("Teacher added.");
                    else System.out.println("Username exists.");
                } else if (c == 3) {
                    int cid = readUniqueId(sc, uni, "course");
                    System.out.print("Course Name: "); String cname = sc.nextLine();
                    System.out.print("Department: "); String cdept = sc.nextLine();
                    System.out.print("Credit: "); int credit = readInt(sc);
                    System.out.print("Teacher ID to assign: "); int tid = readInt(sc);
                    if (uni.getTeacherById(tid) == null) System.out.println("Teacher not found.");
                    else {
                        Course course = new Course(cid, cname, cdept, credit, tid);
                        uni.addCourse(course);
                        System.out.println("Course added.");
                    }
                } else if (c == 4) {
                    System.out.print("Student ID: "); int sid = readInt(sc);
                    System.out.print("Course ID: "); int cid = readInt(sc);
                    if (uni.enrollStudentInCourse(sid, cid)) System.out.println("Enrolled.");
                    else System.out.println("Student or Course not found.");
                } else if (c == 5) {
                    System.out.print("Student ID to update: "); int sid = readInt(sc);
                    Student s = uni.getStudentById(sid);
                    if (s == null) System.out.println("Not found.");
                    else {
                        System.out.print("New name: "); String nn = sc.nextLine();
                        System.out.print("New email: "); String ne = sc.nextLine();
                        System.out.print("New blood: "); String nb = sc.nextLine();
                        Student updated = new Student(sid, nn, ne, nb);
                        updated.getEnrolledCourseIds().addAll(s.getEnrolledCourseIds());
                        uni.deleteStudentById(sid);
                        uni.getStudents().add(updated);
                        uni.saveAll();
                        System.out.println("Student updated.");
                    }
                } else if (c == 6) {
                    System.out.print("Teacher ID to update: "); int tid = readInt(sc);
                    Teacher t = uni.getTeacherById(tid);
                    if (t == null) System.out.println("Not found.");
                    else {
                        System.out.print("New name: "); String nn = sc.nextLine();
                        System.out.print("New email: "); String ne = sc.nextLine();
                        System.out.print("New blood: "); String nb = sc.nextLine();
                        System.out.print("New salary: "); double ns = readDouble(sc);
                        System.out.print("New dept: "); String nd = sc.nextLine();
                        Teacher updated = new Teacher(tid, nn, ne, nb, ns, nd);
                        updated.getAssignedCourseIds().addAll(t.getAssignedCourseIds());
                        uni.deleteTeacherById(tid);
                        uni.getTeachers().add(updated);
                        uni.saveAll();
                        System.out.println("Teacher updated.");
                    }
                } else if (c == 7) {
                    System.out.print("Course ID to update: "); int cid = readInt(sc);
                    Course co = uni.getCourseById(cid);
                    if (co == null) System.out.println("Not found.");
                    else {
                        System.out.print("New name: "); String nn = sc.nextLine();
                        System.out.print("New dept: "); String nd = sc.nextLine();
                        System.out.print("New credit: "); int nc = readInt(sc);
                        Course updated = new Course(cid, nn, nd, nc, co.getTeacherId());
                        updated.getStudentIds().addAll(co.getStudentIds());
                        uni.deleteCourseById(cid);
                        uni.getCourses().add(updated);
                        uni.saveAll();
                        System.out.println("Course updated.");
                    }
                } else if (c == 8) {
                    System.out.print("Student ID to delete: "); int sid = readInt(sc);
                    if (!confirmAction(sc, "Delete student with ID " + sid + "? (y/n): ")) { System.out.println("Cancelled."); continue; }
                    if (uni.deleteStudentById(sid)) System.out.println("Deleted.");
                    else System.out.println("Not found.");
                } else if (c == 9) {
                    System.out.print("Teacher ID to delete: "); int tid = readInt(sc);
                    if (!confirmAction(sc, "Delete teacher with ID " + tid + "? (y/n): ")) { System.out.println("Cancelled."); continue; }
                    if (uni.deleteTeacherById(tid)) System.out.println("Deleted.");
                    else System.out.println("Not found.");
                } else if (c == 10) {
                    System.out.print("Course ID to delete: "); int cid = readInt(sc);
                    if (!confirmAction(sc, "Delete course with ID " + cid + "? (y/n): ")) { System.out.println("Cancelled."); continue; }
                    if (uni.deleteCourseById(cid)) System.out.println("Deleted.");
                    else System.out.println("Not found.");
                } else if (c == 11) {
                    System.out.print("Student ID to search: "); int sid = readInt(sc);
                    Student s = uni.getStudentById(sid);
                    if (s == null) System.out.println("Not found.");
                    else {
                        System.out.println(s.getDetails());
                        System.out.println("Enrolled Course IDs: " + s.getEnrolledCourseIds());
                    }
                } else if (c == 12) {
                    System.out.print("Teacher ID to search: "); int tid = readInt(sc);
                    Teacher t = uni.getTeacherById(tid);
                    if (t == null) System.out.println("Not found.");
                    else {
                        System.out.println(t.getDetails());
                        System.out.println("Assigned Course IDs: " + t.getAssignedCourseIds());
                    }
                } else if (c == 13) {
                    System.out.print("Course ID to search: "); int cid = readInt(sc);
                    Course co = uni.getCourseById(cid);
                    if (co == null) System.out.println("Not found.");
                    else {
                        System.out.println(co.getDetails());
                        System.out.println("Student IDs: " + co.getStudentIds());
                    }
                } else if (c == 14) {
                    uni.generateReport();
                } else if (c == 15) {
                    System.out.println("Logging out.");
                    running = false;
                } else {
                    System.out.println("Invalid.");
                }
            } else if (role.equals("teacher")) {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1 View My Details  2 View My Courses  3 Enroll Student in My Course  4 Logout");
                System.out.print("Choose: ");
                int c = readInt(sc);
                Teacher t = uni.getTeacherById(current.getLinkedId());
                if (c == 1) {
                    if (t == null) System.out.println("Teacher record not found.");
                    else System.out.println(t.getDetails());
                } else if (c == 2) {
                    if (t == null) System.out.println("Teacher record not found.");
                    else {
                        for (int cid : t.getAssignedCourseIds()) {
                            Course co = uni.getCourseById(cid);
                            if (co != null) System.out.println(co.getDetails());
                        }
                    }
                } else if (c == 3) {
                    if (t == null) { System.out.println("Teacher record not found."); continue; }
                    System.out.print("Enter your Course ID: "); int cid = readInt(sc);
                    if (!t.getAssignedCourseIds().contains(cid)) { System.out.println("You are not assigned to that course."); continue; }
                    System.out.print("Student ID to enroll: "); int sid = readInt(sc);
                    if (uni.enrollStudentInCourse(sid, cid)) System.out.println("Enrolled.");
                    else System.out.println("Student or Course not found.");
                } else if (c == 4) {
                    System.out.println("Logging out.");
                    running = false;
                } else {
                    System.out.println("Invalid.");
                }
            } else if (role.equals("student")) {
                System.out.println("\n--- Student Menu ---");
                System.out.println("1 View My Details  2 View My Courses  3 Logout");
                System.out.print("Choose: ");
                int c = readInt(sc);
                Student s = uni.getStudentById(current.getLinkedId());
                if (c == 1) {
                    if (s == null) System.out.println("Student record not found.");
                    else System.out.println(s.getDetails());
                } else if (c == 2) {
                    if (s == null) System.out.println("Student record not found.");
                    else {
                        for (int cid : s.getEnrolledCourseIds()) {
                            Course co = uni.getCourseById(cid);
                            if (co != null) System.out.println(co.getDetails());
                        }
                    }
                } else if (c == 3) {
                    System.out.println("Logging out.");
                    running = false;
                } else {
                    System.out.println("Invalid.");
                }
            } else {
                System.out.println("Unknown role. Logging out.");
                running = false;
            }
        }

        sc.close();
        System.out.println("Session ended.");
    }

    // helpers to safely read ints/doubles
    private static int readInt(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (Exception e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                return Double.parseDouble(line);
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private static int readUniqueId(Scanner sc, UniversitySystem uni, String entityType) {
        while (true) {
            if ("course".equalsIgnoreCase(entityType)) {
                System.out.print("Enter academic year for the course (1-4): ");
                int year;
                try {
                    String yline = sc.nextLine().trim();
                    year = Integer.parseInt(yline);
                    if (year < 1 || year > 4) { System.out.println("Year must be between 1 and 4."); continue; }
                } catch (Exception e) {
                    System.out.println("Enter a valid year (1-4).");
                    continue;
                }
                int blockMin = year * 100;
                int blockMax = year * 100 + 99;
                System.out.print("Enter Course ID in range " + blockMin + "-" + blockMax + ": ");
                String line = sc.nextLine().trim();
                int id;
                try {
                    id = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Try again.");
                    continue;
                }
                if (id < blockMin || id > blockMax) {
                    System.out.println("Course ID must be within " + blockMin + "-" + blockMax + " for year " + year + ".");
                    continue;
                }
                if (uni.isCourseIdTaken(id)) {
                    System.out.println("That Course ID is already in use. Choose a different one.");
                    continue;
                }
                return id;
            } else {
                System.out.print("Enter " + entityType + " ID (positive integer): ");
                String line = sc.nextLine().trim();
                int id;
                try {
                    id = Integer.parseInt(line);
                    if (id <= 0) {
                        System.out.println("ID must be a positive integer.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Try again.");
                    continue;
                }

                boolean inRange = false;
                boolean taken = false;
                if ("student".equalsIgnoreCase(entityType)) {
                    inRange = uni.isStudentIdInRange(id);
                    taken = uni.isStudentIdTaken(id);
                    if (!inRange) System.out.println("Student IDs must be between 1000 and 1999.");
                } else if ("teacher".equalsIgnoreCase(entityType)) {
                    inRange = uni.isTeacherIdInRange(id);
                    taken = uni.isTeacherIdTaken(id);
                    if (!inRange) System.out.println("Teacher IDs must be between 2000 and 2999.");
                } else {
                    System.out.println("Unknown entity type for ID validation.");
                    continue;
                }

                if (!inRange) continue;
                if (taken) {
                    System.out.println("That ID is already in use. Choose a different one.");
                    continue;
                }
                return id;
            }
        }
    }

    private static boolean confirmAction(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) return true;
            if (ans.equals("n") || ans.equals("no")) return false;
            System.out.println("Please answer 'y' or 'n'.");
        }
    }
}
