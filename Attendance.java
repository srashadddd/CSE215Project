// Tracks attendance for a student in a course
public class Attendance {
    private Student student;
    private Course course;
    private int totalClasses;
    private int attendedClasses;

    public Attendance(Student student, Course course, int totalClasses) {
        this.student = student;
        this.course = course;
        this.totalClasses = totalClasses;
        this.attendedClasses = 0;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public int getTotalClasses() { return totalClasses; }
    public int getAttendedClasses() { return attendedClasses; }

    public void markAttendance() {
        if (attendedClasses < totalClasses) attendedClasses++;
        else System.out.println("All classes already marked.");
    }

    public double attendancePercentage() {
        if (totalClasses == 0) return 0;
        return (attendedClasses * 100.0) / totalClasses;
    }

    // Format for file: studentId,courseId,total,attended
    public String toFileString() {
        return student.getId() + "," + course.getCourseId() + "," + totalClasses + "," + attendedClasses;
    }

    @Override
    public String toString() {
        return String.format("Attendance: %s in %s | %d/%d (%.1f%%)",
                student.getName(), course.getCourseName(),
                attendedClasses, totalClasses, attendancePercentage());
    }
}
