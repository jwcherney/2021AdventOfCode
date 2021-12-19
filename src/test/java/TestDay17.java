import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDay17 {
    String testInput = "target area: x=20..30, y=-10..-5";

    @Test
    void testTestInput1() {
        Day17 day17 = new Day17(testInput);
        boolean isHit = day17.fire(7,2);
        assertTrue(isHit);
        assertEquals(7, day17.getStep());
        assertEquals(new Day17.Point(28,-7), day17.getPoint());
    }

    @Test
    void testTestInput2() {
        Day17 day17 = new Day17(testInput);
        boolean isHit = day17.fire(6,3);
        assertTrue(isHit);
        assertEquals(9, day17.getStep());
        assertEquals(new Day17.Point(21,-9), day17.getPoint());
    }

    @Test
    void testTestInput3() {
        Day17 day17 = new Day17(testInput);
        boolean isHit = day17.fire(9,0);
        assertTrue(isHit);
        assertEquals(4, day17.getStep());
        assertEquals(new Day17.Point(30,-6), day17.getPoint());
    }

    @Test
    void testTestInput4() {
        Day17 day17 = new Day17(testInput);
        boolean isHit = day17.fire(17,-4);
        assertFalse(isHit);
    }

    @Test
    void testTestInput5() {
        Day17 day17 = new Day17(testInput);
        boolean isHit = day17.fire(6,9);
        assertTrue(isHit);
        assertEquals(20, day17.getStep());
        assertEquals(new Day17.Point(21,-10), day17.getPoint());
    }
}
