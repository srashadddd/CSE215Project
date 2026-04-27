import java.util.ArrayList;

// Service class — calculates GPA from enrollments
public class GPAService {

    // Grade to GPA point conversion (standard 4.0 scale)
    private static double gradeToPoint(double grade) {
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }

    // Weighted GPA using course credits
    public double calculate(Student student, ArrayList<Enrollment> enrollments) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId() && e.getGrade() >= 0) {
                int credit = e.getCourse().getCredit();
                totalPoints += gradeToPoint(e.getGrade()) * credit;
                totalCredits += credit;
            }
        }

        if (totalCredits == 0) return 0.0;
        double gpa = totalPoints / totalCredits;
        student.setGpa(gpa); // update student's GPA field
        return gpa;
    }
}
