package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import lombok.Getter;
import lombok.NonNull;

public class GlobalAPIManager {
    @Getter
    private MessagingAPI api;
    private ISubscribeCallback currentCallback = null;
    private String[] currentChannels = null;

    private static class InnerHolder {
        private static final GlobalAPIManager INSTANCE = new GlobalAPIManager();
    }

    private GlobalAPIManager() {
        api = new MessagingAPI();
    }

    public static GlobalAPIManager getInstance() {
        return InnerHolder.INSTANCE;
    }

    public void send(Object obj, String channel) {
        api.publish()
                .message(obj)
                .channel(channel)
                .execute();
    }

    public void swapListener(@NonNull ISubscribeCallback callback, @NonNull String... channels) {
        if (currentCallback != null)
            api.removeEventListener(currentCallback);

        if (currentChannels != null)
            api.unsubscribe().channels(currentChannels).execute();

        api.addEventListener(callback);
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

    public void close() {
        System.out.println("api going down");
        clearListener();
        api.free();
        System.out.println("api closed");
    }
}