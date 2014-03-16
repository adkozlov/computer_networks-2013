package coursework.common.messages;

import java.io.IOException;

/**
 * @author adkozlov
 */
public class MessageTypeRecognizingException extends IOException {

    public MessageTypeRecognizingException(byte b) {
        super("There is no such type of message: " + b);
    }
}
