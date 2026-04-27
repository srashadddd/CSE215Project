public class UserAccount {
    private String username;
    private String password;
    private String role;
    private int linkedId;

    public UserAccount(String username, String password, String role, int linkedId) {
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
        this.linkedId = linkedId;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public int getLinkedId() {
        return linkedId;
    }
}
