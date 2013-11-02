package lab_02;

import java.io.IOException;

/**
 * @author adkozlov
 */
public interface IMessage {

    byte[] toByteArray() throws IOException;

}
