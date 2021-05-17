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

/**
 * This class is the used as a callback that will have the data that is returned from the update service.
 * @author Utsav Parajuli
 */
public class UpdatesCallback implements ISubscribeCallback, ISubject {

    private final Runnable resolved;
    private final Runnable rejected;

    /**
     * Constructor for the UpdatesCallback class.
     * @author Utsav Parajuli
     */
    public UpdatesCallback(Runnable resolved, Runnable rejected) {
        this.resolved = resolved;
        this.rejected = rejected;
    }

    @Override
    public void status(MessagingAPI messagingAPI, MsgStatus msgStatus) {
    }

    @Override
    public void resolved(MessagingAPI messagingAPI, MsgResultAPI msgResultAPI) {

        //instances of appropriate message data classes
        UsernameMsg usernameMsg = new UsernameMsg();
        PersonalInfoMsg personalInfoMsg = new PersonalInfoMsg();
        PasswordMsg passwordMsg = new PasswordMsg();

        //checking if we are getting the message from the particular instance of api
        if (msgResultAPI.getChannel().equals(Channels.PRIVATE + messagingAPI.getUuid())) {
            UpdateResponseData response = GsonWrapper.fromJson(msgResultAPI.getMessage(), UpdateResponseData.class);

            if (response.isUpdateSuccess()) {                                        //if the login/create was successful
                if (response.getInfo().equalsIgnoreCase("USERNAME")) {    //checking if the message was create
//                    usernameMsg.setMessage("Username Updated!");
//                    usernameMsg.setUsernameUpdate(true);
//                    EventManager.notify(this, usernameMsg);
                    this.resolved.run();

                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
//                    personalInfoMsg.setMessage("Personal Info Updated!");
//                    personalInfoMsg.setPersonalInfoUpdate(true);
//                    EventManager.notify(this, personalInfoMsg);
                    this.resolved.run();

                    // needed to prevent memory leak
                    //EventManager.removeAllObserver(this);
                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
//                    passwordMsg.setMessage("Password Updated");
//                    passwordMsg.setPasswordUpdate(true);
//                    EventManager.notify(this, passwordMsg);
                    this.resolved.run();
                }
            } else {                                                                 //else the login was unsuccessful
                if (response.getInfo().equalsIgnoreCase("USERNAME")) {     //checking if the message was create
//                    usernameMsg.setMessage("Username Update Failed");
//                    usernameMsg.setUsernameUpdate(false);
//                    EventManager.notify(this, usernameMsg);
                    this.rejected.run();
                } else if (response.getInfo().equalsIgnoreCase("PERSONALINFO")) {   //checking if message was login
//                    personalInfoMsg.setMessage("Personal Info Update Failed");
//                    personalInfoMsg.setPersonalInfoUpdate(false);
//                    EventManager.notify(this, personalInfoMsg);
                    this.rejected.run();

                } else if (response.getInfo().equalsIgnoreCase("PASSWORD")) {   //checking if message was login
//                    passwordMsg.setMessage("Password Update Failed");
//                    passwordMsg.setPasswordUpdate(false);
//                    EventManager.notify(this, passwordMsg);
                    this.rejected.run();

                }
            }
        }
    }

    @Override
    public void rejected(Exception e) {

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsernameMsg {
        private String message;
        private boolean usernameUpdate;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalInfoMsg {
        private String message;
        private boolean personalInfoUpdate;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PasswordMsg {
        private String message;
        private boolean passwordUpdate;
    }
}