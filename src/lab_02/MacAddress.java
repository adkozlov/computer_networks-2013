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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacAddress)) return false;

        MacAddress that = (MacAddress) o;

        if (!Arrays.equals(mac, that.mac)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mac != null ? Arrays.hashCode(mac) : 0;
    }
}
