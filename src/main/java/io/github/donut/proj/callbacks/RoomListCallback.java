package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
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
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {
        if (msgStatus.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            messagingAPI.publish()
                    .message("")
                    .channel(Channels.ROOM_LIST.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {
        System.out.println("room data received: ");
        List<RoomData> list = Arrays.asList(GsonWrapper.fromJson(msgResultAPI.getMessage(), RoomData[].class));
        if (updateHandler != null)
            updateHandler.accept(list);

    }

    @Override
    public void rejected(Exception e) {

    }
}
