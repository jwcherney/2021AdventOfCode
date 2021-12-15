import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay12 {
    String input1 = "start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end";
    String output1A = "start,A,b,A,c,A,end\n" +
            "start,A,b,A,end\n" +
            "start,A,b,end\n" +
            "start,A,c,A,b,A,end\n" +
            "start,A,c,A,b,end\n" +
            "start,A,c,A,end\n" +
            "start,A,end\n" +
            "start,b,A,c,A,end\n" +
            "start,b,A,end\n" +
            "start,b,end";
    String output1B = "start,A,b,A,b,A,c,A,end\n" +
            "start,A,b,A,b,A,end\n" +
            "start,A,b,A,b,end\n" +
            "start,A,b,A,c,A,b,A,end\n" +
            "start,A,b,A,c,A,b,end\n" +
            "start,A,b,A,c,A,c,A,end\n" +
            "start,A,b,A,c,A,end\n" +
            "start,A,b,A,end\n" +
            "start,A,b,d,b,A,c,A,end\n" +
            "start,A,b,d,b,A,end\n" +
            "start,A,b,d,b,end\n" +
            "start,A,b,end\n" +
            "start,A,c,A,b,A,b,A,end\n" +
            "start,A,c,A,b,A,b,end\n" +
            "start,A,c,A,b,A,c,A,end\n" +
            "start,A,c,A,b,A,end\n" +
            "start,A,c,A,b,d,b,A,end\n" +
            "start,A,c,A,b,d,b,end\n" +
            "start,A,c,A,b,end\n" +
            "start,A,c,A,c,A,b,A,end\n" +
            "start,A,c,A,c,A,b,end\n" +
            "start,A,c,A,c,A,end\n" +
            "start,A,c,A,end\n" +
            "start,A,end\n" +
            "start,b,A,b,A,c,A,end\n" +
            "start,b,A,b,A,end\n" +
            "start,b,A,b,end\n" +
            "start,b,A,c,A,b,A,end\n" +
            "start,b,A,c,A,b,end\n" +
            "start,b,A,c,A,c,A,end\n" +
            "start,b,A,c,A,end\n" +
            "start,b,A,end\n" +
            "start,b,d,b,A,c,A,end\n" +
            "start,b,d,b,A,end\n" +
            "start,b,d,b,end\n" +
            "start,b,end";
    String input2 = "dc-end\n" +
            "HN-start\n" +
            "start-kj\n" +
            "dc-start\n" +
            "dc-HN\n" +
            "LN-dc\n" +
            "HN-end\n" +
            "kj-sa\n" +
            "kj-HN\n" +
            "kj-dc";
    String output2 = "start,HN,dc,HN,end\n" +
            "start,HN,dc,HN,kj,HN,end\n" +
            "start,HN,dc,end\n" +
            "start,HN,dc,kj,HN,end\n" +
            "start,HN,end\n" +
            "start,HN,kj,HN,dc,HN,end\n" +
            "start,HN,kj,HN,dc,end\n" +
            "start,HN,kj,HN,end\n" +
            "start,HN,kj,dc,HN,end\n" +
            "start,HN,kj,dc,end\n" +
            "start,dc,HN,end\n" +
            "start,dc,HN,kj,HN,end\n" +
            "start,dc,end\n" +
            "start,dc,kj,HN,end\n" +
            "start,kj,HN,dc,HN,end\n" +
            "start,kj,HN,dc,end\n" +
            "start,kj,HN,end\n" +
            "start,kj,dc,HN,end\n" +
            "start,kj,dc,end";
    String input3 = "fs-end\n" +
            "he-DX\n" +
            "fs-he\n" +
            "start-DX\n" +
            "pj-DX\n" +
            "end-zg\n" +
            "zg-sl\n" +
            "zg-pj\n" +
            "pj-he\n" +
            "RW-he\n" +
            "fs-DX\n" +
            "pj-RW\n" +
            "zg-RW\n" +
            "start-pj\n" +
            "he-WI\n" +
            "zg-he\n" +
            "pj-fs\n" +
            "start-RW";
    int output3Size = 226;

    @Test
    void testInput1A() {
        Day12 day12 = new Day12(input1);
        assertEquals(10, day12.getPathCount());
        assertEquals(output1A, day12.getCompletePaths());
    }
    @Test
    void testInput1B() {
        Day12 day12 = new Day12(input1);
        day12.getPathCount(true);
        assertEquals(36, day12.getPathCount(true));
        assertEquals(output1B, day12.getCompletePaths());
    }
    @Test
    void testInput2A() {
        Day12 day12 = new Day12(input2);
        assertEquals(19, day12.getPathCount());
        assertEquals(output2, day12.getCompletePaths());
    }
    @Test
    void testInput2B() {
        Day12 day12 = new Day12(input2);
        assertEquals(103, day12.getPathCount(true));
    }
    @Test
    void testInput3A() {
        Day12 day12 = new Day12(input3);
        assertEquals(output3Size, day12.getPathCount());
    }
    @Test
    void testInput3B() {
        Day12 day12 = new Day12(input3);
        assertEquals(3509, day12.getPathCount(true));
    }

    String anotherInput = "start-A\nA-b\nA-c\nA-end";
    String anotherOutput = "start,A,b,A,c,A,b,A,end";
    @Test
    void anotherTest() {
        Day12 day12 = new Day12(anotherInput);
        day12.getPathCount(true);
        assert(day12.getCompletePaths().contains(anotherOutput));
    }

    @Test
    void testPathHasMultipleSmallCaves() {
        Day12 day12 = new Day12(anotherInput);
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start"));
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start,A"));
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start,A,b"));
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start,A,b,A"));
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start,A,b,A,c"));
        assertEquals(false, day12.pathHasRepeatedSmallCaves("start,A,b,A,c,A"));
        assertEquals(true, day12.pathHasRepeatedSmallCaves("start,A,b,A,c,A,b"));
        assertEquals(true, day12.pathHasRepeatedSmallCaves("start,A,b,A,c,A,b,A"));
        assertEquals(true, day12.pathHasRepeatedSmallCaves("start,A,b,A,c,A,b,A,end"));
        assertEquals(true, day12.pathHasRepeatedSmallCaves("b,b"));
    }
}
