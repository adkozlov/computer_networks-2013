package coursework.client.gui;

import javax.swing.*;
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

    private static final String LOGIN_LABEL = "Login";
    private static final String PASSWORD_LABEL = "Password";
    private static final String OK_BUTTON_LABEL = "OK";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";
    private static final int FIELD_LENGTH = 16;
    private static final Dimension DEFAULT_DIMENSION = new Dimension(300, 200);

    private static final String AUTHENTICATION_FAILED_DIALOG_TITLE = "Authentication failed";
    private static final String AUTHENTICATION_FAILED_DIALOG_LABEL = "Login/password is incorrect";

    private static final String EMPTY_STRING_DIALOG_TITLE = "Empty login/password";
    private static final String EMPTY_STRING_DIALOG_LABEL = "Login/password cannot be empty";

    private JTextField loginField = new JTextField(FIELD_LENGTH);
    private JPasswordField passwordField = new JPasswordField(FIELD_LENGTH);

    public AuthenticationDialog(ClientFrame parent, boolean isStudent) {
        super(parent, DIALOG_TITLE + (isStudent ? STUDENT_TITLE : LECTURER_TITLE), true);

        setContentPane(createPanel());
        setSize(DEFAULT_DIMENSION);
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private JPanel createPanel() {
        loginField.setBorder(BorderFactory.createTitledBorder(LOGIN_LABEL));
        passwordField.setBorder(BorderFactory.createTitledBorder(PASSWORD_LABEL));

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

        JPanel northPanel = new JPanel();
        northPanel.add(loginField);
        northPanel.add(passwordField);

        JButton okButton = new JButton(OK_BUTTON_LABEL);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });

        JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.add(okButton);
        southPanel.add(cancelButton);

        JPanel result = new JPanel(new BorderLayout());
        result.add(northPanel, BorderLayout.CENTER);
        result.add(southPanel, BorderLayout.PAGE_END);

        return result;
    }

    private void ok() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        ClientFrame parent = getParent();
        if (!(login.equals("") || password.equals(""))) {
            if (parent.authenticate(login, password)) {
                parent.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(AuthenticationDialog.this, AUTHENTICATION_FAILED_DIALOG_LABEL, AUTHENTICATION_FAILED_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            }

            loginField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(AuthenticationDialog.this, EMPTY_STRING_DIALOG_LABEL, EMPTY_STRING_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cancel() {
        setVisible(false);
        getParent().dispose();
    }

    @Override
    public ClientFrame getParent() {
        return (ClientFrame) super.getParent();
    }
}
