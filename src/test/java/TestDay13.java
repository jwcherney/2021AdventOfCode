import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay13 {
    String testInput =
            "6,10\n" +
            "0,14\n" +
            "9,10\n" +
            "0,3\n" +
            "10,4\n" +
            "4,11\n" +
            "6,0\n" +
            "6,12\n" +
            "4,1\n" +
            "0,13\n" +
            "10,12\n" +
            "3,4\n" +
            "3,0\n" +
            "8,4\n" +
            "1,10\n" +
            "2,14\n" +
            "8,10\n" +
            "9,0\n" +
            "\n" +
            "fold along y=7\n" +
            "fold along x=5";
    String image0 =
            "...#..#..#.\n" +
            "....#......\n" +
            "...........\n" +
            "#..........\n" +
            "...#....#.#\n" +
            "...........\n" +
            "...........\n" +
            "...........\n" +
            "...........\n" +
            "...........\n" +
            ".#....#.##.\n" +
            "....#......\n" +
            "......#...#\n" +
            "#..........\n" +
            "#.#........";
    String image1 =
            "#.##..#..#.\n" +
            "#...#......\n" +
            "......#...#\n" +
            "#...#......\n" +
            ".#.#..#.###\n" +
            "...........\n" +
            "...........";
    String image2 =
            "#####\n" +
            "#...#\n" +
            "#...#\n" +
            "#...#\n" +
            "#####\n" +
            ".....\n" +
            ".....";
    @Test
    void testImages() {
        Day13 day13 = new Day13(testInput);
        assertEquals(image0, day13.getGrid());
        assertEquals(18, day13.countDots());
        day13.fold();
        assertEquals(image1, day13.getGrid());
        assertEquals(17, day13.countDots());
        day13.fold();
        assertEquals(image2, day13.getGrid());
        assertEquals(16, day13.countDots());
    }
}
