import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay04 {

    //
    // Board Tests
    //

    int[] testBoardArray = new int[] {
            14, 21, 17, 24,  4,
            10, 16, 15,  9, 19,
            18,  8, 23, 26, 20,
            22, 11, 13,  6,  5,
             2,  0, 12,  3,  7
    };

    @Test
    void testRowWin() {
        Day04.Board testBoard = new Day04.Board(testBoardArray);
        testForWin(testBoard, new int[] {14, 21, 17, 24, 4}, new boolean[] {false, false, false, false, true}, 980);
        assertEquals(testBoard.checkBingo(), true);
        testBoard.reset();
        assertEquals(testBoard.checkBingo(), false);
        testForWin(testBoard, new int[] {14, 21, 17, 24, 4, 5}, new boolean[] {false, false, false, false, true, true}, 980);
    }

    @Test
    void testColumnWin() {
        Day04.Board testBoard = new Day04.Board(testBoardArray);
        testForWin(testBoard, new int[] {14, 10, 18, 22, 2}, new boolean[] {false, false, false, false, true}, 518);
        testBoard.reset();
        testForWin(testBoard, new int[] { 2,  0, 12,  3, 11, 7}, new boolean[] {false, false, false, false, false, true}, 2030);
    }

    @Test
    void testDiagonalDoNotWin() {
        Day04.Board testBoard = new Day04.Board(testBoardArray);
        testForWin(testBoard, new int[] {14, 16, 23,  6, 7}, new boolean[] {false, false, false, false, false}, 0);
    }

    void testForWin(Day04.Board board, int[] inputCall, boolean[] winOutput, int expectedBoardValue) {
        for(int i = 0; i < inputCall.length; i++) {
            boolean isWin = board.call(inputCall[i]);
//            System.out.println("input: " + inputCall[i] + " isWin: " + isWin);
            assertEquals(isWin, winOutput[i]);
        }
        assertEquals(board.getValue(), expectedBoardValue);
    }

    //
    // Play Bingo tests
    //
    int[] callArray = new int[] {7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1};
    int[][] boardData = new int[][] {
            /* 00 */{22, 13, 17, 11 , 0, 8 , 2, 23 , 4, 24, 21 , 9, 14, 16 , 7, 6, 10 , 3, 18 , 5, 1, 12, 20, 15, 19},
            /* 01 */{3, 15 , 0 , 2, 22, 9, 18, 13, 17 , 5, 19 , 8 , 7, 25, 23, 20, 11, 10, 24 , 4, 14, 21, 16, 12 , 6},
            /* 02 */{14, 21, 17, 24 , 4, 10, 16, 15 , 9, 19, 18 , 8, 23, 26, 20, 22, 11, 13 , 6 , 5, 2 , 0, 12 , 3 , 7}
    };
    @Test
    void testPlayBingoPart1() {
        Day04.Board winningBoard = Day04.playBingoPart1(boardData, callArray);
        assertEquals(4512, winningBoard.getValue());
    }
    @Test
    void testPlayBingoPart2() {
        Day04.Board lastWinningBoard = Day04.playBingoPart2(boardData, callArray);
        assertEquals(1924, lastWinningBoard.getValue());
    }
}
