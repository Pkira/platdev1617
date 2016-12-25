
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;


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

}
