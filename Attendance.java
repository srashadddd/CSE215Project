public class Attendance {
    private Student student;
    private Course course;
    private int totalClasses;
    private int attendedClasses;

    public Attendance(Student student, Course course, int totalClasses) {
        this.setStudent(student);
        this.setCourse(course);
        this.totalClasses = totalClasses;
    }

    public void markAttendance() {
        if (attendedClasses < totalClasses) {
            attendedClasses++;
        }
    }
    
    public double attendancePercentage() {
    	if (totalClasses == 0) return 0;
    	return (attendedClasses * 100.0) / totalClasses;
    }

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
