// Represents a student-course enrollment with a grade
public class Enrollment {
    private Student student;
    private Course course;
    private double grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = -1; // -1 = not graded yet
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getGrade() { return grade; }

    public void updateGrade(double g) {
        if (g >= 0 && g <= 100) this.grade = g;
        else System.out.println("Invalid grade. Must be 0-100.");
    }

    // Format for file save: studentId,courseId,grade
    public String toFileString() {
        return student.getId() + "," + course.getCourseId() + "," + grade;
    }

    @Override
    public String toString() {
        return String.format("Enrollment: %s -> %s | Grade: %s",
                student.getName(), course.getCourseName(),
                (grade < 0 ? "Not graded" : String.valueOf(grade)));
    }
}
