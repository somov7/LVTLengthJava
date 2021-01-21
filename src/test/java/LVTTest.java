import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigInteger;

public class LVTTest extends LVT {

    @Test
    public void testEncodeLength() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> LVT.encodeLength(BigInteger.valueOf(0))
        );
        Assertions.assertDoesNotThrow(
                () -> LVT.encodeLength(BigInteger.valueOf(1))
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            LVT.encodeLength(BigInteger.ONE.shiftLeft(1008))
        );
        Assertions.assertDoesNotThrow(() ->
                LVT.encodeLength(BigInteger.ONE.shiftLeft(1008).subtract(BigInteger.ONE))
        );

        byte[] expected = new byte[]{0x01};
        Assertions.assertArrayEquals(expected, LVT.encodeLength(BigInteger.valueOf(1)));

        expected = new byte[]{0x7f};
        Assertions.assertArrayEquals(expected, LVT.encodeLength(BigInteger.valueOf(127)));

        expected = new byte[]{(byte) 0x81, (byte) 0x80};
        Assertions.assertArrayEquals(expected, LVT.encodeLength(BigInteger.valueOf(128)));

        expected = new byte[]{(byte) 0x84, 0x49, (byte) 0x96, 0x02, (byte) 0xD2};
        Assertions.assertArrayEquals(expected, LVT.encodeLength(BigInteger.valueOf(1234567890)));

        expected = new byte[127];
        expected[0] = (byte) 0xFE;
        for (int i = 1; i < 127; i++) {
            expected[i] = (byte) 0xFF;
        }
        Assertions.assertArrayEquals(expected, LVT.encodeLength(BigInteger.ONE.shiftLeft(1008).subtract(BigInteger.ONE)));
    }

    @Test
    public void testDecodeLength() {
        byte[] argument = new byte[]{0x01};
        Assertions.assertEquals(BigInteger.valueOf(1), LVT.decodeLength(argument));

        argument = new byte[]{0x7f};
        Assertions.assertEquals(BigInteger.valueOf(127), LVT.decodeLength(argument));

        argument = new byte[]{(byte) 0x81, (byte) 0x80};
        Assertions.assertEquals(BigInteger.valueOf(128), LVT.decodeLength(argument));

        argument = new byte[]{(byte) 0x84, 0x49, (byte) 0x96, 0x02, (byte) 0xD2};
        Assertions.assertEquals(BigInteger.valueOf(1234567890), LVT.decodeLength(argument));

        argument = new byte[127];
        argument[0] = (byte) 0xFE;
        for (int i = 1; i < 127; i++) {
            argument[i] = (byte) 0xFF;
        }
        Assertions.assertEquals(BigInteger.ONE.shiftLeft(1008).subtract(BigInteger.ONE), LVT.decodeLength(argument));
    }
}