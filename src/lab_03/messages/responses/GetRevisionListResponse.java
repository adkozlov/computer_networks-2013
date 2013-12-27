package lab_03.messages.responses;

import lab_03.utils.Key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author adkozlov
 */
public class GetRevisionListResponse extends Response {

    private final SortedMap<Key, Integer> revisionList;

    public GetRevisionListResponse(SortedMap<Key, Integer> revisionList) {
        this.revisionList = revisionList;
    }

    public static GetRevisionListResponse readFromStream(DataInputStream dis) throws IOException {
        int length = dis.readInt();
        SortedMap<Key, Integer> result = new TreeMap<>();
        for (int i = 0; i < length; i++) {
            result.put(Key.readFromStream(dis), dis.readInt());
        }

        return new GetRevisionListResponse(result);
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        dos.writeInt(revisionList.size());

        for (Map.Entry<Key, Integer> entry : revisionList.entrySet()) {
            dos.write(entry.getKey().getBytes());
            dos.writeInt(entry.getValue());
        }
    }
}
