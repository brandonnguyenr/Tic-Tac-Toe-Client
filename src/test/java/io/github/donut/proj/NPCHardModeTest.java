package io.github.donut.proj;

import static io.github.donut.proj.common.Token.*;

import io.github.donut.proj.PlayerType.NPCHardMode;
import io.github.donut.proj.common.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for the hard mode AI. Tests the evaluation methods as well as
 * the actual AI itself with a few different scenarios. 
 * @author Grant Goldsworth
 */
public class NPCHardModeTest
{
    /**
     * Test the evaluation method for the Homi-Mode AI.
     * The evaluate method is designed to return 10/0/-10
     * based on a max/draw/min win.
     * @author Grant Goldsworth
     * @see NPCHardMode#evaluate(Board, int)
     */
    @Test
    public void evaluateBoardTest() {
        Board board = new Board();
        NPCHardMode test = new NPCHardMode(X, O);

        // test row win
        board.updateToken(0, 0, X);
        board.updateToken(0, 1, X);
        board.updateToken(0, 2, X);

        // assert that it is a maximizer (X) win on a horizontal row
        assertEquals(10, test.evaluate(board, 0));


        // test column win
        board = new Board();
        board.updateToken(0, 1, O);
        board.updateToken(1, 1, O);
        board.updateToken(2, 1, O);

        // assert that it is a minimizer (X) win on a horizontal row
        assertEquals(-10, test.evaluate(board, 0));


        // test diagonal
        board = new Board();
        board.updateToken(0,0,X);
        board.updateToken(1,1,X);
        board.updateToken(2,2,X);

        // assert that it is a maximizer (X) win on diagonal
        assertEquals(10, test.evaluate(board, 0));


        // test other diagonal
        board = new Board();
        board.updateToken(0,2,O);
        board.updateToken(1,1,O);
        board.updateToken(2,0,O);

        // assert that it is a minimizer (X) win on diagonal
        assertEquals(-10, test.evaluate(board, 0));

    }

    /**
     * Test the minimax algorithm with a few different scenarios. 
     * @author Grant Goldsworth
     * @see NPCHardMode#miniMax(Board, int, int, int, boolean)
     */
    @Test
    public void miniMaxAlgoTest() {

        int bestValue = -100;   // simulated best value
        int moveRow = 0;        // row of minimax's choice
        int moveCol = 0;        // col of minimax's choice

        // give a scenario
        Board board = new Board();
        NPCHardMode test = new NPCHardMode(X, O);
        board.updateToken(0, 0, X);
        board.updateToken(2, 0, O);
        board.updateToken(0, 1, X);
        board.updateToken(1, 0, O);

        // create a simple driver to run minimax on this scenario
        // for each cell
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 3; col ++) {
               // if the cell is empty
               if(board.getToken(col, row) == BLANK) {
                   // simulate the player's move here (or maximizer's move)
                   board.updateToken(col, row, X);

                   // run minimax on this spot and record result
                   int miniMaxResult = test.miniMax(board, 6, Integer.MIN_VALUE, Integer.MAX_VALUE,false);

                   // undo the move for this iteration
                   board.updateToken(col, row, BLANK);

                   // if the current move's value from minimax is better than the bestValue, update
                   // the best value and record location
                   // should end with value 10 and move [2,2]
                   if (bestValue < miniMaxResult) {
                       bestValue = miniMaxResult;
                       moveRow = row;
                       moveCol = col;
                   }
               }
            }
        }


        // ensure that it returns correct numerical choice
        assertEquals(16, bestValue);
        assertEquals(2, moveRow);
        assertEquals(0, moveCol);
    }

}
