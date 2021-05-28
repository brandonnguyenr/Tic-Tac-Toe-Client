package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.Ping;
import io.github.coreutils.proj.messages.RoomData;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RoomListCallback implements ISubscribeCallback {
    Consumer<List<RoomData>> updateHandler = null;

    public RoomListCallback(Consumer<List<RoomData>> updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public void status(MessagingAPI mAPI, MsgStatus status) {
        if (status.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            mAPI.publish()
                    .message(new Ping())
                    .channel(Channels.ROOM_LIST.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI mAPI, MsgResultAPI message) {
        List<RoomData> list = Arrays.asList(GsonWrapper.fromJson(message.getMessage(), RoomData[].class));
        if (updateHandler != null)
            updateHandler.accept(list);
    }

    @Override
    public void rejected(Exception e) {

    }
}
