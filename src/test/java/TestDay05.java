import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay05 {
    String testInput =
            "0,9 -> 5,9\n" +
            "8,0 -> 0,8\n" +
            "9,4 -> 3,4\n" +
            "2,2 -> 2,1\n" +
            "7,0 -> 7,4\n" +
            "6,4 -> 2,0\n" +
            "0,9 -> 2,9\n" +
            "3,4 -> 1,4\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2";

    @Test
    void testOverlapsPart1() {
        Day05 vents = new Day05(testInput, false);
        //System.out.println(vents.printMap());
        assertEquals(5, vents.countOverlaps());
    }

    @Test
    void testOverlapsPart2() {
        Day05 vents = new Day05(testInput, true);
        //System.out.println(vents.printMap());
        assertEquals(12, vents.countOverlaps());
    }
}
