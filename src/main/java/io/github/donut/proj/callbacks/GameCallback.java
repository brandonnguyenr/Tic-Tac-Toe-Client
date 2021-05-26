package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.MoveRequestData;
import io.github.coreutils.proj.messages.RoomData;
import lombok.Setter;

import java.util.Arrays;
import java.util.function.Consumer;

public class GameCallback implements ISubscribeCallback {
    private RoomData room;
    @Setter
    private Consumer<MoveRequestData> boardHandler;

    public GameCallback(RoomData room) {
        this.room = room;
    }


    @Override
    public void status(MessagingAPI mAPI, MsgStatus status) {
        if (status.getCategory().equals(MsgStatusCategory.MsgConnectedCategory)) {
            mAPI.publish()
                    .message(room)
                    .channel(Channels.REQUEST_MOVE.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI mAPI, MsgResultAPI message) {
        if (message.getChannel().equals(room.getRoomChannel())) {
            MoveRequestData data = GsonWrapper.fromJson(message.getMessage(), MoveRequestData.class);
            boardHandler.accept(data);
//            if (data.getCurrentPlayer() == null) {
//                if (boardHandler != null)
//                    boardHandler.accept(data.getBoard());
//                if (data.getWinningToken() == Token.X) {
//                    System.out.println(Token.X + " Player: " + data.getRoomData().getPlayer1().getPlayerUserName() + " has won");
//                } else if (data.getWinningToken() == Token.O) {
//                    System.out.println(Token.O + " Player: " + data.getRoomData().getPlayer2().getPlayerUserName() + " has won");
//                } else {
//                    System.out.println("Tie Game");
//                }
//            } else if (data.getCurrentPlayer().equals(userName)) {
//                boardHandler.accept(data.getBoard());
//                System.out.println("turn switched");
//            }
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
