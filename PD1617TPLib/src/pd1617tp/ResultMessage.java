
package pd1617tp;


public enum ResultMessage {
    LoginSucess("Logged with sucess"),
    LoginInvalid("Invalid login, invalid username or password"),
    LoginAllreadyLogged("User already logged"),
    RegisterAllreadyExist("Username already exist"),
    RegisterSucess("User registed successfully in the system"),
    RegisterInvalid("Invalid username or password specified");
    
    private String Message;
    
    ResultMessage(String Message) {this.Message = Message;}
    public String Message() {return this.Message;}
}

