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

public class HistoryCallback implements ISubscribeCallback, ISubject {

    private final Runnable resolved;
    private final Runnable rejected;

    public HistoryCallback(Runnable resolved, Runnable rejected) {
        this.resolved = resolved;
        this.rejected = rejected;
    }

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {

    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {
        if (msgResultAPI.getChannel().equals(Channels.PRIVATE + messagingAPI.getUuid())) {
            ArrayList<RoomResponse> response = GsonWrapper.fromJson(msgResultAPI.getMessage(), RoomResponse.class);
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
