package coursework.common.messages;

import java.io.IOException;

/**
 * @author adkozlov
 */
public interface IMessage {

    public byte[] toByteArray() throws IOException;

    public int getType();
}
