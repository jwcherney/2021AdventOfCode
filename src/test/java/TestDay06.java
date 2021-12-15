import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay06 {
    int[] testInput = new int[] {3,4,3,1,2};

    @Test
    void testTestInput() {
        Day06 day06 = new Day06(testInput);
        assertEquals(new BigInteger("5"), day06.getCount(0));
        assertEquals(new BigInteger("5"), day06.getCount(1));
        assertEquals(new BigInteger("6"), day06.getCount(2));
        assertEquals(new BigInteger("26"), day06.getCount(18));
        assertEquals(new BigInteger("5934"), day06.getCount(80));
        assertEquals(new BigInteger("26984457539"), day06.getCount(256));
    }
}
