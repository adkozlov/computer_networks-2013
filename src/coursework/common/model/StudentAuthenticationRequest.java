package coursework.common.model;

/**
 * @author adkozlov
 */
public class StudentAuthenticationRequest extends AuthenticationRequest {

    public StudentAuthenticationRequest(String login, int passwordHashCode) {
        super(login, passwordHashCode);
    }

    public StudentAuthenticationRequest(String loginHashCode, String passwordHashCode) {
        super(loginHashCode, passwordHashCode);
    }

    @Override
    public boolean isStudent() {
        return true;
    }
}
