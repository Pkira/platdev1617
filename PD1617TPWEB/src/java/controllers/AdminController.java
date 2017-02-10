
package controllers;

import entities.Notification;
import entities.User;
import facades.IAdmin;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import utils.ResultMessage;


@Named(value = "adminController")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    private IDAO dAO;
    
    @EJB
    private IAdmin adminFacade;

    public AdminController() {       
        
    }
    
    public List<User> getUsersToActivate()
    {
        List<User> users = dAO.getEntityManager().createNamedQuery("User.findByAccountActivation").setParameter("accountActivation",false).getResultList();
        
        return users;
    }
    
    public List<User> getUsersSuspended()
    {
        List<User> users = dAO.getEntityManager().createNamedQuery("User.findByAccountSuspension").setParameter("accountSuspension",true).getResultList();
        
        return users;
    }
    
    public void activateUser(long UserId){
        ResultMessage ActivateAccount = adminFacade.ActivateAccount(UserId);
    }
    
    public void reactivateUser(long UserId){
        ResultMessage ReActivateAccount = adminFacade.ReActivateAccount(UserId);
    }
    
    public List<Notification> getNotifications()
    {
        List<Notification> notifications = dAO.getEntityManager().createNamedQuery("Notification.findAll").getResultList();
        
        return notifications;
    }
    
}
