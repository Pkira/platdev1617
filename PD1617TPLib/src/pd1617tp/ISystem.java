
package pd1617tp;

import libraries.ResultMessage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.jms.Message;
import libraries.NewsLetter;
import libraries.Notification;


@LocalBean

public interface ISystem {
    
     ResultMessage LoginUser(String Username, String Password);   
     ResultMessage RegisterUser(String Username, String Password);
     boolean LogOffUser(String Username);
     String SeeProfile(String Username);
     ResultMessage UpdateProfile(String Username, String Address, String password, String CurrentPass);
     String CheckBalance(String Username);
     ResultMessage LoadBalance(String Username, double increment);
     NewsLetter GetNewsletter();
     ArrayList CheckMessage(String Username);
     ResultMessage SendMessage(String Username, String Addressed, String Subject, String Message);
     ResultMessage CreateItem(String Username, String Item, String Category, String Desc, double Price, double BuyNow, String Budget);
     ArrayList SearchItem(String Item, String Category);
     ResultMessage CreateAuction(String Username, String Item, long id);
     ArrayList<Notification> GetNotifications();
     ResultMessage ActivateAccount(String Username);
     ResultMessage SuspendAccount(String Username);
     ResultMessage ReActivateAccount(String Username);
     ResultMessage ChangePassword(String Username, String Password);
     void SaveState();
     String SeeUserProfile(String Username);
     ResultMessage AskReactivation(String name);
     ArrayList ItemInSell(String username);
     List FollowItens(String username);
     ResultMessage FollowItem(Long Item, String Username);
}
