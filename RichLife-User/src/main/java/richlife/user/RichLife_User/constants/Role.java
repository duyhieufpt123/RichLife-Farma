package richlife.user.RichLife_User.constants;

public enum Role {
    USER("Người dùng"),
    ADMIN("Quản trị viên");

    public final String name;

    Role(String name) {
        this.name = name;
    }
}
