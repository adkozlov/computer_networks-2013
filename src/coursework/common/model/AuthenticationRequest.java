package coursework.common.model;

/**
 * @author adkozlov
 */
public class AuthenticationRequest {

    private final int loginHashCode, passwordHashCode;

    public AuthenticationRequest(int loginHashCode, int passwordHashCode) {
        this.loginHashCode = loginHashCode;
        this.passwordHashCode = passwordHashCode;
    }

    public AuthenticationRequest(String loginHashCode, String passwordHashCode) {
        this.loginHashCode = loginHashCode.hashCode();
        this.passwordHashCode = passwordHashCode.hashCode();
    }

    public int getLoginHashCode() {
        return loginHashCode;
    }

    public int getPasswordHashCode() {
        return passwordHashCode;
    }
}
