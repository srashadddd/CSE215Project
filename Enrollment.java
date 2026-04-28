public class Enrollment {
    private Student student;
    private Course course;
    private double grade;

    public Enrollment(Student student, Course course) {
        this.setStudent(student);
        this.setCourse(course);
    }

    public void updateGrade(double g) { setGrade(g); }

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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
}
