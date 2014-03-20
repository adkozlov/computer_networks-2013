package coursework.client.gui;

import coursework.client.Client;
import coursework.client.IGroupable;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import javax.swing.*;
import java.awt.*;

/**
 * @author adkozlov
 */
public abstract class ClientFrame extends JFrame implements IGroupable {

    private static final Dimension DEFAULT_DIMENSION = new Dimension(400, 300);

    private final Client client;

    public ClientFrame(Client client) {
        super("");
        this.client = client;

        new AuthenticationDialog(this, isStudentsObject());

        setContentPane(createContentPane());
        setSize(DEFAULT_DIMENSION);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public Client getClient() {
        return client;
    }

    protected abstract JTabbedPane createContentPane();

    protected abstract AuthenticationRequest createAuthenticationRequest(String login, String password);

    private String login;

    protected boolean authenticate(String login, String password) {
        AuthenticationResponse authenticationResponse = client.authenticate(createAuthenticationRequest(login, password));
        boolean result = authenticationResponse.isPassed();

        if (result) {
            setTitle(authenticationResponse.getName());
            this.login = login;
        }

        return result;
    }

    protected String getLogin() {
        return login;
    }
}
