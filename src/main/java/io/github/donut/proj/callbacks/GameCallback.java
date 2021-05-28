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
import io.github.coreutils.proj.messages.PlayerData;
import io.github.coreutils.proj.messages.RoomData;
import lombok.Setter;

import java.util.function.Consumer;

@Setter
public class GameCallback implements ISubscribeCallback {
    private RoomData room;
    private Board board;
    private Consumer<MoveRequestData> boardHandler;

    public GameCallback(RoomData room, Board board) {
        this.room = room;
        this.board = board;
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
        } else if (message.getChannel().equals("CLOSE")) {
            PlayerData data = GsonWrapper.fromJson(message.getMessage(), PlayerData.class);
            boardHandler.accept(new MoveRequestData(this.board, room, null, (data.getPlayerToken() == Token.X) ? Token.O : Token.X));
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
