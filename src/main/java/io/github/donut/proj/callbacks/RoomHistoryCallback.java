package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.PlayerData;
import io.github.coreutils.proj.messages.RoomResponse;
import io.github.donut.proj.controllers.AppController;
import io.github.donut.proj.listener.ISubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RoomHistoryCallback implements ISubscribeCallback, ISubject {

    Consumer<List<RoomResponse>> updateHandler = null;

    public RoomHistoryCallback(Consumer<List<RoomResponse>> updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {
        if (msgStatus.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            messagingAPI.publish()
                    .message(AppController.getPlayer())
                    .channel(Channels.GET_ROOMS_DATA.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {
            List<RoomResponse> list = Arrays.asList(GsonWrapper.fromJson(msgResultAPI.getMessage(), RoomResponse[].class));

            if (updateHandler != null)
                updateHandler.accept(list);
    }

    @Override
    public void rejected(Exception e) {

    }
}
