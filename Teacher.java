import java.util.ArrayList;

public class Teacher extends Person implements Evaluable {
    private double salary;
    private String department;
    private ArrayList<Integer> assignedCourseIds;

    public Teacher(int id, String name, String email, String bloodGroup, double salary, String department) {
        super(id, name, email, bloodGroup);
        this.salary = salary;
        this.department = department;
        assignedCourseIds = new ArrayList<>();
    }

    public void assignCourseId(int courseId) {
        if (!assignedCourseIds.contains(courseId)) assignedCourseIds.add(courseId);
    }

    public void removeCourseId(int courseId) {
        assignedCourseIds.remove(Integer.valueOf(courseId));
    }

    public ArrayList<Integer> getAssignedCourseIds() {
        return assignedCourseIds;
    }

    public double calculateSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getDetails() {
        return "Teacher: " + getName() + " | Dept: " + department + " | Blood Group: " + getBloodGroup() + " | ID: " + getId();
    }
}
