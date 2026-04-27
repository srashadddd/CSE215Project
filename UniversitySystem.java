import java.util.ArrayList;

public class UniversitySystem {
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<Course> courses;

    public UniversitySystem() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addStudent(Student s) { students.add(s); }
    public void updateStudent(Student s) {}
    public void deleteStudent(Student s) { students.remove(s); }
    public void searchStudent(int id) {}

    public void addTeacher(Teacher t) { teachers.add(t); }
    public void updateTeacher(Teacher t) {}

    public void addCourse(Course c) { courses.add(c); }
    public void updateCourse(Course c) {}

    public void enrollStudent(Student s, Course c) { c.addStudent(s); }
    public void markAttendance(Student s, Course c) {}

    public void generateReport() {
        System.out.println("=== Students Report ===");
        for (Student s : students) {
            System.out.println(s.getDetails());
        }
        System.out.println("=== Teachers Report ===");
        for (Teacher t : teachers) {
            System.out.println(t.getDetails());
        }
        System.out.println("=== Courses Report ===");
        for (Course c : courses) {
            System.out.println(c.getDetails());
        }
    }

    public void sortStudents() {}
    public void sortCourses() {}
    public void saveData() {}
    public void loadData() {}
}
