
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class User implements IUser {

    String Username;
    
    @EJB
    private IAuction Auction;
    
    
    @Override
    public boolean Login(String Username, String Password) {
        
        return Auction.LoginUser(Username, Password);
    }

    @Override
    public boolean LogOff() {
        return false;
    }

}
