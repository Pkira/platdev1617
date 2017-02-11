
package facades;

import entities.Category;
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
     List<Category> GetAllCategories();
     ResultMessage UpdateCategory(long CategoryId, String CategoryName, String Description);
     ResultMessage AddCategory(String CategoryName, String Description);
     Category GetCategoryInfo(long CategoryId);
}
