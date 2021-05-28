package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.messagedata.MsgStatusCategory;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.OnlineState;
import io.github.coreutils.proj.messages.Ping;
import io.github.coreutils.proj.messages.RoomData;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RoomListCallback implements ISubscribeCallback {
    Consumer<List<RoomData>> updateLobbyHandler = null;
    Consumer<List<OnlineState>> updateOnlineHandler = null;

    public RoomListCallback(Consumer<List<RoomData>> updateLobbyHandler, Consumer<List<OnlineState>> updateOnlineHandler) {
        this.updateLobbyHandler = updateLobbyHandler;
        this.updateOnlineHandler = updateOnlineHandler;
    }

    @Override
    public void status(MessagingAPI mAPI, MsgStatus status) {
        if (status.getCategory() == MsgStatusCategory.MsgConnectedCategory) {
            mAPI.publish()
                    .message(new Ping())
                    .channel(Channels.ROOM_LIST.toString())
                    .execute();
            mAPI.publish()
                    .message(new Ping())
                    .channel(Channels.ONLINE_STATE.toString())
                    .execute();
        }
    }

    @Override
    public void resolved(MessagingAPI mAPI, MsgResultAPI message) {
        if (message.getChannel().equals(Channels.REQUEST_STATE.toString()) ||
                message.getChannel().equals(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid() + Channels.BUILDER + Channels.REQUEST_STATE)) {
            List<OnlineState> list = Arrays.asList(GsonWrapper.fromJson(message.getMessage(), OnlineState[].class));
            if (updateOnlineHandler != null) {
                updateOnlineHandler.accept(list);
            }
        }

        if (message.getChannel().equals(Channels.REQUEST + Channels.ROOM_LIST.toString()) ||
                message.getChannel().equals(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid())) {
            List<RoomData> list = Arrays.asList(GsonWrapper.fromJson(message.getMessage(), RoomData[].class));
            if (updateLobbyHandler != null)
                updateLobbyHandler.accept(list);
        }
    }

    @Override
    public void rejected(Exception e) {

    }
}
