public class UserAccount {
    private String username;
    private String password;
    private String role;
    private Person linkedPerson;

    public UserAccount(String username, String password, String role, Person linkedPerson) {
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
        this.linkedPerson = linkedPerson;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public Person getLinkedPerson() { return linkedPerson; }
}
