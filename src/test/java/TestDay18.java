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

    @Test
    void testMagnitudes() {
        Day18 day18 = new Day18("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        assertEquals(1384, day18.magnitude());
        day18 = new Day18("[[[[1,1],[2,2]],[3,3]],[4,4]]");
        assertEquals(445, day18.magnitude());
        day18 = new Day18("[[[[3,0],[5,3]],[4,4]],[5,5]]");
        assertEquals(791, day18.magnitude());
        day18 = new Day18("[[[[5,0],[7,4]],[5,5]],[6,6]]");
        assertEquals(1137, day18.magnitude());
        day18 = new Day18("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
        assertEquals(3488, day18.magnitude());
    }
    @Test
    void testAdd1() {
        Day18 day18 = new Day18("[1,1]\n[2,2]\n[3,3]\n[4,4]");
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", day18.toString());
    }

    @Test
    void testAdd2() {
        Day18 day18 = new Day18("[1,1]\n[2,2]\n[3,3]\n[4,4]\n[5,5]");
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", day18.toString());
    }

    @Test
    void testAddStepByStep() {
        String s = "[[[[4,3],4],4],[7,[[8,4],9]]] + [1,1]:\n" +
                "\n" +
                "after addition: [[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]\n" +
                "after explode:  [[[[0,7],4],[7,[[8,4],9]]],[1,1]]\n" +
                "after explode:  [[[[0,7],4],[15,[0,13]]],[1,1]]\n" +
                "after split:    [[[[0,7],4],[[7,8],[0,13]]],[1,1]]\n" +
                "after split:    [[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]\n" +
                "after explode:  [[[[0,7],4],[[7,8],[6,0]]],[8,1]]";
        String input1 = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]";
        Day18.SNumber sNumber = Day18.SNumber.factory(0, input1);
        assertEquals("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", sNumber.explode().toString());
        assertEquals("[[[[0,7],4],[15,[0,13]]],[1,1]]", sNumber.explode().toString());
//        day18 = new Day18("[[[[4,3],4],4],[7,[[8,4],9]]]\n[1,1]");
//        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", day18.toString());
    }
}
