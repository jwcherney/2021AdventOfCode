import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay14 {
    String testInput = "NNCB\n" +
            "\n" +
            "CH -> B\n" +
            "HH -> N\n" +
            "CB -> H\n" +
            "NH -> C\n" +
            "HB -> C\n" +
            "HC -> B\n" +
            "HN -> C\n" +
            "NN -> C\n" +
            "BH -> H\n" +
            "NC -> B\n" +
            "NB -> B\n" +
            "BN -> B\n" +
            "BB -> N\n" +
            "BC -> B\n" +
            "CC -> N\n" +
            "CN -> C";
    String step0 = "NNCB";
    String step1 = "NCNBCHB";
    String step2 = "NBCCNBBBCBHCB";
    String step3 = "NBBBCNCCNBBNBNBBCHBHHBCHB";
    String step4 = "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB";
    int step05Length = 97;
    int step10Length = 3073;
    int step10BCount = 1749;
    int step10CCount = 298;
    int step10HCount = 161;
    int step10NCount = 865;
    int step10MostLessLeast = 1588;

    @Test
    void testTestData() {
        Day14 day14 = new Day14(testInput);
        assertEquals(0, day14.getStepCount());
        assertEquals(step0, day14.getData());
        assertEquals(new BigInteger("4"), day14.getLength());
        assertEquals(new BigInteger("2"), day14.getMostLessLeast());

        assertEquals(1, day14.step());
        assertEquals(new BigInteger("7"), day14.getLength());
        assertEquals(BigInteger.ONE, day14.getMostLessLeast());

        assertEquals(2, day14.step());
        assertEquals(new BigInteger("13"), day14.getLength());
        assertEquals(3, day14.step());
        assertEquals(new BigInteger("25"), day14.getLength());
        assertEquals(4, day14.step());
        assertEquals(new BigInteger("49"), day14.getLength());

        while(day14.getStepCount() < 10) {
            day14.step();
            if (day14.getStepCount() == 5) {
                assertEquals(new BigInteger(String.valueOf(step05Length)), day14.getLength());
            }
        }
        assertEquals(10, day14.getStepCount());
        assertEquals(new BigInteger(String.valueOf(step10Length)), day14.getLength());
        assertEquals(new BigInteger(String.valueOf(step10MostLessLeast)), day14.getMostLessLeast());

        while(day14.getStepCount() < 40) {
            day14.step();
        }
        assertEquals(new BigInteger("2188189693529"), day14.getMostLessLeast());
    }
}
