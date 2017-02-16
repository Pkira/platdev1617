
package controllers;

import entities.Category;
import entities.Notification;
import entities.User;
import facades.IAdmin;
import facades.IUser;
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
    
    @EJB
    private IUser userFacade;
    
    private User userToEdit;
    
    private long categoryid;
    private String categoryname;
    private String categorydescription;

    public AdminController() {       
        
    }
    
    public List<User> getUsersToActivate()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<User> users = null;
        
        try {
            users = adminFacade.GetAllUsersToActivate();
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
            users = users = adminFacade.GetAllUsersSuspended();
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
            notifications = adminFacade.GetNotifications();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get notifications", null));
            return new ArrayList<Notification>();
        }
        
        return notifications;
    }
    
    public List<User> getAllUsers()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<User> users = null;
        
        try {
            users = userFacade.getAll();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get users list", null));
            return new ArrayList<User>();
        }
        
        return users;
    }
    
    public String changeUserInfo(long UserId){
        
        userToEdit = adminFacade.SeeUserProfile(UserId);
        //return "AdminOptions.xhtml";
        return "AdminChangeUserInfo.xhtml";
    }
    
    public String changeCategoryInfo(long CategoryId){
        
        Category category = adminFacade.GetCategoryInfo(CategoryId);
        
        this.categoryid = category.getId();
        this.categoryname = category.getName();
        this.categorydescription = category.getDescription();
        
        return "AdminCategoryDetails.xhtml";
    }
    
    public void updateUserProfile(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResultMessage result = adminFacade.ChangeUserPassword(userToEdit.getId(), userToEdit.getPassword());
        
        if(result != ResultMessage.AccountPasswordChanged)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
    }
    
    public String suspendUserAccount(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResultMessage result = adminFacade.SuspendAccount(userToEdit.getId());
        
        if(result != ResultMessage.AccountSuspended)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        
        return "AdminOptions.xhtml";
    }
    
    public List<Category> getCategories()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Category> categories = null;
        
        try {
            categories = adminFacade.GetAllCategories();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResultMessage.CategoriesNotFound.Message(), null));
        }
        
        return categories;
    }
    
    public void addNewCategory(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            
            ResultMessage result = adminFacade.AddCategory(categoryname, categorydescription);
            
            if(result != ResultMessage.CategoryAdded)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResultMessage.CategoryErrorAdd.Message(), null));
        }
        
        this.categoryid = 0;
        this.categoryname = null;
        this.categorydescription = null;

    }
    
     public String updateCategory(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            
            ResultMessage result = adminFacade.UpdateCategory(categoryid,categoryname, categorydescription);
            
            if(result != ResultMessage.CategoryUpdated)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            else
            {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));                
            }
                
            
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResultMessage.CategoryAllreadyExists.Message(), null));
        }
        
        this.categoryid = 0;
        this.categoryname = null;
        this.categorydescription = null;
        
        
        return "AdminCategories.xhtml";

    }

    public User getUserToEdit() {
        return userToEdit;
    }

    public void setUserToEdit(User userToEdit) {
        this.userToEdit = userToEdit;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorydescription() {
        return categorydescription;
    }

    public void setCategorydescription(String categorydescription) {
        this.categorydescription = categorydescription;
    }
    
    
    
}
