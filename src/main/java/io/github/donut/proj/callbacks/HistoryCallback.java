package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.RoomResponse;
import io.github.donut.proj.listener.ISubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HistoryCallback implements ISubscribeCallback, ISubject {

    Consumer<List<RoomResponse>> updateHandler = null;

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {

    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {
        if (msgResultAPI.getChannel().equals(Channels.PRIVATE + messagingAPI.getUuid())) {
            List<RoomResponse> response = Arrays.asList(GsonWrapper.fromJson(msgResultAPI.getMessage(), RoomResponse[].class));

            updateHandler.accept(response);
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
