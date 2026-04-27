public abstract class Person {
    private int id;
    private String name;
    private String email;
    private String bloodGroup;

    public Person(int id, String name, String email, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
    }

    public abstract String getDetails();

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getBloodGroup() {
        return bloodGroup;
    }
}
