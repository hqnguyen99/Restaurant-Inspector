package model;

public class Violations {
    String id;
    String description;

    public Violations(
            String id,
            String description
    ) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Number: " + id +
                "\nDescription: " + description;
    }
}
