package coursework.common.model;

/**
 * @author adkozlov
 */
public class LecturerAuthenticationRequest extends AuthenticationRequest {

    public LecturerAuthenticationRequest(String login, int passwordHashCode) {
        super(login, passwordHashCode);
    }

    public LecturerAuthenticationRequest(String loginHashCode, String passwordHashCode) {
        super(loginHashCode, passwordHashCode);
    }

    @Override
    public boolean isStudent() {
        return false;
    }
}
