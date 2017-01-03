
package libraries;


public enum ResultMessage {
    LoginSucess("[INFO] Logged with sucess"),
    LoginInvalidUsername("[INFO] Invalid Username specified"),
    LoginInvalid("[INFO] Invalid username or password"),
    LoginUserNotFound("[INFO] User not found or invalid username/password"),
    LoginAllreadyLogged("[INFO] User already logged"),
    RegisterAllreadyExist("[INFO] Username already exist"),
    RegisterSucess("[INFO] User registed successfully in the system"),
    RegisterInvalid("[INFO] Invalid username or password specified"),
    UpdateProfileValid("[INFO] Update Successfull"),
    UpdateProfileInvalid("[INFO] Update invalid"),
    UpdateProfileWrongPass("[INFO] Your current password is wrong"),
    LoadBalanceValid("[INFO] Your load balance was successfully made"),
    LoadBalanceInvalid("[INFO] Load balance invalid"),
    SendMessageSuccess("[INFO] Your message was sent with success"),
    SendMessageUnsuccess("[Error] Something wrong happen to your message!"),
    SendMessageNoUser("[INFO] The user you pretend send the message does not exist"),
    CreateItemSuccess("[INFO] The was created with success"),
    CreateItemUnsuccess("[INFO] The item creation have fail"),
    AuctionCreated("[INFO] Auction created with success"),
    AuctionNotCreated("[INFO] You don´t have this Item or the Item does't exist"),
    AccountActivated("[INFO] Account successfully activated"),
    AccountAllreadyActivated("[INFO] Account as allready Activated"),
    AccountNotActivated("[INFO] Account not activated yet"),
    AccountSuspended("[INFO] Account suspended"),
    AccountNotSuspended("[INFO] Account as not suspended"),
    AccountAllreadySuspended("[INFO] Account as allready suspended"),
    AccountReActivated("[INFO] Account re-activated"),
    AccountPasswordChanged("[INFO] Password changed successfully"),
    AccountReactivation("[INFO] The request for the account reactivation was sent to the Admin, please wait a while."),
    AccountReactivationFail("[INFO] This accout is not suspend"),
    AccountReactivationNoUser("[INFO] This account doesn't exist");
    
    
    private String Message;
    
    ResultMessage(String Message) {this.Message = Message;}
    public String Message() {return this.Message;}
}

