package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginResponseData;
import io.github.donut.proj.controllers.CreateAccountController;
import io.github.donut.proj.controllers.LoginController;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;

public class AuthorizationCallback implements ISubscribeCallback, ISubject{

    public AuthorizationCallback() {
        EventManager.register(this, CreateAccountController.getInstance());
        EventManager.register(this, LoginController.getInstance());
    }

    @Override
    public void status(MessagingAPI mApi, MsgStatus status) {

    }

    @Override
    public void resolved(MessagingAPI mApi, MsgResultAPI message) {
        CreateMessage createMsg = new CreateMessage();
        LoginMessage loginMsg = new LoginMessage();

        System.out.println(message.getChannel());

        if (message.getChannel().equals(Channels.PRIVATE + mApi.getUuid())) {
            LoginResponseData response = GsonWrapper.fromJson(message.getMessage(), LoginResponseData.class);
            if (response.isLoginSuccess()) {
                if (response.getInfo().equalsIgnoreCase("CREATE")) {
                    createMsg.setAccountCreationSuccess("Account Created Successfully!");
                    createMsg.setAccountCreation(true);
                    EventManager.notify(this, createMsg);
                    //System.out.println(msg.isAccountCreation());
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {
                    loginMsg.setLoginSuccess("Login Success!");
                    loginMsg.setLoginValidation(true);
                    EventManager.notify(this, loginMsg);
                }

            } else {
                //TODO: Remove test comments
                System.out.println("unsuccessful");
                if (response.getInfo().equalsIgnoreCase("CREATE")) {
                    createMsg.setAccountCreationUnSuccess("Account Creation Failed!");
                    createMsg.setAccountCreation(false);
                    EventManager.notify(this, createMsg);
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {
                    loginMsg.setLoginUnSuccess("Login Unsuccessful!");
                    loginMsg.setLoginValidation(false);
                    EventManager.notify(this, loginMsg);
                }
            }
        }
    }

    @Override
    public void rejected(Exception e) {
    }

    public static class CreateMessage {
        private String accountCreationSuccess;
        private String accountCreationUnSuccess;
        private boolean accountCreation;

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

        public boolean isAccountCreation() {
            return accountCreation;
        }

        public void setAccountCreation(boolean accountCreation) {
            this.accountCreation = accountCreation;
        }
    }

    public static class LoginMessage {
        private String loginSuccess;
        private String loginUnSuccess;
        private boolean loginValidation;

        public boolean isLoginValidation() {
            return loginValidation;
        }

        public void setLoginValidation(boolean loginValidation) {
            this.loginValidation = loginValidation;
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
    }
}

