package coursework.client.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author adkozlov
 */
public final class AuthenticationDialog extends JDialog {

    private static final String DIALOG_TITLE = "Authentication dialog: ";
    private static final String STUDENT_TITLE = "student";
    private static final String LECTURER_TITLE = "lecturer";

    private static final String LOGIN_LABEL = "login:";
    private static final String PASSWORD_LABEL = "password:";
    private static final String OK_BUTTON_LABEL = "OK";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";
    private static final int FIELD_LENGTH = 12;
    private static final Dimension DEFAULT_DIMENSION = new Dimension(300, 200);

    private static final String AUTHENTICATION_FAILED_DIALOG_TITLE = "Authentication failed";
    private static final String AUTHENTICATION_FAILED_DIALOG_LABEL = "Login/password is incorrect";

    private final JLabel loginLabel = new JLabel(LOGIN_LABEL, JLabel.RIGHT);
    private final JTextField loginField = new JTextField(FIELD_LENGTH);
    private final JLabel passwordLabel = new JLabel(PASSWORD_LABEL, JLabel.RIGHT);
    private final JPasswordField passwordField = new JPasswordField(FIELD_LENGTH);
    private final JButton okButton = new JButton(OK_BUTTON_LABEL);
    private final JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);

    public AuthenticationDialog(final ClientFrame parent, boolean isStudent) {
        super(parent, DIALOG_TITLE + (isStudent ? STUDENT_TITLE : LECTURER_TITLE), true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        ok();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        cancel();
                        break;
                }
            }
        };

        loginField.addKeyListener(keyAdapter);
        passwordField.addKeyListener(keyAdapter);

        setContentPane(createPanel());
        setSize(DEFAULT_DIMENSION);
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private JPanel createPanel() {
        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBadConstraints = new GridBagConstraints();

        gridBadConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBadConstraints.gridx = 0;
        gridBadConstraints.gridy = 0;
        gridBadConstraints.gridwidth = 1;
        northPanel.add(loginLabel, gridBadConstraints);

        gridBadConstraints.gridx = 1;
        gridBadConstraints.gridy = 0;
        gridBadConstraints.gridwidth = 2;
        northPanel.add(loginField, gridBadConstraints);

        gridBadConstraints.gridx = 0;
        gridBadConstraints.gridy = 1;
        gridBadConstraints.gridwidth = 1;
        northPanel.add(passwordLabel, gridBadConstraints);

        gridBadConstraints.gridx = 1;
        gridBadConstraints.gridy = 1;
        gridBadConstraints.gridwidth = 2;

        northPanel.add(passwordField, gridBadConstraints);
        northPanel.setBorder(new LineBorder(Color.GRAY));

        JPanel southPanel = new JPanel();
        southPanel.add(okButton);
        southPanel.add(cancelButton);

        JPanel result = new JPanel(new BorderLayout());
        result.add(northPanel, BorderLayout.CENTER);
        result.add(southPanel, BorderLayout.PAGE_END);

        return result;
    }

    private void ok() {
        if (authenticate()) {
            getParent().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(AuthenticationDialog.this, AUTHENTICATION_FAILED_DIALOG_LABEL, AUTHENTICATION_FAILED_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        setVisible(false);
        ((JFrame) getParent()).dispose();
    }

    private boolean authenticate() {
        ClientFrame parent = (ClientFrame) getParent();

        boolean result = parent.authenticate(loginField.getText(), new String(passwordField.getPassword()));
        loginField.setText("");
        passwordField.setText("");

        return result;
    }
}
