import java.util.ArrayList;

// Inheritance: Teacher extends Person, implements Payable
public class Teacher extends Person implements Payable {
    private double salary;
    private String department;
    private ArrayList<Course> assignedCourses;

    public Teacher(int id, String name, String email, String department, double salary) {
        super(id, name, email);
        this.department = department;
        this.salary = salary;
        this.assignedCourses = new ArrayList<>();
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public ArrayList<Course> getAssignedCourses() { return assignedCourses; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String dept) { this.department = dept; }

    public void assignCourse(Course c) {
        if (!assignedCourses.contains(c)) {
            assignedCourses.add(c);
            c.setTeacher(this);
        }
    }

    public void removeCourse(Course c) {
        assignedCourses.remove(c);
        if (c.getTeacher() == this) c.setTeacher(null);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    public void updateTeacher(String name, String email, String dept, double salary) {
        setName(name);
        setEmail(email);
        this.department = dept;
        this.salary = salary;
    }

    @Override
    public String getDetails() {
        return String.format("Teacher[%d] %s | Email: %s | Dept: %s | Salary: %.2f | Courses: %d",
                getId(), getName(), getEmail(), department, salary, assignedCourses.size());
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + getEmail() + "," + department + "," + salary;
    }
}
