package coursework.common;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class Connection {

    private final InetAddress address;
    private final Signature signature;

    public Connection(InetAddress address, Signature signature) {
        this.address = address;
        this.signature = signature;
    }

    public InetAddress getAddress() {
        return address;
    }

    public Signature getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;

        Connection that = (Connection) o;

        return !(address != null ? !address.equals(that.address) : that.address != null) && !(signature != null ? !signature.equals(that.signature) : that.signature != null);

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }
}
