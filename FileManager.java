import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public ArrayList<Student> loadStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            File f = new File("students.txt");
            if (!f.exists()) return list;
            FileReader fr = new FileReader(f);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 5);
                
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String blood = parts[3];
                Student s = new Student(id, name, email, blood);
                if (parts.length == 5 && parts[4].length() > 0) {
                    String[] cids = parts[4].split("\\|");
                    for (String c : cids) {
                        if (c.length() > 0) s.enrollCourseId(Integer.parseInt(c));
                    }
                }
                list.add(s);
            }
            sc.close();
            fr.close();
        } catch (Exception e) { }
        return list;
    }

    public void saveStudents(ArrayList<Student> students) {
        try {
            FileWriter fw = new FileWriter("students.txt", false);
            for (Student s : students) {
                StringBuilder sb = new StringBuilder();
                sb.append(s.getId()).append(",")
                  .append(s.getName()).append(",")
                  .append(s.getEmail()).append(",")
                  .append(s.getBloodGroup()).append(",");
                ArrayList<Integer> cids = s.getEnrolledCourseIds();
                for (int i = 0; i < cids.size(); i++) {
                    if (i > 0) sb.append("|");
                    sb.append(cids.get(i));
                }
                sb.append("\n");
                fw.write(sb.toString());
            }
            fw.close();
        } catch (Exception e) { System.out.println("Error saving students: " + e.getMessage()); }
    }

    public ArrayList<Teacher> loadTeachers() {
        ArrayList<Teacher> list = new ArrayList<>();
        try {
            File f = new File("teachers.txt");
            if (!f.exists()) return list;
            FileReader fr = new FileReader(f);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 7);
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String blood = parts[3];
                double salary = Double.parseDouble(parts[4]);
                String dept = parts[5];
                Teacher t = new Teacher(id, name, email, blood, salary, dept);
                if (parts.length == 7 && parts[6].length() > 0) {
                    String[] cids = parts[6].split("\\|");
                    for (String c : cids) {
                        if (c.length() > 0) t.assignCourseId(Integer.parseInt(c));
                    }
                }
                list.add(t);
            }
            sc.close();
            fr.close();
        } catch (Exception e) { }
        return list;
    }

    public void saveTeachers(ArrayList<Teacher> teachers) {
        try {
            FileWriter fw = new FileWriter("teachers.txt", false);
            for (Teacher t : teachers) {
                StringBuilder sb = new StringBuilder();
                sb.append(t.getId()).append(",")
                  .append(t.getName()).append(",")
                  .append(t.getEmail()).append(",")
                  .append(t.getBloodGroup()).append(",")
                  .append(t.calculateSalary()).append(",")
                  .append(t.getDepartment()).append(",");
                ArrayList<Integer> cids = t.getAssignedCourseIds();
                for (int i = 0; i < cids.size(); i++) {
                    if (i > 0) sb.append("|");
                    sb.append(cids.get(i));
                }
                sb.append("\n");
                fw.write(sb.toString());
            }
            fw.close();
        } catch (Exception e) { System.out.println("Error saving teachers: " + e.getMessage()); }
    }

    public ArrayList<Course> loadCourses() {
        ArrayList<Course> list = new ArrayList<>();
        try {
            File f = new File("courses.txt");
            if (!f.exists()) return list;
            FileReader fr = new FileReader(f);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 6);
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String dept = parts[2];
                int credit = Integer.parseInt(parts[3]);
                int teacherId = Integer.parseInt(parts[4]);
                Course c = new Course(id, name, dept, credit, teacherId);
                if (parts.length == 6 && parts[5].length() > 0) {
                    String[] sids = parts[5].split("\\|");
                    for (String s : sids) {
                        if (s.length() > 0) c.addStudentId(Integer.parseInt(s));
                    }
                }
                list.add(c);
            }
            sc.close();
            fr.close();
        } catch (Exception e) { }
        return list;
    }

    public void saveCourses(ArrayList<Course> courses) {
        try {
            FileWriter fw = new FileWriter("courses.txt", false);
            for (Course c : courses) {
                StringBuilder sb = new StringBuilder();
                sb.append(c.getCourseId()).append(",")
                  .append(c.getCourseName()).append(",")
                  .append(c.getDepartment()).append(",")
                  .append(c.getCredit()).append(",")
                  .append(c.getTeacherId()).append(",");
                ArrayList<Integer> sids = c.getStudentIds();
                for (int i = 0; i < sids.size(); i++) {
                    if (i > 0) sb.append("|");
                    sb.append(sids.get(i));
                }
                sb.append("\n");
                fw.write(sb.toString());
            }
            fw.close();
        } catch (Exception e) { System.out.println("Error saving courses: " + e.getMessage()); }
    }

    public ArrayList<UserAccount> loadAccounts() {
        ArrayList<UserAccount> list = new ArrayList<>();
        try {
            File f = new File("accounts.txt");
            if (!f.exists()) return list;
            FileReader fr = new FileReader(f);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 4);
                String uname = parts[0];
                String pass = parts[1];
                String role = parts[2];
                int linked = -1;
                if (parts.length == 4) linked = Integer.parseInt(parts[3]);
                list.add(new UserAccount(uname, pass, role, linked));
            }
            sc.close();
            fr.close();
        } catch (Exception e) { }
        return list;
    }

    public void saveAccounts(ArrayList<UserAccount> accounts) {
        try {
            FileWriter fw = new FileWriter("accounts.txt", false);
            for (UserAccount a : accounts) {
                fw.write(a.getUsername() + "," + a.getPassword() + "," + a.getRole() + "," + a.getLinkedId() + "\n");
            }
            fw.close();
        } catch (Exception e) { System.out.println("Error saving accounts: " + e.getMessage()); }
    }
}
