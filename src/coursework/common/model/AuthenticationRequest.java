package coursework.common.model;

/**
 * @author adkozlov
 */
public abstract class AuthenticationRequest {

    private final String login;
    private final int passwordHashCode;

    protected AuthenticationRequest(String login, int passwordHashCode) {
        this.login = login;
        this.passwordHashCode = passwordHashCode;
    }

    protected AuthenticationRequest(String login, String password) {
        this.login = login;
        passwordHashCode = password.hashCode();
    }

    public String getLogin() {
        return login;
    }

    public int getPasswordHashCode() {
        return passwordHashCode;
    }

    public abstract boolean isStudent();
}
