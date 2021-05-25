package io.github.donut.proj;

import io.github.coreutils.proj.enginedata.Board;
import io.github.donut.proj.PlayerType.NPCEasyMode;
import org.junit.jupiter.api.Test;

import static io.github.coreutils.proj.enginedata.Token.*;

/**
 * Testing for PlayerType Easy Mode
 */
public class NPCEasyModeTest {

    /**
     * Testing when the board only has one spot left for the token to be placed
     *
     * @author : Utsav Parajuli
     */
    @Test
    public void boardLastSpotTest() {
        Board board = new Board();

        //filling up the board
        for (int i = 0; i < board.BOARD_ROWS; i++) {
            for (int j = 0; j < board.BOARD_COLUMNS; j++) {
                board.updateToken(i, j, X);
            }
        }

        board.updateToken(2, 2, BLANK);

        NPCEasyMode easyMode = new NPCEasyMode();

        easyMode.makeMove(board, O);
    }
}
