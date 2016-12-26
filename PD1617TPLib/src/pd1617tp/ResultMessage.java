
package pd1617tp;


public enum ResultMessage {
    LoginSucess("Logged with sucess"),
    LoginInvalid("Invalid username or password"),
    LoginUserNotFound("User not found or invalid username/password"),
    LoginAllreadyLogged("User already logged"),
    RegisterAllreadyExist("Username already exist"),
    RegisterSucess("User registed successfully in the system"),
    RegisterInvalid("Invalid username or password specified"),
    UpdatePerfilValid("Update Successfull"),
    UpdatePerfilInvalid("Update invalid"),
    LoadBalanceValid("Your load balance was successfully made"),
    LoadBalanceInvalid("Load balance invalid");
    
    private String Message;
    
    ResultMessage(String Message) {this.Message = Message;}
    public String Message() {return this.Message;}
}

