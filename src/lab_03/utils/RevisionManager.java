package lab_03.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author adkozlov
 */
public final class RevisionManager {
    private static final RevisionManager INSTANCE = new RevisionManager();

    public static RevisionManager getInstance() {
        return INSTANCE;
    }

    private final SortedMap<Key, Revision> revisions = new TreeMap<>();

    private RevisionManager() {
    }

    public synchronized String makeRevisionListString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Key, Revision> entry : revisions.entrySet()) {
            sb.append(entry.getKey().bigIntegerValue());
            sb.append(entry.getValue().getVersion());
        }

        return sb.toString();
    }

}
