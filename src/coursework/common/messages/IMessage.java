package coursework.common.messages;

import java.io.IOException;

/**
 * @author adkozlov
 */
public interface IMessage {

    public byte[] toByteArray() throws IOException;

    public byte getType();

    public static class UnexpectedMessageException extends IOException {

        public UnexpectedMessageException(IMessage message) {
            super("Unexpected message of type: " + message.getType());
        }
    }
}
