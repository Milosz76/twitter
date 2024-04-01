package mvc.model;

public enum Role {
    USER("USER"),
    PRESS("PRESS USER"),
    CORPORATE_USER("CORPORATE USER"),
    INSTITUTIONAL_USER("INSTITUTIONAL USER"),
    GOVERNMENT_USER("GOVERNMENT USER");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
