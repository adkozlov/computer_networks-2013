package coursework.client.gui;

import coursework.client.Client;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.StudentAuthenticationRequest;

import javax.swing.*;

/**
 * @author adkozlov
 */
public class StudentClientFrame extends ClientFrame {

    public StudentClientFrame(Client client) {
        super(client);
    }

    @Override
    protected JTabbedPane createTabbedPane() {
        return new JTabbedPane();
    }

    @Override
    protected boolean isStudentClient() {
        return true;
    }

    @Override
    protected AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new StudentAuthenticationRequest(login, password);
    }
}
