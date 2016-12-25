
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class SessionUser implements ISessionUser {

    String Username;
    
    @EJB
    private IAuctionSystem AServer;
    
    
    @Override
    public boolean Login(String Username, String Password) {
        
        return AServer.LoginUser(Username, Password);
    }

    @Override
    public boolean LogOff() {
        return false;
    }

}
