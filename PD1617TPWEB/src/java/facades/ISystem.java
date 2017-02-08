
package facades;

import utils.ResultMessage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.jms.Message;
import entities.Newsletter;
import entities.Notification;
import entities.User;


@LocalBean

public interface ISystem {
    
     ResultMessage AddUserToActiveList(User user);   
     boolean CheckUserState(String Username);
     boolean LogOffUserFromActiveList(String Username);

//     ResultMessage CreateItem(String Username, String Item, String Category, String Desc, double Price, double BuyNow, String Budget);
//     ArrayList SearchItem(String Item, String Category);
//     ResultMessage CreateAuction(String Username, String Item, long id);
//     ArrayList<Notification> GetNotifications();
//     ResultMessage ActivateAccount(String Username);
//     ResultMessage SuspendAccount(String Username);
//     ResultMessage ReActivateAccount(String Username);
//     ResultMessage ChangePassword(String Username, String Password);
//     void SaveState();
//     String SeeUserProfile(String Username);
//     ResultMessage AskReactivation(String name);
//     ArrayList ItemInSell(String username);
//     List FollowItens(String username);
//     ResultMessage FollowItem(Long Item, String Username);
//     ResultMessage CancelFollowItem(Long Item, String Username);
//     ResultMessage ReportItem(String name, Long Id);
//     ResultMessage ReportUser(String name, String reported);
}
