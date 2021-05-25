package io.github.donut.proj.PlayerType;

import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.utils.DataValidation;

import static io.github.coreutils.proj.enginedata.Token.BLANK;

/**
 * Implementation of Homi mode for the tic tac toe AI.
 * This PlayerType uses minimax to calculate the best possible move and
 * will always win or tie the other player.
 * @author Grant Goldsworth
 */
public class NPCHardMode implements IPlayerType {
    private static final int MAX_DEPTH = 6;    // needed to avoid long compute times
    private static Token MAXIMIZER = BLANK;
    private static Token MINIMIZER = BLANK;

    // arbitrary numbers for evaluation of the board state
    private static final int WIN_VAL = 10;
    private static final int BEST_VAL = 100;

    //constructor
    public NPCHardMode(Token p1, Token p2) {
        MAXIMIZER = p1;
        MINIMIZER = p2;
    }


    /**
     * Hard mode or Homi Mode - use minimax algorithm to choose the move for homi mode AI.
     * @param board : the tic tac toe board
     * @param c : the token
     * @author : Grant Goldsworth
     */
    @Override
    public void makeMove(Board board, Token c) {
        DataValidation.ensureObjectNotNull("Board", board);
        int moveRow = -1;           // moveRow coordinate of move to make
        int moveCol = -1;           // moveCol coordinate of move to make
        int bestValue = -BEST_VAL;  // starting best value of the AI move

        // for each cell
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 3; col ++) {
                // if the cell is empty
                if (board.getToken(col, row) == BLANK) {
                    // simulate the player's move here (or maximizer's move)
                    board.updateToken(col, row, c);

                    // run minimax on this spot and record result
                    int miniMaxResult = miniMax(board, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE,false);

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
        } // end for each cell in board
        EventManager.notify(this, new IPlayerType.BoardMoveInfo(moveCol, moveRow));
    }


    /**
     * The minimax algorithm. Uses recursion to analyze all possible paths forward from the
     * current state of the board, and make the best possible decision in order
     * for the AI to win or achieve a draw.
     * @param board the board with the current state of the game
     * @param depth the current depth of the recursion; used to increase efficiency
     * @param isMaximizer whether or not current call is for the maximizer's turn
     * @return value chosen at current node by AI
     * @author Grant Goldsworth
     */
    public int miniMax(Board board, int depth, int alpha, int beta, boolean isMaximizer) {
        // evaluate the current board to find out if there is a win/loss
        int boardState = evaluate(board, depth);

        // base cases
        if (Math.abs(boardState) > 0 || depth == 0 || board.isBoardFull())
            return boardState;

        // maximizer's move
        int bestValue;
        if (isMaximizer) {
            bestValue = -BEST_VAL;
            // for each child move, analyze possible routes and the state
            // this means traversing all cells in the board and analyzing
            for (int row = 0; row < 3; row ++) {
                for (int col = 0; col < 3; col ++) {
                    // is this cell empty?
                    if (board.getToken(col, row) == BLANK) {
                        // make move of maximizer since it's their move
                        board.updateToken(col, row, MAXIMIZER);

                        // with hypothetical move made, analyze game state with recursive call
                        bestValue = Math.max(bestValue, miniMax(board, depth - 1, alpha, beta, false));

                        // undo the move
                        board.updateToken(col, row, BLANK);

                        // pruning branches that are the not relevant
                        alpha = Math.max(alpha, bestValue);
                        if (alpha >= beta)
                            return bestValue;
                    }
                }
            } // end for each child
        } // end maximizer's move
        else { // minimizer's move
            bestValue = BEST_VAL;
            // for each child move, analyze possible routes and the state
            // this means traversing all cells in the board and analyzing
            for (int row = 0; row < 3; row ++) {
                for (int col = 0; col < 3; col ++) {
                    // is this cell empty?
                    if (board.getToken(col, row) == BLANK) {
                        // make move of minimizer since it's their move
                        board.updateToken(col, row, MINIMIZER);

                        // with hypothetical move made, analyze game state with recursive call
                        bestValue = Math.min(bestValue, miniMax(board, depth - 1, alpha, beta, true));

                        // undo the move
                        board.updateToken(col, row, BLANK);

                        // pruning branches that are the not relevant
                        beta = Math.min(beta, bestValue);
                        if (alpha >= beta)
                            return bestValue;
                    }
                }
            } // end for each child
        } // end minimizer's move
        return bestValue;
    }


    /**
     * Returns the numerical win/loss status of the current board.
     * Positive WIN_VAL is returned if the maximizer has won, negative WIN_VAL if
     * the minimizer has won, and 0 if there is a draw or no win/loss.
     * @param board the board with the current state of the game
     * @return 10 if max win, 0 if draw/none, -10 if min win
     * @author Grant Goldsworth
     */
    public int evaluate(Board board, int depth) {
        // check columns for X or O victory
        // check that contents are equal in row, then return +/- 10 based on what character is
        for (int col = 0; col < 3; col++) {
            if (board.getToken(col, 0) == board.getToken(col, 1) && board.getToken(col, 0) == board.getToken(col, 2)) {
                // row is all one token - what token is it?
                if (board.getToken(col, 0) == MAXIMIZER)
                    return WIN_VAL + depth;
                else if (board.getToken(col, 0) == MINIMIZER)
                    return -WIN_VAL - depth;
            }

        }

        // check rows for X or O victory
        for (int row = 0; row < 3; row++) {
            if (board.getToken(0, row) == board.getToken(1, row) && board.getToken(0, row) == board.getToken(2, row)) {
                // row is all one token - what token is it?
                if (board.getToken(0, row) == MAXIMIZER)
                    return WIN_VAL + depth;
                else if (board.getToken(0, row) == MINIMIZER)
                    return -WIN_VAL - depth;
            }

        }

        // check diagonals for X or O victory
        // diagonal 1
        if (board.getToken(0,0) == board.getToken(1,1) && board.getToken(0,0) == board.getToken(2,2)) {
            if (board.getToken(0,0) == MAXIMIZER)
                return WIN_VAL + depth;
            else if (board.getToken(0, 0) == MINIMIZER)
                return -WIN_VAL - depth;
        }

        // diagonal 2
        if (board.getToken(0,2) == board.getToken(1,1) && board.getToken(0,2) == board.getToken(2,0)) {
            if (board.getToken(0,2) == MAXIMIZER)
                return WIN_VAL + depth;
            else if (board.getToken(0, 2) == MINIMIZER)
                return -WIN_VAL - depth;
        }

        // final case: no win/loss, return 0
        return 0;
    }
}
