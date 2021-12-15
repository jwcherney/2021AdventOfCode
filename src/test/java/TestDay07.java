import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay07 {
    Integer[] testInput = new Integer[] {16,1,2,0,4,2,7,1,2,14};

    @Test
    void testTestInputPart1() {
        Day07 day07 = new Day07(testInput);
        assertEquals(2, day07.getMinPosition());
        assertEquals(37, day07.getMinFuel());
        assertEquals(37, day07.getFuelUsedAt(2));
        assertEquals(41, day07.getFuelUsedAt(1));
        assertEquals(39, day07.getFuelUsedAt(3));
        assertEquals(71, day07.getFuelUsedAt(10));
    }

    @Test
    void testTestInputPart2() {
        Day07 day07 = new Day07(testInput, true);
        assertEquals(5, day07.getMinPosition());
        assertEquals(168, day07.getMinFuel());
        assertEquals(206, day07.getFuelUsedAt(2));
    }
}
