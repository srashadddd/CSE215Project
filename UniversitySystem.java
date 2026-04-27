import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UniversitySystem uni = new UniversitySystem();
        Scanner sc = new Scanner(System.in);
        ArrayList<UserAccount> accounts = new ArrayList<>();

        boolean authenticated = false;
        UserAccount currentUser = null;

        while (!authenticated) {
            System.out.println("\n=== Welcome to Smart University System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.print("Enter new username: ");
                String uname = sc.nextLine();
                System.out.print("Enter new password: ");
                String pass = sc.nextLine();
                System.out.print("Enter role (student/teacher/admin): ");
                String role = sc.nextLine();
                accounts.add(new UserAccount(uname, pass, role));
                System.out.println("Account created successfully. Please login.");
            } 
            else if (option == 2) {
                System.out.print("Enter username: ");
                String uname = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();
                for (UserAccount acc : accounts) {
                    if (acc.getUsername().equals(uname) && acc.getPassword().equals(pass)) {
                        authenticated = true;
                        currentUser = acc;
                        System.out.println("Login successful. Welcome " + uname + " (" + acc.getRole() + ")");
                        break;
                    }
                }
                if (!authenticated) {
                    System.out.println("Invalid credentials. Try again.");
                }
            }
        }

        boolean running = true;
        while (running) {
            if (currentUser.getRole().equals("admin")) {
                System.out.println("\n=== Admin Menu ===");
                System.out.println("1. Add Student");
                System.out.println("2. Add Teacher");
                System.out.println("3. Add Course");
                System.out.println("4. Enroll Student in Course");
                System.out.println("5. Update Student");
                System.out.println("6. Update Teacher");
                System.out.println("7. Update Course");
                System.out.println("8. Delete Student");
                System.out.println("9. Delete Teacher");
                System.out.println("10. Delete Course");
                System.out.println("11. Search Student");
                System.out.println("12. Search Teacher");
                System.out.println("13. Search Course");
                System.out.println("14. Generate Report");
                System.out.println("15. Logout");
            } else if (currentUser.getRole().equals("teacher")) {
                System.out.println("\n=== Teacher Menu ===");
                System.out.println("1. Add Course");
                System.out.println("2. Enroll Student in Course");
                System.out.println("3. Generate Report");
                System.out.println("4. Logout");
            } else if (currentUser.getRole().equals("student")) {
                System.out.println("\n=== Student Menu ===");
                System.out.println("1. View My Details");
                System.out.println("2. View My Courses");
                System.out.println("3. Logout");
            }

            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (currentUser.getRole().equals("admin")) {
                if (choice == 15) { running = false; }
                else if (choice == 14) { uni.generateReport(); }
                
            } 
            else if (currentUser.getRole().equals("teacher")) {
                if (choice == 4) { running = false; }
                else if (choice == 3) { uni.generateReport(); }
                
            } 
            else if (currentUser.getRole().equals("student")) {
                if (choice == 3) { running = false; }
                else if (choice == 1) {
                    System.out.println("My Details: " + currentUser.getUsername());
                }
                else if (choice == 2) {
                    System.out.println("Courses enrolled will be shown here.");
                }
            }
        }

        sc.close();
    }
}
