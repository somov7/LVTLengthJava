import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected exactly 2 arguments");
        }
        if (args[0].equals("e")) {
            BigInteger value = new BigInteger(args[1]);
            byte[] encoded = LVT.encodeLength(value);
            StringBuilder encodedString = new StringBuilder();
            for (byte digit: encoded) {
                encodedString.append(String.format("%02X", digit));
            }
            System.out.println(encodedString);
        }
        else if(args[0].equals("d")) {
            byte[] encoded = (new BigInteger(args[1], 16)).toByteArray();
            byte[] tmp = new byte[encoded.length - 1];
            System.arraycopy(encoded, 1, tmp, 0, tmp.length);
            encoded = tmp;
            BigInteger value = LVT.decodeLength(encoded);
            System.out.println(value);
        }
        else {
            throw new IllegalArgumentException("First argument must be either 'e' or 'd'");
        }
    }
}
