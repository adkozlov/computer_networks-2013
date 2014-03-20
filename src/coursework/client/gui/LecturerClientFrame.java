package coursework.client.gui;

import coursework.client.LecturerClient;
import coursework.common.Utils;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.LecturerAuthenticationRequest;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    protected JTabbedPane createContentPane() {
        JTabbedPane result = new JTabbedPane();

        result.add(NEW_TASK_TITLE, createNewTaskPanel());
        result.add(NEW_VERDICT_TITLE, createNewVerdictPanel());

        return result;
    }

    private static final String NEW_TASK_TITLE = "New task";

    private static final String OK_BUTTON_LABEL = "Submit";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";

    private static final String NAME_PANEL_LABEL = "Task name";
    private static final String TEXT_PANEL_LABEL = "Task text";
    private static final String DEADLINE_PANEL_LABEL = "Deadline";

    private static final String EMPTY_TASK_STRING_DIALOG_TITLE = "Empty task name/text";
    private static final String EMPTY_TASK_STRING_DIALOG_LABEL = "Task name/text cannot be empty";
    private static final String CLOSE_DEADLINE_DIALOG_TITLE = "Close deadline";
    private static final String CLOSE_DEADLINE_DIALOG_LABEL = "Task deadline is too close";

    private JPanel createNewTaskPanel() {
        final JTextField nameField = new JTextField();
        JScrollPane nameScrollPane = new JScrollPane(nameField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nameScrollPane.setBorder(BorderFactory.createTitledBorder(NAME_PANEL_LABEL));

        final JTextArea textArea = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createTitledBorder(TEXT_PANEL_LABEL));

        final JXDatePicker datePicker = new JXDatePicker();
        datePicker.setBorder(BorderFactory.createTitledBorder(DEADLINE_PANEL_LABEL));
        datePicker.setDate(tomorrow());
        datePicker.setFormats(Utils.dateFormat());

        JButton okButton = new JButton(OK_BUTTON_LABEL);
        final JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);

        JPanel southPanel = new JPanel();
        southPanel.add(datePicker);
        southPanel.add(okButton);
        southPanel.add(cancelButton);

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

                if (!(name.equals("") || text.equals("") || deadline < tomorrow().getTime())) {
                    newTask(name, text, deadline);

                    cancelButton.doClick();
                } else {
                    boolean stringIsEmpty = name.equals("") || text.equals("");

                    JOptionPane.showMessageDialog(LecturerClientFrame.this, stringIsEmpty ? EMPTY_TASK_STRING_DIALOG_LABEL : CLOSE_DEADLINE_DIALOG_LABEL, stringIsEmpty ? EMPTY_TASK_STRING_DIALOG_TITLE : CLOSE_DEADLINE_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                textArea.setText("");
                datePicker.setDate(tomorrow());
            }
        });

        return result;
    }

    private static Date tomorrow() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date result = calendar.getTime();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return result;
    }

    private static final String NEW_VERDICT_TITLE = "New verdict";

    private static final String STUDENT_NAME_PANEL_LABEL = "Student name";
    private static final String TASK_NAME_PANEL_LABEL = "Task name";
    private static final String COMMENTS_PANEL_LABEL = "Comments";
    private static final String ACCEPTED_CHECKBOX_LABEL = "Accepted";

    private static final String EMPTY_VERDICT_STRING_DIALOG_TITLE = "Empty task name/text";
    private static final String EMPTY_VERDICT_STRING_DIALOG_LABEL = "Task name/text cannot be empty";

    private JPanel createNewVerdictPanel() {
        final JTextField studentNameField = new JTextField();
        JScrollPane studentNameScrollPane = new JScrollPane(studentNameField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        studentNameScrollPane.setBorder(BorderFactory.createTitledBorder(STUDENT_NAME_PANEL_LABEL));

        final JTextField taskNameField = new JTextField();
        JScrollPane taskNameScrollPane = new JScrollPane(taskNameField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        taskNameScrollPane.setBorder(BorderFactory.createTitledBorder(TASK_NAME_PANEL_LABEL));

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(studentNameScrollPane);
        northPanel.add(taskNameScrollPane);

        final JTextArea commentsArea = new JTextArea();
        JScrollPane commentsScrollPane = new JScrollPane(commentsArea);
        commentsScrollPane.setBorder(BorderFactory.createTitledBorder(COMMENTS_PANEL_LABEL));

        final JCheckBox acceptedCheckBox = new JCheckBox(ACCEPTED_CHECKBOX_LABEL);
        JButton okButton = new JButton(OK_BUTTON_LABEL);
        final JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);

        JPanel southPanel = new JPanel();
        southPanel.add(acceptedCheckBox);
        southPanel.add(okButton);
        southPanel.add(cancelButton);

        JPanel result = new JPanel(new BorderLayout());
        result.add(northPanel, BorderLayout.PAGE_START);
        result.add(commentsScrollPane, BorderLayout.CENTER);
        result.add(southPanel, BorderLayout.PAGE_END);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = studentNameField.getText();
                String taskName = taskNameField.getText();
                String comments = commentsArea.getText();
                boolean accepted = acceptedCheckBox.isSelected();

                if (!(studentName.equals("") || taskName.equals(""))) {
                    newVerdict(studentName, taskName, accepted, comments);

                    cancelButton.doClick();
                } else {
                    JOptionPane.showMessageDialog(LecturerClientFrame.this, EMPTY_VERDICT_STRING_DIALOG_LABEL, EMPTY_VERDICT_STRING_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentNameField.setText("");
                taskNameField.setText("");
                commentsArea.setText("");
                acceptedCheckBox.setSelected(false);
            }
        });

        return result;
    }

    @Override
    public LecturerClient getClient() {
        return (LecturerClient) super.getClient();
    }

    private void newTask(String taskName, String text, long deadline) {
        getClient().newTask(getLogin(), taskName, text, deadline);
    }

    private void newVerdict(String studentName, String taskName, boolean accepted, String comments) {
        getClient().newVerdict(studentName, taskName, accepted, comments);
    }

    @Override
    protected final AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new LecturerAuthenticationRequest(login, password);
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
