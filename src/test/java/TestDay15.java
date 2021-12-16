import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay15 {
    String testInput =
            "1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581";

    @Test
    void testPart1() {
        Day15 day15 = new Day15(testInput);
        assertEquals(40, day15.getPart1());
    }
}
