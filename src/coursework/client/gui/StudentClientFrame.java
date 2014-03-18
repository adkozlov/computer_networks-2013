package coursework.client.gui;

import coursework.client.StudentClient;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.StudentAuthenticationRequest;

import javax.swing.*;

/**
 * @author adkozlov
 */
public class StudentClientFrame extends ClientFrame {

    public StudentClientFrame(StudentClient client) {
        super(client);
    }

    @Override
    protected JTabbedPane createTabbedPane() {
        return new JTabbedPane();
    }

    @Override
    public StudentClient getClient() {
        return (StudentClient) super.getClient();
    }

    @Override
    protected final boolean isStudentClient() {
        return true;
    }

    @Override
    protected final AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new StudentAuthenticationRequest(login, password);
    }
}
