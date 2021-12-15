import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay09 {
    String testInput =
            "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678";

    @Test
    void testPart1() {
        Day09 day09 = new Day09(testInput);
        assertEquals(15, day09.getRiskSum());
    }

    @Test
    void testPart2() {
        Day09 day09 = new Day09(testInput);
        assertEquals(1134, day09.getTopBasins());
    }

    @Test
    void testGeneralStuff() {
        Day09.Point p1 = new Day09.Point(2, 3);
        Day09.Point p2 = new Day09.Point(2, 3);
        assertEquals(p1, p2);
        ArrayList<Day09.Point> list = new ArrayList<>();
        list.add(p1);
        assert(list.contains(p1));
        assert(list.contains(p2));
    }
}
