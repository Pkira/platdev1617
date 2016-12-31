package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;



@Stateful
public class AdminBean implements IAdmin {

   String Username = "Admin";
    
    @EJB
    private ISystem AServer;
    
    
    @Override
    public boolean LogOff() {
        return AServer.LogOffUser(Username);
    }
    
    @Override
    public boolean CreateUsersBatch() {
        
        //admin        
        AServer.RegisterUser("admin", "admin");
        
        
        return true;
    }
    
}
