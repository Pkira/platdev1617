package facades;

import controllers.IDAO;
import entities.Newsletter;
import entities.Notification;
import entities.User;
import java.text.ParseException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import utils.ResultMessage;


@Stateful
public class AdminFacade implements IAdmin {

   String Username = "Admin";
   
   @EJB
   private IDAO dAO; 
    
   @EJB
   private INewsLetter Newsletter;
   
   @EJB
   private ISystem AServer;
    
    
    @Remove
    @Override
    public boolean LogOff() {
        return AServer.LogOffUserFromActiveList(Username);
    }   

    @Override
    public ArrayList<Notification> GetNotifications() {
        
        return (ArrayList<Notification>) dAO.getEntityManager().createNamedQuery("Notification.findByStatus").setParameter("status", false).getResultList();
    }

    @Override
    public ResultMessage ActivateAccount(Long UserId) {
        //validate input
        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
        User user = new User();
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return ResultMessage.LoginInvalidUsername;
        }
       
            boolean isActivated = user.getAccountActivation();
            if(isActivated)    
                return ResultMessage.AccountAllreadyActivated;
            else
            {
                user.setAccountActivation(true);
                
                Newsletter.addNewsletterItem("New user registed in system, welcome " + Username);
                
                dAO.getEntityManager().persist(user);
                
                return ResultMessage.AccountActivated;
            }
    }
    
    @Override
    public ResultMessage ReActivateAccount(Long UserId) {
        //validate input
        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from BD
        User user = new User();
        
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return ResultMessage.LoginInvalidUsername;
        }
        
            boolean isSuspended = user.getAccountSuspension();
            
            if(!isSuspended)    
                return ResultMessage.AccountNotSuspended;
            
            boolean isActivated = user.getAccountActivation();
            if(!isActivated)    
                return ResultMessage.AccountNotActivated;
            
            user.setAccountSuspension(false);
            
            Newsletter.addNewsletterItem("Removed suspension for User " + Username);
            
            dAO.getEntityManager().persist(user);
            
            return ResultMessage.AccountReActivated;

    }
    
    @Override
    public ResultMessage SuspendAccount(Long UserId) {
//        validate input
        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from BD
        User user = new User();
        
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return ResultMessage.LoginInvalidUsername;
        }
        
        boolean isSuspended = user.getAccountSuspension();
        
        if(isSuspended)    
            return ResultMessage.AccountAllreadySuspended;
        else
        {
            user.setAccountSuspension(true);

            Newsletter.addNewsletterItem("User " + Username + " as been suspended");
            
            dAO.getEntityManager().persist(user);
                
            return ResultMessage.AccountSuspended;
        }

    }

    @Override
    public ResultMessage ChangeUserPassword(Long UserId, String Password) {
          
        //validate input
        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from BD
        User user = new User();
        
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return ResultMessage.LoginInvalidUsername;
        }
        
        user.setPassword(Password);       
        
        dAO.getEntityManager().persist(user);
        
        return ResultMessage.AccountPasswordChanged;

    }
    
    @Override
    public User SeeUserProfile(String User){
         
        User user = new User();
        
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", User).getSingleResult();
        }catch(Exception e){
             return null;
        }
        
        return user;

    }
}
