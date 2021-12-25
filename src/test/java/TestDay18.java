import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay18 {
    String testInput1 = "[1,2]\n" + "[[3,4],5]";
    String testOutput1 = "[[1,2],[[3,4],5]]";

    @Test
    void testSimpleAdd() {
        Day18 day18 = new Day18(testInput1);
        assertEquals(testOutput1, day18.toString());
        assertEquals(143, day18.magnitude());
    }
}
