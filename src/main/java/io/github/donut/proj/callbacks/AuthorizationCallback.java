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

/**
 * This class is the used as a callback that will have the data that is returned from the authentication service.
 * This class implements the {@code}ISubject class as we need to pass the data back to the controllers using the
 * {@code}EventManager.notify method.
 * @author Utsav Parajuli
 */
public class AuthorizationCallback implements ISubscribeCallback, ISubject{

    /**
     * Constructor for the AuthorizationCallback class. Register the instance of CreateAccountController, and
     * LoginController as those methods are listening from this method.
     * @author Utsav Parajuli
     */
    public AuthorizationCallback() {
        EventManager.register(this, CreateAccountController.getInstance());
        EventManager.register(this, LoginController.getInstance());
    }

    @Override
    public void status(MessagingAPI mApi, MsgStatus status) {

    }

    /**
     * This method will get the response returned back from the Authentication microservice. We will relay the
     * appropriate message back to the part of the client that sent the message.
     * @param mApi : The api instance
     * @param message : the result api that contains message
     * @author Utsav Parajuli
     */
    @Override
    public void resolved(MessagingAPI mApi, MsgResultAPI message) {
        //instances of appropriate message data classes
        CreateMessage createMsg = new CreateMessage();
        LoginMessage  loginMsg  = new LoginMessage();

        //checking if we are getting the message from the particular instance of api
        if (message.getChannel().equals(Channels.PRIVATE + mApi.getUuid())) {
            LoginResponseData response = GsonWrapper.fromJson(message.getMessage(), LoginResponseData.class);

            if (response.isLoginSuccess()) {                                        //if the login/create was successful
                if (response.getInfo().equalsIgnoreCase("CREATE")) {    //checking if the message was create
                    createMsg.setAccountCreationSuccess("Account Created Successfully!");
                    createMsg.setAccountCreation(true);
                    EventManager.notify(this, createMsg);

                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {   //checking if message was login
                    loginMsg.setLoginSuccess("Login Success!");
                    loginMsg.setLoginValidation(true);
                    EventManager.notify(this, loginMsg);
                    // needed to prevent memory leak
                    EventManager.removeAllObserver(this);
                }

            } else {                                                                 //else the login was unsuccessful
                if (response.getInfo().equalsIgnoreCase("CREATE")) {     //checking if the message was create
                    createMsg.setAccountCreationUnSuccess("Account Creation Failed!");
                    createMsg.setAccountCreation(false);
                    EventManager.notify(this, createMsg);
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {   //checking if message was login
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

    /**
     * This class contains the appropriate display message and values if the createAccount message was successful or
     * unsuccessful
     * @author Utsav Parajuli
     */
    public static class CreateMessage {
        private String  accountCreationSuccess;
        private String  accountCreationUnSuccess;
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

    /**
     * This class contains the appropriate display message and values if the login/validation message was successful or
     * unsuccessful
     * @author Utsav Parajuli
     */
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