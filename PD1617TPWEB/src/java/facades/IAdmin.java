
package facades;

import entities.Notification;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import utils.ResultMessage;


@LocalBean
public interface IAdmin {
 
     boolean LogOff();
//     ArrayList<Notification> GetNotifications();
//     ResultMessage ActivateAccount(String Username);
//     ResultMessage ReActivateAccount(String Username);
//     ResultMessage SuspendAccount(String Username);
//     ResultMessage ChangeUserPassword(String Username, String Password);
//     void SaveState();
//     String SeeUserProfile(String Username);
}
