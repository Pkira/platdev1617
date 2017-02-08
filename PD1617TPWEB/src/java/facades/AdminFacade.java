package facades;

import entities.Notification;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import utils.ResultMessage;


@Stateful
public class AdminFacade implements IAdmin {

   String Username = "Admin";
    
    @EJB
    private ISystem AServer;
    
    @Remove
    @Override
    public boolean LogOff() {
        return AServer.LogOffUserFromActiveList(Username);
    }   
//
//    @Override
//    public ArrayList<Notification> GetNotifications() {
//        return AServer.GetNotifications();
//    }
//
//    @Override
//    public ResultMessage ActivateAccount(String Username) {
//        return AServer.ActivateAccount(Username);
//    }
//    
//    @Override
//    public ResultMessage ReActivateAccount(String Username) {
//        return AServer.ReActivateAccount(Username);
//    }
//    
//    @Override
//    public ResultMessage SuspendAccount(String Username) {
//        return AServer.SuspendAccount(Username);
//    }
//
//    @Override
//    public ResultMessage ChangeUserPassword(String Username, String Password) {
//        return AServer.ChangePassword(Username, Password);
//    }
//    
//    @Override
//    public void SaveState() {
//        AServer.SaveState();
//    }
//    
//    @Override
//    public String SeeUserProfile(String User){
//        return AServer.SeeUserProfile(User);
//    }
}
