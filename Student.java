import java.util.ArrayList;

// Inheritance: Student extends Person, implements Evaluable
public class Student extends Person implements Evaluable {
    private double gpa;
    private String department;
    private ArrayList<Course> enrolledCourses;

    public Student(int id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
        this.gpa = 0.0;
        this.enrolledCourses = new ArrayList<>();
    }

    public double getGpa() { return gpa; }
    public String getDepartment() { return department; }
    public ArrayList<Course> getEnrolledCourses() { return enrolledCourses; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setDepartment(String dept) { this.department = dept; }

    public void enrollCourse(Course c) {
        if (!enrolledCourses.contains(c)) {
            enrolledCourses.add(c);
            c.addStudent(this);
        }
    }

    public void dropCourse(Course c) {
        enrolledCourses.remove(c);
        c.removeStudent(this);
    }

    // GPA is set externally via GPAService; this returns current value
    @Override
    public double calculateGPA() {
        return gpa;
    }

    public void updateStudent(String name, String email, String dept) {
        setName(name);
        setEmail(email);
        this.department = dept;
    }

    @Override
    public String getDetails() {
        return String.format("Student[%d] %s | Email: %s | Dept: %s | GPA: %.2f | Courses: %d",
                getId(), getName(), getEmail(), department, gpa, enrolledCourses.size());
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + getEmail() + "," + department + "," + gpa;
    }
}
