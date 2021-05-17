package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.UpdateResponseData;
import io.github.donut.proj.listener.ISubject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Consumer;

/**
 * This class is the used as a callback that will have the data that is returned from the update service.
 * @author Utsav Parajuli
 */
public class UpdatesCallback implements ISubscribeCallback, ISubject {

    private final Consumer<RequestMsg> resolved;
    private final Consumer<RequestMsg> rejected;

    /**
     * Constructor for the UpdatesCallback class.
     * @author Utsav Parajuli
     */
    public UpdatesCallback(Consumer<RequestMsg> resolved, Consumer<RequestMsg> rejected) {
        this.resolved = resolved;
        this.rejected = rejected;
    }

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {
    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {

        //checking if we are getting the message from the particular instance of api
        if (msgResultAPI.getChannel().equals(Channels.PRIVATE + messagingAPI.getUuid())) {
            UpdateResponseData response = GsonWrapper.fromJson(msgResultAPI.getMessage(), UpdateResponseData.class);

            if (response.isUpdateSuccess()) {                                        //if the login/create was successful
                if (response.getInfo().equalsIgnoreCase("USERNAME")) {    //checking if the message was create
                    this.resolved.accept(new RequestMsg("Username", true));

                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
                    this.resolved.accept(new RequestMsg("Personal", true));

                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
                    this.resolved.accept(new RequestMsg("Password", true));
                }
            } else {                                                                 //else the login was unsuccessful
                if (response.getInfo().equalsIgnoreCase("USERNAME")) {     //checking if the message was create
                    this.rejected.accept(new RequestMsg("Username", false));

                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
                    this.rejected.accept(new RequestMsg("Personal", false));

                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
                    this.rejected.accept(new RequestMsg("Password", false));

                }
            }
        }
    }

    @Override
    public void rejected(Exception e) {

    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class RequestMsg {
        private final String type;
        private final boolean isResolved;
    }
}