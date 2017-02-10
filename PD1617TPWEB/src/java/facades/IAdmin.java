
package facades;

import entities.Notification;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import utils.ResultMessage;


@LocalBean
public interface IAdmin {
 
     boolean LogOff();
     List<Notification> GetNotifications();
     ResultMessage ActivateAccount(Long UserId);
     ResultMessage ReActivateAccount(Long UserId);
     ResultMessage SuspendAccount(Long UserId);
     ResultMessage ChangeUserPassword(Long UserId, String Password);
     User SeeUserProfile(long UserId);
     List<User> GetAllUsersToActivate();
     List<User> GetAllUsersSuspended();
}
