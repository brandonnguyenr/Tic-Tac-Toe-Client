package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.PlayerData;
import io.github.coreutils.proj.messages.RoomData;
import io.github.coreutils.proj.messages.RoomFactory;
import lombok.Setter;

import java.util.function.Consumer;

public class RoomRequestCallback implements ISubscribeCallback {
    private final RoomData room;
    private final PlayerData player;
    @Setter
    private Consumer<RoomData> resolved;
    @Setter
    private Consumer<RoomData> rejected;

    public RoomRequestCallback(RoomData room, PlayerData player) {
        this.room = room;
        this.player = player;
    }

    @Override
    public void status(MessagingAPI mApi, MsgStatus status) {
        if (status.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            mApi.publish()
                    .channel(Channels.ROOM_REQUEST.toString())
                    .message(room)
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI mApi, MsgResultAPI message) {
        if (message.getChannel().equals(Channels.PRIVATE + mApi.getUuid())) {
            RoomData response = GsonWrapper.fromJson(message.getMessage(), RoomData.class);

            if (response.getRequestType().equals(RoomData.RequestType.NORMAL) && resolved != null) {
                this.resolved.accept(response);
            } else if (rejected != null) {
                this.rejected.accept(response);
            }
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
