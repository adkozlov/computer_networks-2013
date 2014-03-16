package coursework.client.gui;

import coursework.client.Client;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.LecturerAuthenticationRequest;

import javax.swing.*;

/**
 * @author adkozlov
 */
public class LecturerClientFrame extends ClientFrame {

    public LecturerClientFrame(Client client) {
        super(client);
    }

    @Override
    protected JTabbedPane createTabbedPane() {
        return new JTabbedPane();
    }

    @Override
    protected boolean isStudentClient() {
        return false;
    }

    @Override
    protected AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new LecturerAuthenticationRequest(login, password);
    }
}
