package facades;

import entities.Newsletter;
import entities.Notification;
import entities.User;
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

    @Override
    public ArrayList<Notification> GetNotifications() {
        return null;
    }

    @Override
    public ResultMessage ActivateAccount(String Username) {
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isActivated = user.getAccountActivation();
//            if(isActivated)    
//                return ResultMessage.AccountAllreadyActivated;
//            else
//            {
//                user.setAccountActivation(true);
//                
//                Newsletter.addNewsToList(new NewsLetterItem("New user registed in system, welcome " + Username));
//                
//                return ResultMessage.AccountActivated;
////            }
//        }
//        else
            return ResultMessage.LoginInvalidUsername;
    }
    
    @Override
    public ResultMessage ReActivateAccount(String Username) {
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isSuspended = user.isAccountSuspension();
//            if(!isSuspended)    
//                return ResultMessage.AccountNotSuspended;
//            
//            boolean isActivated = user.getAccountActivation();
//            if(!isActivated)    
//                return ResultMessage.AccountNotActivated;
//            
//            user.setAccountSuspension(false);
//            
//            Newsletter.addNewsToList(new NewsLetterItem("Removed suspension for User " + Username));
//            
//            return ResultMessage.AccountReActivated;
//            
//        }
//        else
            return ResultMessage.LoginInvalidUsername;
    }
    
    @Override
    public ResultMessage SuspendAccount(String Username) {
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isSuspended = user.isAccountSuspension();
//            if(isSuspended)    
//                return ResultMessage.AccountAllreadySuspended;
//            else
//            {
//                user.setAccountSuspension(true);
//                
//                Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " as been suspended"));
//                
//                return ResultMessage.AccountSuspended;
//            }
//        }
//        else
            return ResultMessage.LoginInvalidUsername;
    }

    @Override
    public ResultMessage ChangeUserPassword(String Username, String Password) {
//          
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            user.setPassword(Password);        
//            return ResultMessage.AccountPasswordChanged;
//            
//        }
//        else
            return ResultMessage.LoginInvalidUsername;
    }
    
    @Override
    public String SeeUserProfile(String User){
//         User user = Users.get(Username);
//        
//        if(user != null)
//            return "Username:  " + user.getName() + "\nAddress:  " + user.getAddress() + "\nBalance:  " + user.getBalance() + "\n";
//        else
            return "User doesn't exist";
    }
}
