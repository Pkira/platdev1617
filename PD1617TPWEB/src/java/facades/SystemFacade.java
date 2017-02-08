
package facades;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import utils.ResultMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import entities.Auction;
import entities.Item;
import entities.Newsletter;
import entities.User;
import entities.Message;
import entities.Notification;
import javax.ejb.EJB;


@Singleton
public class SystemFacade implements ISystem {
   
    private HashMap<String,User> Users = new HashMap<>();
    private HashMap<Integer,Message> Messages = new HashMap<>();
    private HashMap<Long,Notification> Notifications = new HashMap<>();
      
    @Override
    public ResultMessage AddUserToActiveList(User user) {
                
        //validate input
        if(user == null )
            return ResultMessage.LoginInvalid;
        
        //get user from list
        User isAlreadyActive = Users.get(user.getUsername());
        
        //check if user is already logged
        if(isAlreadyActive != null) 
        {
            boolean isLogged = user.isLogged();
            if(isLogged)    
                return ResultMessage.LoginAllreadyLogged;
            
            boolean isUnverified = user.getAccountActivation();
            if(!isUnverified)    
                return ResultMessage.AccountNotActivated;
            
            boolean isSuspended = user.getAccountSuspension();
            if(isSuspended)    
                return ResultMessage.AccountSuspended;
        }
        else
        {
            user.setLogged(true);
            user.setLastAction();
            Users.put(user.getUsername(),user);

            return ResultMessage.LoginSucess;
        }         

        return ResultMessage.LoginUserNotFound;
        

    }
    
    @Override
    public boolean CheckUserState(String Username) {
                
        //validate input
        if(Username == null )
            return false;
        
        //get user from list
        User isAlreadyActive = Users.get(Username);
        
        //check if user is already logged
        if(isAlreadyActive != null) 
        {
            return true;           
        }
        else
        {
            return false;
        }         

    }
        
    @Override
    public boolean LogOffUserFromActiveList(String Username){
        //validate input
        if(Username == null)
            return false;
        
        //get user from list
        User user = Users.get(Username);
        
        // if exists 
        if(user != null)
        {
            Users.remove(Username);
            return true;
        }

        return false;
        
    }
 
    
}
