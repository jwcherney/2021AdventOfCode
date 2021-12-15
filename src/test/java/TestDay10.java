import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay10 {
    String testInput =
            "[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]";

    @Test
    void testPart1() {
        Day10 day10 = new Day10(testInput);
        assertEquals(26397, day10.getPart1());
    }

    @Test
    void testPart2() {
        Day10 day10 = new Day10(testInput);
        assertEquals(new BigInteger("288957"), day10.getPart2());
    }
}
