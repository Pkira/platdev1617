
package controllers;

import entities.Message;
import facades.IMessage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import utils.ResultMessage;


@Named(value = "messageController")
@RequestScoped
public class MessageController {

    @EJB
    private IMessage messageFacade;
    
    private String tousername;
    private String message;
    private String subject;
    
    public MessageController() {
        
    }
    
    public List<Message> getUserMessages(long UserId)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Message> messages = new ArrayList<Message>();
        try {
            messages = messageFacade.GetMessagesToUser(UserId);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get user messages", null));
        }
        
        return messages;
    }
    
    public void sendMessage(long FromId)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            
            ResultMessage result = messageFacade.SendMessage(FromId,tousername,subject,message);
            
            if(result != ResultMessage.SendMessageSuccess)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            else
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
            
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get user messages", null));
        }

    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
    
}
