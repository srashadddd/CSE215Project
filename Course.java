import java.util.ArrayList;

public class Course {
    private int courseId;
    private String courseName;
    private String department;
    private int credit;
    private int teacherId;
    private ArrayList<Integer> studentIds;

    public Course(int courseId, String courseName, String department, int credit, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
        this.credit = credit;
        this.teacherId = teacherId;
        studentIds = new ArrayList<>();
    }

    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getDepartment() { return department; }
    public int getCredit() { return credit; }
    public int getTeacherId() { return teacherId; }

    public void addStudentId(int sid) {
        if (!studentIds.contains(sid)) studentIds.add(sid);
    }

    public void removeStudentId(int sid) {
        studentIds.remove(Integer.valueOf(sid));
    }

    public ArrayList<Integer> getStudentIds() {
        return studentIds;
    }

    public String getDetails() {
        return "Course: " + courseName + " | Dept: " + department + " | ID: " + courseId + " | Credit: " + credit + " | TeacherID: " + teacherId;
    }
}
