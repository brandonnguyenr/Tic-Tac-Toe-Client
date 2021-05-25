package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.*;
import io.github.donut.proj.listener.ISubject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MoveHistoryCallback implements ISubscribeCallback, ISubject {

    Consumer<List<MoveData>> updateHandler = null;
    RoomData dataObj;

    public MoveHistoryCallback(Consumer<List<MoveData>> updateHandler, RoomData dataObj) {
        this.updateHandler = updateHandler;
        this.dataObj = dataObj;
    }

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {
        if (msgStatus.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            messagingAPI.publish()
                    .message(dataObj)
                    .channel(Channels.GET_MOVES_DATA.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {
        List<MoveData> list = Arrays.asList(GsonWrapper.fromJson(msgResultAPI.getMessage(), MoveData[].class));

        if (updateHandler != null)
            updateHandler.accept(list);
    }

    @Override
    public void rejected(Exception e) {

    }

    public void setRoomDataObj(RoomData dataObj) {
        this.dataObj = dataObj;
    }
}
