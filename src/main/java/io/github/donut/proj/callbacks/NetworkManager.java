package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.PlayerData;
import lombok.NonNull;

public class NetworkManager {
    private static class InnerHolder {
        private static final NetworkManager INSTANCE = new NetworkManager();
    }

    private NetworkManager() {
        // don't delete
    }

    public static NetworkManager getInstance() {
        return InnerHolder.INSTANCE;
    }

    private MessagingAPI api;
    private ISubscribeCallback currentCallback = null;
    private String[] currentChannels = null;
    private PlayerData player;

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

    public void clear() {
        if (currentCallback != null)
            api.removeEventListener(currentCallback);

        if (currentChannels != null)
            api.unsubscribe().channels(currentChannels).execute();

        currentCallback = null;
        currentChannels = null;
    }
}