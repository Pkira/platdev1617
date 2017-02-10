
package controllers;

import entities.Notification;
import entities.User;
import facades.IAdmin;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<User> users = null;
        
        try {
            users = dAO.getEntityManager().createNamedQuery("User.findByAccountActivation").setParameter("accountActivation", false).getResultList();
        } catch (Exception e) {
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get users to activate", null));
            return new ArrayList<User>();
        }
        
        return users;
    }
    
    public List<User> getUsersSuspended()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<User> users = null;
        
        try {
            users = dAO.getEntityManager().createNamedQuery("User.findByAccountSuspension").setParameter("accountSuspension", true).getResultList();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get users to reactivate", null));
            return new ArrayList<User>();
        }
        
        return users;
    }
    
    public void activateUser(long UserId){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResultMessage ActivateAccount = adminFacade.ActivateAccount(UserId);
        
        if(ActivateAccount != ResultMessage.AccountActivated)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ActivateAccount.Message(), null));
    }
    
    public void reactivateUser(long UserId){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResultMessage ReActivateAccount = adminFacade.ReActivateAccount(UserId);
        
        if(ReActivateAccount != ResultMessage.AccountReActivated)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ReActivateAccount.Message(), null));
    }
    
    public List<Notification> getNotifications()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Notification> notifications = null;
        try {
            notifications = dAO.getEntityManager().createNamedQuery("Notification.findAll").getResultList();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get notifications", null));
            return new ArrayList<Notification>();
        }
        
        return notifications;
    }
    
}
