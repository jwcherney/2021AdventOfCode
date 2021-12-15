import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay11 {

    String stepAlpha = "000\n" +
            "090\n" +
            "000";
    String stepBravo = "222\n" +
            "202\n" +
            "222";
    String stepCharlie = "333\n" +
            "313\n" +
            "333";
    String stepA = "11111\n" +
            "19991\n" +
            "19191\n" +
            "19991\n" +
            "11111";
    String stepB = "34543\n" +
            "40004\n" +
            "50005\n" +
            "40004\n" +
            "34543";
    String stepC = "45654\n" +
            "51115\n" +
            "61116\n" +
            "51115\n" +
            "45654";

    String step0 = "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526";

    String step1 = "6594254334\n" +
            "3856965822\n" +
            "6375667284\n" +
            "7252447257\n" +
            "7468496589\n" +
            "5278635756\n" +
            "3287952832\n" +
            "7993992245\n" +
            "5957959665\n" +
            "6394862637";

    String step2 = "8807476555\n" +
            "5089087054\n" +
            "8597889608\n" +
            "8485769600\n" +
            "8700908800\n" +
            "6600088989\n" +
            "6800005943\n" +
            "0000007456\n" +
            "9000000876\n" +
            "8700006848";

    String step3 = "0050900866\n" +
            "8500800575\n" +
            "9900000039\n" +
            "9700000041\n" +
            "9935080063\n" +
            "7712300000\n" +
            "7911250009\n" +
            "2211130000\n" +
            "0421125000\n" +
            "0021119000";

    String step100 = "0397666866\n" +
            "0749766918\n" +
            "0053976933\n" +
            "0004297822\n" +
            "0004229892\n" +
            "0053222877\n" +
            "0532222966\n" +
            "9322228966\n" +
            "7922286866\n" +
            "6789998766";

    @Test
    void testStepAlpha() {
        Day11 day11 = new Day11(stepAlpha);
        assertEquals(0, day11.getStep());
        assertEquals(stepAlpha, day11.toString());
        assertEquals(0, day11.getFlashCount());
        day11.tick();
        assertEquals(1, day11.getStep());
        assertEquals(stepBravo, day11.toString());
        assertEquals(1, day11.getFlashCount());
        day11.tick();
        assertEquals(2, day11.getStep());
        assertEquals(stepCharlie, day11.toString());
        assertEquals(1, day11.getFlashCount());
    }

    @Test
    void testStepA() {
        Day11 day11 = new Day11(stepA);
        assertEquals(0, day11.getStep());
        assertEquals(stepA, day11.toString());
        assertEquals(0, day11.getFlashCount());
        day11.tick();
        assertEquals(1, day11.getStep());
        assertEquals(stepB, day11.toString());
        assertEquals(9, day11.getFlashCount());
        day11.tick();
        assertEquals(2, day11.getStep());
        assertEquals(stepC, day11.toString());
        assertEquals(9, day11.getFlashCount());
    }

    @Test
    void testStep0() {
        Day11 day11 = new Day11(step0);
        assertEquals(0, day11.getStep());
        assertEquals(step0, day11.toString());
        day11.tick();
        assertEquals(1, day11.getStep());
        assertEquals(step1, day11.toString());
        day11.tick();
        assertEquals(2, day11.getStep());
        assertEquals(step2, day11.toString());
        day11.tick();
        assertEquals(3, day11.getStep());
        assertEquals(step3, day11.toString());
        do {
            day11.tick();
        } while(day11.getStep() < 100);
        assertEquals(100, day11.getStep());
        assertEquals(step100, day11.toString());
        assertEquals(1656, day11.getFlashCount());
    }

    @Test
    void testAllFlashed() {
        Day11 day11 = new Day11("0000\n0000\n0000\n0000");
        assertEquals(true, day11.didAllFlash());
        day11 = new Day11("0000\n0000\n0000\n0001");
        assertEquals(false, day11.didAllFlash());
    }

    @Test
    void testOctopusFlash() {
        Day11.Octopus octopus = new Day11.Octopus(0, 0, 8, null);
        assertEquals(false, octopus.canFlash());
        assertEquals(8, octopus.value);
        assertEquals("8", octopus.toString());
        octopus.startTick();
        assertEquals(false, octopus.canFlash());
        assertEquals(9, octopus.value);
        assertEquals("9", octopus.toString());
        octopus.endTick();
        assertEquals(false, octopus.canFlash());
        assertEquals(9, octopus.value);
        assertEquals("9", octopus.toString());
        octopus.startTick();
        assertEquals(true, octopus.canFlash());
        assertEquals(10, octopus.value);
        assertEquals("10", octopus.toString());
        octopus.endTick();
        assertEquals(false, octopus.canFlash());
        assertEquals(0, octopus.value);
        assertEquals("0", octopus.toString());
    }
}
