package coursework.client.gui;

import coursework.client.Client;

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
        return null;
    }
}
