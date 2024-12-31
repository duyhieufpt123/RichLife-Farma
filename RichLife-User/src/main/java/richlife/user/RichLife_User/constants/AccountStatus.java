package richlife.user.RichLife_User.constants;

public enum AccountStatus {
    /**
     * Active account status.
     */
    ACTIVE("Hoạt động"),
    /**
     * Inactive account status.
     */
    INACTIVE("Không hoạt động");

    /**
     * -- GETTER --
     *  Gets name.
     *
     * @return the name
     */
    public final String name;

    AccountStatus(String name) {
        this.name = name;
    }
}
