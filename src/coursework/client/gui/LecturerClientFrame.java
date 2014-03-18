package coursework.client.gui;

import coursework.client.LecturerClient;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.LecturerAuthenticationRequest;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author adkozlov
 */
public class LecturerClientFrame extends ClientFrame {

    public LecturerClientFrame(LecturerClient client) {
        super(client);
    }

    @Override
    protected JTabbedPane createTabbedPane() {
        JTabbedPane result = new JTabbedPane();

        result.add(NEW_TASK_TITLE, createNewTaskPanel());

        return result;
    }

    private static final String NEW_TASK_TITLE = "New Task";
    private static final String OK_BUTTON_LABEL = "Submit";
    private static final String NAME_PANEL_LABEL = "Name";
    private static final String TEXT_PANEL_LABEL = "Text";
    private static final String DEADLINE_PANEL_LABEL = "Deadline";
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    private static final String EMPTY_STRING_DIALOG_TITLE = "Empty task name/text";
    private static final String EMPTY_STRING_DIALOG_LABEL = "Task name/text cannot be empty";
    private static final String CLOSE_DEADLINE_DIALOG_TITLE = "Close deadline";
    private static final String CLOSE_DEADLINE_DIALOG_LABEL = "Task deadline is too close";

    private JPanel createNewTaskPanel() {
        final JTextField nameField = new JTextField();
        JScrollPane nameScrollPane = new JScrollPane(nameField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nameField.setBorder(BorderFactory.createTitledBorder(NAME_PANEL_LABEL));

        final JTextArea textArea = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createTitledBorder(TEXT_PANEL_LABEL));

        JButton okButton = new JButton(OK_BUTTON_LABEL);

        final JXDatePicker datePicker = new JXDatePicker();
        datePicker.setBorder(BorderFactory.createTitledBorder(DEADLINE_PANEL_LABEL));
        datePicker.setDate(Calendar.getInstance().getTime());
        datePicker.setFormats(new SimpleDateFormat(DATE_FORMAT));

        JPanel southPanel = new JPanel();
        southPanel.add(datePicker);
        southPanel.add(okButton);

        JPanel result = new JPanel(new BorderLayout());
        result.add(nameScrollPane, BorderLayout.PAGE_START);
        result.add(textScrollPane, BorderLayout.CENTER);
        result.add(southPanel, BorderLayout.PAGE_END);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String text = textArea.getText();
                long deadline = datePicker.getDate().getTime();

                Date time = Calendar.getInstance().getTime();
                if (!(name.equals("") || text.equals("") || deadline <= time.getTime())) {
                    newTask(name, text, deadline);

                    nameField.setText("");
                    textArea.setText("");
                    datePicker.setDate(time);
                } else {
                    boolean stringIsEmpty = name.equals("") || text.equals("");

                    JOptionPane.showMessageDialog(LecturerClientFrame.this, stringIsEmpty ? EMPTY_STRING_DIALOG_LABEL : CLOSE_DEADLINE_DIALOG_LABEL, stringIsEmpty ? EMPTY_STRING_DIALOG_TITLE : CLOSE_DEADLINE_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return result;
    }

    @Override
    public LecturerClient getClient() {
        return (LecturerClient) super.getClient();
    }

    private void newTask(String name, String text, long deadline) {
        getClient().newTask(name, text, deadline);
    }

    @Override
    protected final boolean isStudentClient() {
        return false;
    }

    @Override
    protected final AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new LecturerAuthenticationRequest(login, password);
    }
}
