import java.util.ArrayList;

public class Student extends Person implements Evaluable {
    private double gpa;
    private ArrayList<Course> enrolledCourses;

    public Student(int id, String name, String email, String bloodGroup) {
        super(id, name, email, bloodGroup);
        enrolledCourses = new ArrayList<>();
    }

    public void enrollCourse(Course c) { enrolledCourses.add(c); }
    public void dropCourse(Course c) { enrolledCourses.remove(c); }

    public double calculateGPA() { return gpa; }

    public String getDetails() {
        return "Student: " + getName() + " Email: " + getEmail() + " Blood Group: " + getBloodGroup();
    }
}
