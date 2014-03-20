package coursework.client.gui;

import coursework.client.IGroupable;
import coursework.client.StudentClient;
import coursework.common.FileWrapper;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.StudentAuthenticationRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class StudentClientFrame extends ClientFrame implements IGroupable {

    public StudentClientFrame(StudentClient client) {
        super(client);
    }

    @Override
    protected JTabbedPane createContentPane() {
        JTabbedPane result = new JTabbedPane();

        result.add(NEW_SOLUTION_TITLE, createNewSolutionPanel());

        return result;
    }

    private static final String NEW_SOLUTION_TITLE = "New solution";

    private static final String COURSE_PANEL_LABEL = "Course name";
    private static final String NAME_PANEL_LABEL = "Task name";
    private static final String SOLUTION_BUTTON_LABEL = "Select solution file";
    private static final String OK_BUTTON_LABEL = "Submit";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";

    private static final String OPEN_ERROR_DIALOG_TITLE = "File not loaded";

    private static final String EMPTY_STRING_DIALOG_TITLE = "Empty course/task name";
    private static final String EMPTY_STRING_DIALOG_LABEL = "Course/task name cannot be empty";
    private static final String NO_FILE_ATTACHED_DIALOG_TITLE = "No solution file attached";
    private static final String NO_FILE_ATTACHED_DIALOG_LABEL = NO_FILE_ATTACHED_DIALOG_TITLE;

    private JPanel createNewSolutionPanel() {
        final JTextField courseField = new JTextField();
        JScrollPane courseScrollPane = new JScrollPane(courseField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        courseField.setBorder(BorderFactory.createTitledBorder(COURSE_PANEL_LABEL));

        final JTextField nameField = new JTextField();
        JScrollPane nameScrollPane = new JScrollPane(nameField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nameField.setBorder(BorderFactory.createTitledBorder(NAME_PANEL_LABEL));

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(courseScrollPane);
        northPanel.add(nameScrollPane);

        JButton solutionButton = new JButton(SOLUTION_BUTTON_LABEL);
        JButton okButton = new JButton(OK_BUTTON_LABEL);
        final JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);

        JPanel southPanel = new JPanel();
        southPanel.add(solutionButton);
        southPanel.add(okButton);
        southPanel.add(cancelButton);

        JPanel result = new JPanel(new BorderLayout());
        result.add(northPanel, BorderLayout.PAGE_START);
        result.add(southPanel, BorderLayout.PAGE_END);

        solutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser solutionChooser = new JFileChooser();

                if (solutionChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    try {
                        solution = new FileWrapper(solutionChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(StudentClientFrame.this, e1.getLocalizedMessage(), OPEN_ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseField.getText();
                String name = nameField.getText();

                if (!(courseName.equals("") || name.equals("") || solution == null)) {
                    newSolution(courseName, name, solution);

                    cancelButton.doClick();
                } else {
                    boolean stringIsEmpty = courseName.equals("") || name.equals("");

                    JOptionPane.showMessageDialog(StudentClientFrame.this, stringIsEmpty ? EMPTY_STRING_DIALOG_LABEL : NO_FILE_ATTACHED_DIALOG_LABEL, stringIsEmpty ? EMPTY_STRING_DIALOG_TITLE : NO_FILE_ATTACHED_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseField.setText("");
                nameField.setText("");
                solution = null;
            }
        });

        return result;
    }

    private FileWrapper solution = null;

    @Override
    public StudentClient getClient() {
        return (StudentClient) super.getClient();
    }

    private void newSolution(String courseName, String name, FileWrapper solution) {
        getClient().newSolution(courseName, name, solution);
    }

    @Override
    protected final AuthenticationRequest createAuthenticationRequest(String login, String password) {
        return new StudentAuthenticationRequest(login, password);
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}
