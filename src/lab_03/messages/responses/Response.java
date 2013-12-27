package lab_03.messages.responses;

import lab_03.messages.Message;

/**
 * @author adkozlov
 */
public abstract class Response extends Message {

    protected static final byte OK_CODE = 0x00;
    protected static final byte DENIED_CODE = 0x01;

    protected static final String EXCEPTION_FORMAT = "wrong code: %b";

}
