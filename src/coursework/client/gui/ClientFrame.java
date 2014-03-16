package coursework.client.gui;

import coursework.client.Client;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import javax.swing.*;

/**
 * @author adkozlov
 */
public abstract class ClientFrame extends JFrame {

    private final Client client;

    public ClientFrame(Client client) {
        super("");
        this.client = client;

        new AuthenticationDialog(this);

        setContentPane(createTabbedPane());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected abstract JTabbedPane createTabbedPane();

    protected boolean authenticate(String login, String password) {
        AuthenticationResponse authenticationResponse = client.authenticate(new AuthenticationRequest(login, password));
        boolean result = authenticationResponse.isPassed();

        if (result) {
            setTitle(authenticationResponse.getName());
        }

        return result;
    }
}
