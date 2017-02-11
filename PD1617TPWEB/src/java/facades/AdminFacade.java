package facades;

import controllers.IDAO;
import entities.Category;
import entities.Notification;
import entities.User;
import java.util.ArrayList;
import java.util.List;
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
    public List<Notification> GetNotifications() {
        
        List<Notification> notifications;
                
        try {
            notifications = dAO.getEntityManager().createNamedQuery("Notification.findAll").getResultList();
        } catch (Exception e) {
            return new ArrayList<Notification>();
        }
        
        return notifications;
    }

    @Override
    public ResultMessage ActivateAccount(Long UserId) {
    
        
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
            
            try {
                dAO.getEntityManager().persist(user);
            } catch (Exception e) {
                return ResultMessage.AccountNotActivated;
            }

            Newsletter.addNewsletterItem("New user registed in system, welcome " + Username);          

            return ResultMessage.AccountActivated;
        }
    }
    
    @Override
    public ResultMessage ReActivateAccount(Long UserId) {
   
        
        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
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
            
            try {
                dAO.getEntityManager().persist(user);
            } catch (Exception e) {
                return ResultMessage.AccountNotActivated;
            }
            
            Newsletter.addNewsletterItem("Removed suspension for User " + Username);
            
            return ResultMessage.AccountReActivated;

    }
    
    @Override
    public ResultMessage SuspendAccount(Long UserId) {

        if(UserId == null)
            return ResultMessage.LoginInvalidUsername;
        
        
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
            
            try {
                dAO.getEntityManager().persist(user);
            } catch (Exception e) {
                return ResultMessage.AccountNotSuspended;
            }
                
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
        
        try {
            dAO.getEntityManager().persist(user);
        } catch (Exception e) {
            return ResultMessage.AccountPasswordNotChanged;
        }
        
        return ResultMessage.AccountPasswordChanged;

    }
    
    @Override
    public User SeeUserProfile(long UserId){
         
        User user = new User();
        
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return null;
        }
        
        return user;

    }
    
    @Override
    public List<User> GetAllUsersToActivate()
    {
        List<User> users = null;
        
        try {
            users = dAO.getEntityManager().createNamedQuery("User.findByAccountActivation").setParameter("accountActivation", false).getResultList();
        } catch (Exception e) {
            return new ArrayList<User>();
        }
        
        return users;
    }
    
    @Override
    public List<User> GetAllUsersSuspended()
    {
        
        List<User> users = null;
        
        try {
            users = dAO.getEntityManager().createNamedQuery("User.findByAccountSuspension").setParameter("accountSuspension", true).getResultList();
        } catch (Exception e) {
            return new ArrayList<User>();
        }
        
        return users;
    }
    
    @Override
    public List<Category> GetAllCategories(){
    
        List<Category> categories = null;
        
        try {
            categories = dAO.getEntityManager().createNamedQuery("Category.findAll").getResultList();
        } catch (Exception e) {
            return new ArrayList<Category>();
        }
        
        return categories;
    }
    
    @Override
    public Category GetCategoryInfo(long CategoryId){
    
        Category category = null;
        
        try {
            category =  (Category)dAO.getEntityManager().createNamedQuery("Category.findById").setParameter("id", CategoryId).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
        return category;
    }
    
    @Override
    public ResultMessage UpdateCategory(long CategoryId, String CategoryName, String Description){
    
        Category category = null;
        
        try {
            category = (Category) dAO.getEntityManager().createNamedQuery("Category.findById").setParameter("id", CategoryId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.CategoryErrorUpdate;
        }
        
        List<Category> categories = null;
        try {
            categories = (List<Category>) dAO.getEntityManager().createNamedQuery("Category.findByName").setParameter("name", CategoryName).getResultList();
        } catch (Exception e) {
            return ResultMessage.CategoryErrorAdd;
        }
        
        if(categories != null && categories.size() > 1)
            return ResultMessage.CategoryAllreadyExists;
        
        category.setName(CategoryName);
        category.setDescription(Description);
        
        try {
            dAO.getEntityManager().persist(category);
        } catch (Exception e) {
            return ResultMessage.CategoryErrorUpdate;
        }
        
        return ResultMessage.CategoryUpdated;
    }
    
    @Override
    public ResultMessage AddCategory(String Name, String Description){
    
        Category category = null;
        
        try {
            category = (Category) dAO.getEntityManager().createNamedQuery("Category.findByName").setParameter("name", Name).getSingleResult();
        } 
        catch (Exception e) {
            
        }
        
        if(category != null)
            return ResultMessage.CategoryAllreadyExists;
        
        category = new Category();
        category.setId((long) -1);
        category.setName(Name);
        category.setDescription(Description);
        
        try {
            dAO.getEntityManager().persist(category);
        } catch (Exception e) {
            return ResultMessage.CategoryErrorAdd;
        }
        
        return ResultMessage.CategoryAdded;
    }
}
