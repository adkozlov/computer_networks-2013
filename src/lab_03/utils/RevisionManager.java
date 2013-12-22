package lab_03.utils;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author adkozlov
 */
public final class RevisionManager {
    private static final RevisionManager INSTANCE = new RevisionManager();

    public static RevisionManager getInstance() {
        return INSTANCE;
    }

    private final Map<BigInteger, Revision> revisions = new ConcurrentHashMap<>();

    private RevisionManager() {
    }
}
