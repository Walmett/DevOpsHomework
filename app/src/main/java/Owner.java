package garage;

public class Owner {
    private final int ownerId;
    private final String name;
    private final String lastName;
    private final int age;

    public Owner(int ownerId, String name, String lastName, int age) {
        this.ownerId = ownerId;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public int hashCode() {
        return this.getOwnerId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Owner) {
            return this.getOwnerId() == ((Owner) obj).getOwnerId();
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
