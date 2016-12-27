
package pd1617tp;

import libraries.ResultMessage;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.jms.Message;


@Stateful
public class UserBean implements IUser {

    String Username;
    
    @EJB
    private ISystem AServer;
    
    
    @Override
    public ResultMessage Login(String Username, String Password) {  
        
        ResultMessage result = AServer.LoginUser(Username, Password);
        
        if(result == ResultMessage.LoginSucess)
            this.Username = Username;
        
        return result;
    }

    @Override
    public boolean LogOff() {
        return AServer.LogOffUser(Username);
    }

    @Override
    public ResultMessage Register(String Username, String Password) {
        return AServer.RegisterUser(Username, Password);
    }
    
    @Override
    public String SeePerfil(){
        String msg = AServer.SeePerfil(Username);
        return msg;
    }
    
    @Override
    public ResultMessage UpdatePerfil(String Address)
    {
        return AServer.UpdatePerfil(Username, Address);
    }
    
    @Override
    public String CheckBalance(){
        String msg = AServer.CheckBalance(Username);
        return msg;
    }
    
    public ResultMessage LoadBalance(double increment){
        return AServer.LoadBalance(Username, increment);
    }

    @Override
    public boolean CreateUsersBatch() {
        
        //admin        
        AServer.RegisterUser("admin", "admin");
        
        
        return true;
    }
    
    @Override
    public ArrayList CheckMessage(){
        return AServer.CheckMessage(Username);
    }

    @Override
    public ResultMessage SendMessage(String Addressed, String Subject, String Message){
        return AServer.SendMessage(Username, Addressed, Subject, Message);
    }
}
