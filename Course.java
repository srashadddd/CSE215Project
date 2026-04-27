import java.util.ArrayList;

public class Course {
    private int courseId;
    private String courseName;
    private int credit;
    private Teacher teacher;
    private ArrayList<Student> students;

    public Course(int courseId, String courseName, int credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.students = new ArrayList<>();
    }

    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredit() { return credit; }
    public Teacher getTeacher() { return teacher; }
    public ArrayList<Student> getStudents() { return students; }

    public void setCourseName(String name) { this.courseName = name; }
    public void setCredit(int credit) { this.credit = credit; }
    public void setTeacher(Teacher t) { this.teacher = t; }

    public void addStudent(Student s) {
        if (!students.contains(s)) students.add(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    // Update course fields interactively
    public void updateCourse(String newName, int newCredit) {
        this.courseName = newName;
        this.credit = newCredit;
    }

    public String getDetails() {
        String teacherName = (teacher != null) ? teacher.getName() : "None";
        return String.format("Course[%d] %s | Credits: %d | Teacher: %s | Students: %d",
                courseId, courseName, credit, teacherName, students.size());
    }

    @Override
    public String toString() {
        return courseId + "," + courseName + "," + credit + "," +
               (teacher != null ? teacher.getId() : -1);
    }
}
