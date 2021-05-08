package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginResponseData;
import io.github.donut.proj.controllers.CreateAccountController;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;

public class AuthorizationCallback implements ISubscribeCallback, ISubject{

    public AuthorizationCallback() {
        EventManager.register(this, CreateAccountController.getInstance());
    }

    @Override
    public void status(MessagingAPI mApi, MsgStatus status) {

    }

    @Override
    public void resolved(MessagingAPI mApi, MsgResultAPI message) {
        ReplyMessage msg = new ReplyMessage();

        System.out.println(message.getChannel());

        if (message.getChannel().equals(Channels.PRIVATE + mApi.getUuid())) {
            LoginResponseData response = GsonWrapper.fromJson(message.getMessage(), LoginResponseData.class);
            if (response.isLoginSuccess()) {
                //out.printf("%s's query was processed successfully!%n%n", response.getData().getUsername());
                //TODO: Remove test comments
                System.out.println("Successful");
                System.out.println(message.getChannel());
                System.out.println(Channels.AUTHOR_CREATE.toString());

                if (response.getInfo().equalsIgnoreCase("CREATE")) {
                    //System.out.println("HERE");
                    msg.setAccountCreationSuccess("Account Created Successfully!");
                    msg.setAccountCreation(true);
                    //System.out.println(msg.isAccountCreation());
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {
                    msg.setLoginSuccess("Login Success!");
                    msg.setLoginValidation(true);
                }

            } else {
                //TODO: Remove test comments
                System.out.println("unsuccessful");
                if (response.getInfo().equalsIgnoreCase("CREATE")) {
                    msg.setAccountCreationUnSuccess("Account Creation Failed!");
                    msg.setAccountCreation(false);
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {
                    msg.setLoginUnSuccess("Login Unsuccessful!");
                    msg.setLoginValidation(false);
                }
//                out.printf("%s's query was NOT processed successfully!%n", response.getData().getUsername());
//                out.printf("Error message returned: %s%n", response.getInfo());
            }

            Logger.log("BRO");
            EventManager.notify(this, msg);

        }


    }

    @Override
    public void rejected(Exception e) {

    }

    public static class ReplyMessage {
        private String accountCreationSuccess;
        private String accountCreationUnSuccess;
        private String loginSuccess;
        private String loginUnSuccess;
        private boolean accountCreation;
        private boolean loginValidation;

        public String getAccountCreationSuccess() {
            return accountCreationSuccess;
        }

        public void setAccountCreationSuccess(String accountCreationSuccess) {
            this.accountCreationSuccess = accountCreationSuccess;
        }

        public String getAccountCreationUnSuccess() {
            return accountCreationUnSuccess;
        }

        public void setAccountCreationUnSuccess(String accountCreationUnSuccess) {
            this.accountCreationUnSuccess = accountCreationUnSuccess;
        }

        public String getLoginSuccess() {
            return loginSuccess;
        }

        public void setLoginSuccess(String loginSuccess) {
            this.loginSuccess = loginSuccess;
        }

        public String getLoginUnSuccess() {
            return loginUnSuccess;
        }

        public void setLoginUnSuccess(String loginUnSuccess) {
            this.loginUnSuccess = loginUnSuccess;
        }

        public boolean isAccountCreation() {
            return accountCreation;
        }

        public void setAccountCreation(boolean accountCreation) {
            this.accountCreation = accountCreation;
        }

        public boolean isLoginValidation() {
            return loginValidation;
        }

        public void setLoginValidation(boolean loginValidation) {
            this.loginValidation = loginValidation;
        }
    }
}

