package animals.enums;

public enum Gender {
    MALE("Самец"),
    FEMALE("Самка");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
