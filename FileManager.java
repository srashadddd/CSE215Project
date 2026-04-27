import java.io.*;
import java.util.ArrayList;

// Handles all file I/O — saves/loads students, teachers, courses, enrollments, attendance
public class FileManager {
    private static final String STUDENTS_FILE = "students.txt";
    private static final String TEACHERS_FILE = "teachers.txt";
    private static final String COURSES_FILE  = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";
    private static final String ATTENDANCE_FILE  = "attendance.txt";

    // ---- SAVE ----

    public void saveStudents(ArrayList<Student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : students) pw.println(s.toString());
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public void saveTeachers(ArrayList<Teacher> teachers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TEACHERS_FILE))) {
            for (Teacher t : teachers) pw.println(t.toString());
        } catch (IOException e) {
            System.out.println("Error saving teachers: " + e.getMessage());
        }
    }

    public void saveCourses(ArrayList<Course> courses) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(COURSES_FILE))) {
            for (Course c : courses) pw.println(c.toString());
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    public void saveEnrollments(ArrayList<Enrollment> enrollments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ENROLLMENTS_FILE))) {
            for (Enrollment e : enrollments) pw.println(e.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    public void saveAttendance(ArrayList<Attendance> attendanceList) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Attendance a : attendanceList) pw.println(a.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    // ---- LOAD ----

    public ArrayList<Student> loadStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                // Format: id,name,email,department,gpa
                Student s = new Student(Integer.parseInt(p[0]), p[1], p[2], p[3]);
                s.setGpa(Double.parseDouble(p[4]));
                list.add(s);
            }
        } catch (FileNotFoundException e) {
            // No file yet — fresh start
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Teacher> loadTeachers() {
        ArrayList<Teacher> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                // Format: id,name,email,department,salary
                list.add(new Teacher(Integer.parseInt(p[0]), p[1], p[2], p[3], Double.parseDouble(p[4])));
            }
        } catch (FileNotFoundException e) {
            // Fresh start
        } catch (IOException e) {
            System.out.println("Error loading teachers: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Course> loadCourses(ArrayList<Teacher> teachers) {
        ArrayList<Course> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                // Format: courseId,courseName,credit,teacherId
                Course c = new Course(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]));
                int teacherId = Integer.parseInt(p[3]);
                if (teacherId != -1) {
                    for (Teacher t : teachers) {
                        if (t.getId() == teacherId) { c.setTeacher(t); break; }
                    }
                }
                list.add(c);
            }
        } catch (FileNotFoundException e) {
            // Fresh start
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Enrollment> loadEnrollments(ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Enrollment> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ENROLLMENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int sid = Integer.parseInt(p[0]);
                int cid = Integer.parseInt(p[1]);
                double grade = Double.parseDouble(p[2]);

                Student s = findStudent(students, sid);
                Course c  = findCourse(courses, cid);
                if (s != null && c != null) {
                    Enrollment e = new Enrollment(s, c);
                    e.updateGrade(grade);
                    list.add(e);
                    // Restore course <-> student links
                    if (!c.getStudents().contains(s)) c.addStudent(s);
                    if (!s.getEnrolledCourses().contains(c)) s.getEnrolledCourses().add(c);
                }
            }
        } catch (FileNotFoundException e) {
            // Fresh start
        } catch (IOException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Attendance> loadAttendance(ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Attendance> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int sid = Integer.parseInt(p[0]);
                int cid = Integer.parseInt(p[1]);
                int total = Integer.parseInt(p[2]);
                int attended = Integer.parseInt(p[3]);

                Student s = findStudent(students, sid);
                Course c  = findCourse(courses, cid);
                if (s != null && c != null) {
                    Attendance a = new Attendance(s, c, total);
                    for (int i = 0; i < attended; i++) a.markAttendance();
                    list.add(a);
                }
            }
        } catch (FileNotFoundException e) {
            // Fresh start
        } catch (IOException e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        }
        return list;
    }

    // ---- Helpers ----

    private Student findStudent(ArrayList<Student> list, int id) {
        for (Student s : list) if (s.getId() == id) return s;
        return null;
    }

    private Course findCourse(ArrayList<Course> list, int id) {
        for (Course c : list) if (c.getCourseId() == id) return c;
        return null;
    }
}
