/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp;

import java.util.ArrayList;
import javax.ejb.Remote;
import libraries.Notification;
import libraries.ResultMessage;

/**
 *
 * @author Caike
 */
@Remote
public interface IAdmin {
 
     boolean CreateUsersBatch();
     boolean LogOff();
     ArrayList<Notification> GetNotifications();
     ResultMessage ActivateAccount(String Username);
     ResultMessage ReActivateAccount(String Username);
     ResultMessage SuspendAccount(String Username);
    
}
