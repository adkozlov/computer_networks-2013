package lab_02;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.SortedSet;
import java.util.TimerTask;
import java.util.TreeSet;

/**
 * @author adkozlov
 */
public class Messenger extends JFrame {

    private static final Messenger INSTANCE = new Messenger();
    private static final long PERIOD = 100;

    public static Messenger getInstance() {
        return INSTANCE;
    }

    public static final String BUTTON_LABEL = "Send";
    public static final String APPLICATION_LABEL = "Messenger";
    public static final String MESSAGES_PANEL_LABEL = "Display area";
    public static final String NEW_MESSAGE_PANEL_LABEL = "Text area";

    private final JPanel messagesPanel = new JPanel();
    private final JPanel newMessagePanel = new JPanel();
    private final JTextArea messagesTextArea = new JTextArea(16, 80);
    private final JTextField newMessageTextField = new JTextField();
    private final JButton sendButton = new JButton(BUTTON_LABEL);

    private Messenger() {
        super(APPLICATION_LABEL);

        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(messagesPanel);
        content.add(newMessagePanel);

        messagesPanel.setBorder(new TitledBorder(new EtchedBorder(), MESSAGES_PANEL_LABEL));
        messagesPanel.setLayout(new BorderLayout());
        messagesPanel.add(messagesTextArea);

        JScrollPane messagesScrollPane = new JScrollPane(messagesTextArea);
        messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);

        newMessagePanel.setBorder(new TitledBorder(new EtchedBorder(), NEW_MESSAGE_PANEL_LABEL));
        newMessagePanel.setLayout(new GridLayout());
        newMessagePanel.add(newMessageTextField);
        newMessagePanel.add(sendButton);

        messagesTextArea.setEditable(false);
        newMessageTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getSource() == newMessageTextField && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sendButton) {
                    sendMessage();
                }
            }
        });
        sendButton.setSize(40, 20);

        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                messagesTextArea.setText(receiveMessages());
            }
        }, 0, PERIOD);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setSize(600, 600);
        setVisible(true);
    }

    private void sendMessage() {
        if (!newMessageTextField.getText().equals("")) {
            TCPClient.getInstance().sendMessage(newMessageTextField.getText());
            newMessageTextField.setText("");
        }
    }

    public String receiveMessages() {
        StringBuilder sb = new StringBuilder();

        for (String message : TCPClient.getInstance().getMessages()) {
            sb.append(message);
        }

        return sb.toString();
    }
}
