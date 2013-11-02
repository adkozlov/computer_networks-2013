package lab_02;

/**
 * @author adkozlov
 */
public class MacAddress {

    private final byte[] mac;

    public MacAddress(byte[] mac) {
        this.mac = mac;
    }

    public byte[] getMac() {
        return mac;
    }
}
