package lab_02;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return Arrays.toString(mac);
    }
}
