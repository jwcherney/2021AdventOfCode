import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDay08 {
    String testInputPart1 =
            "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n" +
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n" +
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n" +
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n" +
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n" +
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n" +
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n" +
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n" +
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n" +
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce";

    @Test
    void testPart1() {
        Day08 day08 = new Day08(testInputPart1);
        assertEquals(26, day08.getPart1Count());
    }

    String testInputPart2 = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf";

    @Test
    void testPart2_1() {
        Day08 day08 = new Day08(testInputPart2);
        assertEquals(5353, day08.getPart2Sum());
    }
    @Test
    void testPart2_2() {
        Day08 day08 = new Day08(testInputPart1);
        assertEquals(61229, day08.getPart2Sum());
    }

    //
    // Tests for SevenSegmentDisplay
    //
    @Test
    void testSevenSegmentDisplay() {
        Day08.SevenSegmentDisplay display = new Day08.SevenSegmentDisplay();
        assertEquals(0, display.toDigit("abcefg"));
        assertEquals(0, display.toDigit("gfecba"));
        assertEquals(1, display.toDigit("cf"));
        assertEquals(1, display.toDigit("fc"));
        assertEquals(2, display.toDigit("acdeg"));
        assertEquals(3, display.toDigit("acdfg"));
        assertEquals(4, display.toDigit("bcdf"));
        assertEquals(5, display.toDigit("abdfg"));
        assertEquals(6, display.toDigit("abdefg"));
        assertEquals(7, display.toDigit("acf"));
        assertEquals(8, display.toDigit("abcdefg"));
        assertEquals(9, display.toDigit("abcdfg"));
    }

    @Test
    void testSevenSegmentDisplayOdd() {
        Day08.SevenSegmentDisplay display = new Day08.SevenSegmentDisplay("zyxwvut");
        assertEquals(0, display.toDigit("zyxvut"));
        assertEquals(0, display.toDigit("tuvxyz"));
        assertEquals(1, display.toDigit("xu"));
        assertEquals(1, display.toDigit("ux"));
        assertEquals(2, display.toDigit("zxwvt"));
        assertEquals(3, display.toDigit("zxwut"));
        assertEquals(4, display.toDigit("yxwu"));
        assertEquals(5, display.toDigit("zywut"));
        assertEquals(6, display.toDigit("zywvut"));
        assertEquals(7, display.toDigit("zxu"));
        assertEquals(8, display.toDigit("zyxwvut"));
        assertEquals(9, display.toDigit("zyxwut"));
    }

    @Test
    void testSevenSegmentDisplayTurnSegmentsOn() {
        Day08.SevenSegmentDisplay display = new Day08.SevenSegmentDisplay();
        display.activateSegments("cf");
        assertEquals(1, display.toDigit());
        assertEquals("cf", display.toSegments());
        display.activateSegments("a");
        assertEquals(7, display.toDigit());
        assertEquals("cfa", display.toSegments());
        display.activateSegments("abcdefg");
        assertEquals(8, display.toDigit());
        assertEquals("cfabdeg", display.toSegments());
    }

    @Test
    void testSevenSegmentDisplayTurnSegmentsOff() {
        Day08.SevenSegmentDisplay display = new Day08.SevenSegmentDisplay();
        display.activateSegments("abcdefg");
        assertEquals(8, display.toDigit());
        assertEquals("abcdefg", display.toSegments());
        display.deactivateSegments("aeg");
        assertEquals(4, display.toDigit());
        assertEquals("bcdf", display.toSegments());
        display.resetSegments();
        assertThrows(IllegalStateException.class, () -> display.toDigit());
        assertEquals("", display.toSegments());
    }

    @Test
    void testSplit() {
        String abc = "abc";
        String[] split = abc.split("");
        assertEquals(3, split.length);
        assertEquals("a", split[0]);
        assertEquals("b", split[1]);
        assertEquals("c", split[2]);
    }
}
