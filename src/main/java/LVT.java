import java.math.BigInteger;

public class LVT {
    static public byte[] encodeLength(BigInteger value) throws IllegalArgumentException {
        if (value.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("Length value must be positive");
        }
        if (value.bitLength() >= 1009) {
            throw new IllegalArgumentException("Length value must not exceed 2^1008");
        }
        if (value.compareTo(BigInteger.valueOf(127)) <= 0) {
            byte[] encoded = new byte[1];
            encoded[0] = (byte) (value.intValue() & 0x7F);
            return encoded;
        }
        byte[] encoded = new byte[(value.bitLength() + 7) / 8 + 1];
        encoded[0] = (byte) ((1 << 7) | ((encoded.length - 1) & 0x7F));
        for (int i = encoded.length - 1; i >= 1; i--) {
            BigInteger[] qr = value.divideAndRemainder(BigInteger.valueOf(256));
            encoded[i] = (byte) (qr[1].intValue() & 0xFF);
            value = qr[0];
        }
        return encoded;
    }

    static public BigInteger decodeLength(byte[] value) {
        boolean isShortForm = (value[0] & (1 << 7)) == 0;
        if (isShortForm) {
            return BigInteger.valueOf(value[0]);
        }
        int length = value[0] & 0x7F;
        value[0] = 0;
        BigInteger decoded = new BigInteger(1, value);
        value[0] = (byte) ((1 << 7) | length);
        return decoded;
    }
}
