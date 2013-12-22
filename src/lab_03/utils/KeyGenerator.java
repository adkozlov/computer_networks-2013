package lab_03.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author adkozlov
 */
public final class KeyGenerator {
    private static final KeyGenerator INSTANCE = new KeyGenerator();

    public static KeyGenerator getInstance() {
        return INSTANCE;
    }

    private static final BigInteger g = new BigInteger("65537");
    private static final BigInteger p = new BigInteger("a56196d86790c6c1c12ce336a362815ca663d1661839145d3ac4cbd3ad9c810a245ef972b9fb88210b8a5c592f9dad013f185091064b7c27fc1e2e075b3392c013e5a4601e9fe6c64d34f2a33f6eb5c8bd760b35be1cf2fed97433c81239f3021de62dfdb1fb91ed785cc8dacfe816b2dcf2505f87608577a5a191f3078f0c3d", 16);
    private static final HashFunction hashFunction = Hashing.sha256();
    private static final Random random = new Random(System.currentTimeMillis());

    private static final BigInteger pSubstractOne = p.subtract(BigInteger.ONE);

    private final BigInteger publicKey;
    private final BigInteger privateKey;

    private KeyGenerator() {
        privateKey = nextBigInteger(pSubstractOne);
        publicKey = g.modPow(privateKey, p);
    }

    private static BigInteger nextBigInteger(BigInteger n) {
        BigInteger result;

        do {
            result = new BigInteger(n.bitLength(), random).mod(n);
        } while (result.equals(BigInteger.ZERO));

        return result;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger[] sign(String m) {
        BigInteger r = BigInteger.ZERO;
        BigInteger s = BigInteger.ZERO;

        while (s.equals(BigInteger.ZERO)) {
            BigInteger k;
            do {
                k = nextBigInteger(pSubstractOne);
            } while (!k.gcd(pSubstractOne).equals(BigInteger.ONE));

            r = g.modPow(k, p);
            s = SHA256Hasher.bigIntegerHash(m).subtract(privateKey.multiply(r)).multiply(k.modInverse(pSubstractOne)).mod(pSubstractOne);
        }

        return new BigInteger[]{r, s};
    }

    public boolean verify(BigInteger[] signature, String m) {
        BigInteger r = signature[0];
        BigInteger s = signature[1];

        if (r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(p) < 0 && s.compareTo(BigInteger.ZERO) > 0 && s.compareTo(p.subtract(BigInteger.ONE)) < 0) {
            BigInteger first = g.modPow(SHA256Hasher.bigIntegerHash(m), p);
            BigInteger second = publicKey.modPow(r, p).multiply(r.modPow(s, p));

            return first.equals(second);
        }

        return false;
    }
}
