package coursework.client.gui;

import coursework.client.Client;

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
}
