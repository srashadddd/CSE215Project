import java.util.ArrayList;

public class Student extends Person implements Evaluable {
    private double gpa;
    private ArrayList<Integer> enrolledCourseIds;

    public Student(int id, String name, String email, String bloodGroup) {
        super(id, name, email, bloodGroup);
        enrolledCourseIds = new ArrayList<>();
    }

    public void enrollCourseId(int courseId) {
        if (!enrolledCourseIds.contains(courseId)) enrolledCourseIds.add(courseId);
    }

    public void dropCourseId(int courseId) {
        enrolledCourseIds.remove(Integer.valueOf(courseId));
    }

    public ArrayList<Integer> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public double calculateGPA() {
        return gpa;
    }

    public String getDetails() {
        return "Student: " + getName() + " | Email: " + getEmail() + " | Blood Group: " + getBloodGroup() + " | ID: " + getId();
    }
}
