public class GPAService {
	public double calculate(Student s) {
	    if (s == null) return 0;
	    return s.getGpa();
	}
}
