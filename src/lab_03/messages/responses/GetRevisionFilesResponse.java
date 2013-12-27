package lab_03.messages.responses;

import lab_03.utils.SHA256Hash;
import lab_03.utils.Signature;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adkozlov
 */
public class GetRevisionFilesResponse extends Response {

    private final boolean isOk;
    private final Signature signature;
    private final int version;
    private final List<SHA256Hash> fileHashes;

    public boolean isOk() {
        return isOk;
    }

    public GetRevisionFilesResponse(Signature signature, int version, List<SHA256Hash> fileHashes) {
        isOk = true;

        this.signature = signature;
        this.version = version;
        this.fileHashes = fileHashes;
    }

    public GetRevisionFilesResponse() {
        isOk = false;

        signature = null;
        version = 0;
        fileHashes = null;
    }

    public static GetRevisionFilesResponse readFromStream(DataInputStream dis) throws IOException {
        byte code = dis.readByte();

        switch (code) {
            case OK_CODE:
                Signature signature = Signature.readFromStream(dis);

                int version = dis.readInt();

                int length = dis.readInt();
                List<SHA256Hash> fileHashes = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    fileHashes.add(SHA256Hash.readFromStream(dis));
                }

                return new GetRevisionFilesResponse(signature, version, fileHashes);
            case DENIED_CODE:
                return new GetRevisionFilesResponse();
            default:
                throw new IOException(String.format(EXCEPTION_FORMAT, code));
        }
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        if (isOk()) {
            dos.writeByte(OK_CODE);

            signature.writeToStream(dos);

            dos.writeInt(version);

            dos.writeInt(fileHashes.size());
            for (SHA256Hash fileHash : fileHashes) {
                fileHash.writeToStream(dos);
            }
        } else {
            dos.writeByte(DENIED_CODE);
        }
    }
}
