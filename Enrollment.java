public class Enrollment {
    private Student student;
    private Course course;
    private double grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public void updateGrade(double g) { grade = g; }
}
