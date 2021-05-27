package io.github.donut.proj;

import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import org.junit.jupiter.api.Test;

import static io.github.coreutils.proj.enginedata.Token.O;
import static io.github.coreutils.proj.enginedata.Token.X;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void toStringTest() {
        Board board = new Board();
        Token token = X;
        for (int i = 0; i < board.BOARD_ROWS; i++) {
            for (int j = 0; j < board.BOARD_COLUMNS; j++) {
                board.updateToken(i, j, token);
                token = (token == X) ? O : X;
            }
        }
        System.out.println(board);
    }

    @Test
    public void equalsTest() {
        Board board1 = new Board();
        Board board2 = new Board();

        assertEquals(board2, board1);

        board2.updateToken(0, 1, X);
        assertNotEquals(board1, board2);
    }

    /**
     * Testing when the board is full and not full
     */
    @Test
    public void fullBoardTest() {
        Board board = new Board();

        // fill board with tokens
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board.updateToken(i, j, i % 2 == 0 ? X : O);

        // assert that method returns that it is a full board
        assertTrue(board.isBoardFull());

        // create non-full board
        board = new Board();
        board.updateToken(0, 2, X);

        // assert that method returns that it is not a full board
        assertFalse(board.isBoardFull());
    }
}
