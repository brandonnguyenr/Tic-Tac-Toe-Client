package io.github.donut.proj.callbacks;

import io.github.API.ISubscribeCallback;
import io.github.API.MessagingAPI;
import io.github.API.messagedata.MsgResultAPI;
import io.github.API.messagedata.MsgStatus;
import io.github.API.utils.GsonWrapper;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginResponseData;
import io.github.donut.proj.listener.ISubject;

import java.util.function.Consumer;

/**
 * This class is the used as a callback that will have the data that is returned from the authentication service.
 * This class implements the {@code}ISubject class as we need to pass the data back to the controllers using the
 * {@code}EventManager.notify method.
 * @author Utsav Parajuli
 */
public class AuthorizationCallback implements ISubscribeCallback, ISubject{

    private final Consumer<LoginResponseData> resolved;
    private final Runnable rejected;
    /**
     * Constructor for the AuthorizationCallback class. Register the instance of CreateAccountController, and
     * LoginController as those methods are listening from this method.
     * @author Utsav Parajuli
     */
    public AuthorizationCallback(Consumer<LoginResponseData> resolved, Runnable rejected) {
        this.resolved = resolved;
        this.rejected = rejected;
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
        //checking if we are getting the message from the particular instance of api
        if (message.getChannel().equals(Channels.PRIVATE + mApi.getUuid())) {
            LoginResponseData response = GsonWrapper.fromJson(message.getMessage(), LoginResponseData.class);

            if (response.isLoginSuccess()) {                                        //if the login/create was successful
                if (response.getInfo().equalsIgnoreCase("CREATE")) {    //checking if the message was create
                    this.resolved.accept(response);
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {   //checking if message was login
                    this.resolved.accept(response);
                }

            } else {                                                                 //else the login was unsuccessful
                if (response.getInfo().equalsIgnoreCase("CREATE")) {     //checking if the message was create
                    this.rejected.run();
                } else if (response.getInfo().equalsIgnoreCase("VALIDATE")) {   //checking if message was login
                    this.rejected.run();
                }
            }
        }
    }

    @Override
    public void rejected(Exception e) {
    }
}