import java.util.ArrayList;

public class UniversitySystem {
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<Course> courses;
    private ArrayList<UserAccount> accounts;
    private ArrayList<Attendance> attendances;
    private FileManager fm;

    private static final int STUDENT_ID_MIN = 1000;
    private static final int STUDENT_ID_MAX = 1999;
    private static final int TEACHER_ID_MIN = 2000;
    private static final int TEACHER_ID_MAX = 2999;

    private static final int COURSE_ID_MIN = 100;
    private static final int COURSE_ID_MAX = 499;

    private static final int USERNAME_MIN_LENGTH = 4;

    public UniversitySystem() {
        fm = new FileManager();
        students = fm.loadStudents();
        teachers = fm.loadTeachers();
        courses = fm.loadCourses();
        accounts = fm.loadAccounts();

        for (Course c : courses) {
            int tid = c.getTeacherId();
            Teacher t = getTeacherById(tid);
            if (t != null) {
                if (!t.getAssignedCourseIds().contains(c.getCourseId())) t.assignCourseId(c.getCourseId());
            }
            for (int sid : c.getStudentIds()) {
                Student s = getStudentById(sid);
                if (s != null) {
                    if (!s.getEnrolledCourseIds().contains(c.getCourseId())) s.enrollCourseId(c.getCourseId());
                }
            }
        }
    }

    public boolean isStudentIdTaken(int id) { return getStudentById(id) != null; }
    public boolean isTeacherIdTaken(int id) { return getTeacherById(id) != null; }
    public boolean isCourseIdTaken(int id) { return getCourseById(id) != null; }
    public boolean isUsernameTaken(String username) { return getAccountByUsername(username) != null; }

    public boolean isStudentIdInRange(int id) { return id >= STUDENT_ID_MIN && id <= STUDENT_ID_MAX; }
    public boolean isTeacherIdInRange(int id) { return id >= TEACHER_ID_MIN && id <= TEACHER_ID_MAX; }
    public boolean isCourseIdInRange(int id) { return id >= COURSE_ID_MIN && id <= COURSE_ID_MAX; }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        if (username.length() < USERNAME_MIN_LENGTH) return false;
        if (username.contains(" ")) return false;
        return true;
    }

    public boolean addStudentWithAccount(Student s, String username, String password) {
        if (getAccountByUsername(username) != null) return false;
        students.add(s);
        accounts.add(new UserAccount(username, password, "student", s.getId()));
        saveAll();
        return true;
    }

    public boolean addTeacherWithAccount(Teacher t, String username, String password) {
        if (getAccountByUsername(username) != null) return false;
        teachers.add(t);
        accounts.add(new UserAccount(username, password, "teacher", t.getId()));
        saveAll();
        return true;
    }

    public boolean addAdminAccount(String username, String password) {
        if (getAccountByUsername(username) != null) return false;
        accounts.add(new UserAccount(username, password, "admin", -1));
        saveAll();
        return true;
    }

    public ArrayList<Student> getStudents() { return students; }
    public ArrayList<Teacher> getTeachers() { return teachers; }
    public ArrayList<Course> getCourses() { return courses; }
    public ArrayList<UserAccount> getAccounts() { return accounts; }

    public Student getStudentById(int id) {
        for (Student s : students) if (s.getId() == id) return s;
        return null;
    }

    public Teacher getTeacherById(int id) {
        for (Teacher t : teachers) if (t.getId() == id) return t;
        return null;
    }

    public Course getCourseById(int id) {
        for (Course c : courses) if (c.getCourseId() == id) return c;
        return null;
    }

    public UserAccount getAccountByUsername(String uname) {
        for (UserAccount a : accounts) if (a.getUsername().equals(uname)) return a;
        return null;
    }

    public void addCourse(Course c) {
        courses.add(c);
        Teacher t = getTeacherById(c.getTeacherId());
        if (t != null) t.assignCourseId(c.getCourseId());
        saveAll();
    }

    public boolean enrollStudentInCourse(int studentId, int courseId) {
        Student s = getStudentById(studentId);
        Course c = getCourseById(courseId);
        if (s == null || c == null) return false;
        if (!s.getEnrolledCourseIds().contains(courseId)) {
            s.enrollCourseId(courseId);
        }
        if (!c.getStudentIds().contains(studentId)) {
            c.addStudentId(studentId);
        }
        saveAll();
        return true;
    }

    public boolean unenrollStudentFromCourse(int studentId, int courseId) {
        Student s = getStudentById(studentId);
        Course c = getCourseById(courseId);
        if (s == null || c == null) return false;
        s.dropCourseId(courseId);
        c.removeStudentId(studentId);
        saveAll();
        return true;
    }

    public boolean deleteStudentById(int id) {
        Student s = getStudentById(id);
        if (s == null) return false;
        for (Course c : courses) c.removeStudentId(id);
        students.remove(s);
        UserAccount accToRemove = null;
        for (UserAccount a : accounts) if (a.getRole().equals("student") && a.getLinkedId() == id) { accToRemove = a; break; }
        if (accToRemove != null) accounts.remove(accToRemove);
        saveAll();
        return true;
    }

    public boolean deleteTeacherById(int id) {
        Teacher t = getTeacherById(id);
        if (t == null) return false;

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getTeacherId() == id) {
                Course replacement = new Course(c.getCourseId(), c.getCourseName(), c.getDepartment(), c.getCredit(), -1);
                for (int sid : c.getStudentIds()) replacement.addStudentId(sid);
                courses.set(i, replacement);
            }
        }
        teachers.remove(t);
        UserAccount accToRemove = null;
        for (UserAccount a : accounts) if (a.getRole().equals("teacher") && a.getLinkedId() == id) { accToRemove = a; break; }
        if (accToRemove != null) accounts.remove(accToRemove);
        saveAll();
        return true;
    }

    public boolean deleteCourseById(int id) {
        Course c = getCourseById(id);
        if (c == null) return false;
        for (Student s : students) s.dropCourseId(id);
        for (Teacher t : teachers) t.removeCourseId(id);
        courses.remove(c);
        saveAll();
        return true;
    }
    public boolean updateStudentGpa(int studentId, double gpa) {
        Student s = getStudentById(studentId);
        if (s == null) return false;

        s.setGpa(gpa);
        saveAll();
        return true;
    }
    
    public void saveAll() {
        fm.saveStudents(students);
        fm.saveTeachers(teachers);
        fm.saveCourses(courses);
        fm.saveAccounts(accounts);
    }

    public void generateReport() {
        System.out.println("\n=== Students Report ===");
        for (Student s : students) System.out.println(s.getDetails());
        System.out.println("\n=== Teachers Report ===");
        for (Teacher t : teachers) System.out.println(t.getDetails());
        System.out.println("\n=== Courses Report ===");
        for (Course c : courses) System.out.println(c.getDetails());
    }

	public ArrayList<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(ArrayList<Attendance> attendances) {
		this.attendances = attendances;
	}
}
