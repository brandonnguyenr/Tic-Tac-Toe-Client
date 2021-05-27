package io.github.donut.proj.PlayerType;

import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;

public class Human implements IPlayerType, IObserver {
    /**
     * This method will make a move for the NPC. It will take in the board object and token of the NPC as
     * parameters and will use algorithms depending on the difficulty level of the NPC to make a move and place
     * it's token on the board.
     *
     * @param board : the tic tac toe board
     * @param c     : the token
     */
    @Override
    public void makeMove(Board board, Token c) {
        // something should go here
        // notify event manager maybe??
    }

    /**
     * New info is received through this method. Object decoding is needed
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof BoardUI.UserSelectionData) {
            BoardUI.UserSelectionData temp = (BoardUI.UserSelectionData) eventType;
            EventManager.notify(this, new IPlayerType.BoardMoveInfo(temp.getX(), temp.getY()));
        }
    }
}
