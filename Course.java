import java.util.ArrayList;

public class Course {
    private int courseId;
    private String courseName;
    private String department;
    private int credit;
    private Teacher teacher;
    private ArrayList<Student> students;

    public Course(int courseId, String courseName, String department, int credit, Teacher teacher) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
        this.credit = credit;
        this.teacher = teacher;
        students = new ArrayList<>();
    }

    public void addStudent(Student s) { students.add(s); }
    public void removeStudent(Student s) { students.remove(s); }

    public String getDetails() {
        return "Course: " + courseName + " Dept: " + department;
    }
}
