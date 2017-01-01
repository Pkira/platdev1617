package pd1617tp;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import libraries.Notification;



@Stateful
public class AdminBean implements IAdmin {

   String Username = "Admin";
    
    @EJB
    private ISystem AServer;
    
    @Remove
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

    @Override
    public ArrayList<Notification> GetNotifications() {
        return AServer.GetNotifications();
    }
    
}
