package coursework.client.gui;

import coursework.client.Client;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import javax.swing.*;
import java.awt.*;

/**
 * @author adkozlov
 */
public abstract class ClientFrame extends JFrame {

    private static final Dimension DEFAULT_DIMENSION = new Dimension(400, 300);

    private final Client client;

    public ClientFrame(Client client) {
        super("");
        this.client = client;

        new AuthenticationDialog(this, isStudentClient());

        setContentPane(createTabbedPane());
        setSize(DEFAULT_DIMENSION);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Client getClient() {
        return client;
    }

    protected abstract JTabbedPane createTabbedPane();

    protected abstract boolean isStudentClient();

    protected abstract AuthenticationRequest createAuthenticationRequest(String login, String password);

    protected boolean authenticate(String login, String password) {
        AuthenticationResponse authenticationResponse = client.authenticate(createAuthenticationRequest(login, password));
        boolean result = authenticationResponse.isPassed();

        if (result) {
            setTitle(authenticationResponse.getName());
        }

        return result;
    }
}
