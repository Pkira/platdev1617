
package pd1617tp;


public enum ResultMessage {
    LoginSucess("Logged with sucess"),
    LoginInvalid("Invalid username or password"),
    LoginUserNotFound("User not found or invalid username/password"),
    LoginAllreadyLogged("User already logged"),
    RegisterAllreadyExist("Username already exist"),
    RegisterSucess("User registed successfully in the system"),
    RegisterInvalid("Invalid username or password specified"),
    UpdatePerfilValid("[INFO] Update Successfull"),
    UpdatePerfilInvalid("[INFO] Update invalid"),
    LoadBalanceValid("[INFO] Your load balance was successfully made"),
    LoadBalanceInvalid("[INFO] Load balance invalid"),
    SendMessageSuccess("[INFO] Your message was sent with success"),
    SendMessageUnsuccess("[Error] Something wrong happen to your message!"),
    SendMessageNoUser("[INFO] The user you pretend send the message does not exist");
    
    private String Message;
    
    ResultMessage(String Message) {this.Message = Message;}
    public String Message() {return this.Message;}
}

