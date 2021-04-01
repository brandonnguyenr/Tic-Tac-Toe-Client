package io.github.donut.proj.PlayerType;

import io.github.donut.proj.common.Board;
import io.github.donut.proj.common.Token;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import java.util.Objects;

/**
 * @author utsavparajuli
 * @author Grant Goldsworth
 * @version 2.0
 * PlayerType represents the non computer player which will have several levels of difficulty. The interface has a
 * method to make a move. It will also extend the ISubject interface which is used as a design pattern
 */
public interface IPlayerType extends ISubject {  //need to implement IObserver and ISubject

    /**
     * The class BoardMoveInfo contains the data that should be updated in the game class using IObserver.
     */
     class BoardMoveInfo {
        private final int  x;     // column in board
        private final int  y;     // row in board

        /**
         * This constructor will initialize the BoardModeInfo class with the x, y, and c which are row-position
         * in board, column-position in board, and token to be placed in board respectively.
         * @param x : column number
         * @param y : row number
         */
        BoardMoveInfo(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * This method will return the x-position/column chose by the NPC
         * @return x (column)
         */
        public int getX() {
            return x;
        }

        /**
         * This method will return the y-position/row chose by the NPC
         * @return y (row)
         */
        public int getY() {
            return y;
        }

        /**
         * This method will override the equals method of the object and will check if two instances
         * of the BoardMoveInfo classes are equal
         * @param o : an object
         * @return : a boolean value of true if the objects are equal or else false
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BoardMoveInfo that = (BoardMoveInfo) o;

            return  getX() == that.getX() &&
                    getY() == that.getY();
        }

        /**
         * Overrides the hashCode method
         * @return : the hashcode of the object
         */
        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }

        /**
         * Overrides the toString method which will return the data of the class as a string
         * @return : a string value
         */
        @Override
        public String toString() {
            return "BoardMoveInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /**
     * This method will make a move for the PlayerType. It will take in the board object and token of the PlayerType as
     * parameters and will use algorithms depending on the difficulty level of the PlayerType to make a move and place
     * it's token on the board.
     * @param board : the tic tac toe board
     * @param c : the token
     */
    void makeMove(Board board, Token c);
}
