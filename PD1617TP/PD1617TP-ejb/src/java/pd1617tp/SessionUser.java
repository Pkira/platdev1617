
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import libraries.NewsLetter;


@Stateful
public class SessionUser implements ISessionUser {

    String Username;
    
    @EJB
    private IAuctionSystem AServer;
    
    
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
    public NewsLetter GetNewsletter() {
        return AServer.GetNewsletter();
    }

}
