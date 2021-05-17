package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.UpdateResponseData;
import io.github.donut.proj.controllers.UpdateAccountController;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;
import lombok.*;

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
//                    usernameMsg.setMessage("Username Updated!");
//                    usernameMsg.setUsernameUpdate(true);
//                    EventManager.notify(this, usernameMsg);
                    this.resolved.accept(new RequestMsg("Username", true));
                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
//                    personalInfoMsg.setMessage("Personal Info Updated!");
//                    personalInfoMsg.setPersonalInfoUpdate(true);
//                    EventManager.notify(this, personalInfoMsg);
                    this.resolved.accept(new RequestMsg("Personal", true));

                    // needed to prevent memory leak
                    //EventManager.removeAllObserver(this);
                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
//                    passwordMsg.setMessage("Password Updated");
//                    passwordMsg.setPasswordUpdate(true);
//                    EventManager.notify(this, passwordMsg);
                    this.resolved.accept(new RequestMsg("Password", true));
                }
            } else {                                                                 //else the login was unsuccessful
                if (response.getInfo().equalsIgnoreCase("USERNAME")) {     //checking if the message was create
//                    usernameMsg.setMessage("Username Update Failed");
//                    usernameMsg.setUsernameUpdate(false);
//                    EventManager.notify(this, usernameMsg);
                    this.rejected.accept(new RequestMsg("Username", false));
                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
//                    personalInfoMsg.setMessage("Personal Info Update Failed");
//                    personalInfoMsg.setPersonalInfoUpdate(false);
//                    EventManager.notify(this, personalInfoMsg);
                    this.rejected.accept(new RequestMsg("Personal", false));

                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
//                    passwordMsg.setMessage("Password Update Failed");
//                    passwordMsg.setPasswordUpdate(false);
//                    EventManager.notify(this, passwordMsg);
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