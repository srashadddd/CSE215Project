public class Attendance {
    private Student student;
    private Course course;
    private int totalClasses;
    private int attendedClasses;

    public Attendance(Student student, Course course, int totalClasses) {
        this.student = student;
        this.course = course;
        this.totalClasses = totalClasses;
    }

    public void markAttendance() { attendedClasses++; }
    public double attendancePercentage() { return (attendedClasses * 100.0) / totalClasses; }
}
