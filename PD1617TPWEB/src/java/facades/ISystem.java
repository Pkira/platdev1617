
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

}
