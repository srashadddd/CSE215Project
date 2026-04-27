import java.util.ArrayList;

public class Teacher extends Person implements Evaluable {
    private double salary;
    private String department;
    private ArrayList<Course> assignedCourses;

    public Teacher(int id, String name, String email, String bloodGroup, double salary, String department) {
        super(id, name, email, bloodGroup);
        this.salary = salary;
        this.department = department;
        assignedCourses = new ArrayList<>();
    }

    public void assignCourse(Course c) { assignedCourses.add(c); }
    public void removeCourse(Course c) { assignedCourses.remove(c); }

    public double calculateSalary() { return salary; }

    public String getDetails() {
        return "Teacher: " + getName() + " Dept: " + department + " Blood Group: " + getBloodGroup();
    }
}
