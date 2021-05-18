package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.PlayerData;
import lombok.NonNull;

public class PlayerManager {
    private MessagingAPI api;
    private ISubscribeCallback currentCallback = null;
    private String[] currentChannels = null;
    private final PlayerData player;

    private static class InnerHolder {
        private static final PlayerManager INSTANCE = new PlayerManager();
    }

    private PlayerManager() {
        player = new PlayerData();
    }

    public static PlayerManager getInstance() {
        return InnerHolder.INSTANCE;
    }


    public String getPlayerID() {
        return player.getPlayerID();
    }
    public PlayerData getPlayer(String channel) {
        PlayerData result = new PlayerData(player);
        result.setChannel(channel);
        return result;
    }

    public void setUserName(String id, String username) {
        player.setPlayerID(id);
        player.setPlayerName(username);
    }

    public void swapListener(@NonNull ISubscribeCallback callback, @NonNull String... channels) {
        api.addEventListener(callback);

        if (currentCallback != null)
            api.removeEventListener(currentCallback);

        if (currentChannels != null)
            api.unsubscribe().channels(currentChannels).execute();

        api.subscribe().channels(channels).execute();

        currentCallback = callback;
        currentChannels = channels;
    }

    public void clearListener() {
        if (currentCallback != null)
            api.removeEventListener(currentCallback);

        if (currentChannels != null)
            api.unsubscribe().channels(currentChannels).execute();

        currentCallback = null;
        currentChannels = null;
    }
}