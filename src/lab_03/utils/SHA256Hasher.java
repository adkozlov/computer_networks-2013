package lab_03.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * @author adkozlov
 */
public final class SHA256Hasher {
    private static final SHA256Hasher INSTANCE = new SHA256Hasher();

    public static SHA256Hasher getInstance() {
        return INSTANCE;
    }

    private static final HashFunction hashFunction = Hashing.sha256();

    private SHA256Hasher() {
    }

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static String stringHash(String string) {
        return hashFunction.newHasher().putString(string, DEFAULT_CHARSET).hash().toString();
    }

    public static BigInteger bigIntegerHash(String string) {
        return new BigInteger(stringHash(string));
    }

    public static byte[] bytesHash(File file) throws IOException {
        String content = Files.toString(file, DEFAULT_CHARSET);
        return stringHash(/* TODO revision list + */ stringHash(file.getName()) + stringHash(content)).getBytes(DEFAULT_CHARSET);
    }
}
