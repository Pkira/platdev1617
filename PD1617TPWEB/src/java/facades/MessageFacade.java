
package facades;

import controllers.IDAO;
import entities.Message;
import entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import utils.ResultMessage;


@Stateless
public class MessageFacade implements IMessage {

    @EJB
    private IDAO dAO;

    @Override
    public List<Message> GetMessagesToUser(long UserId) {
        
        User user = null;
        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return new ArrayList<Message>();
        }
        
        List<Message> messages = null;
        try {
            messages = dAO.getEntityManager().createNamedQuery("Message.findByTo").setParameter("user", user).getResultList();
        } catch (Exception e) {
            return new ArrayList<Message>();
        }
        
        return messages;
    }   

    @Override
    public ResultMessage SendMessage(long FromUserId, String ToUsername, String Subject, String Message){
        
        User from = null;
        try {
            from = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", FromUserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.SendMessageUnsuccess;
        }
        
        User to = null;
        try {
            to = (User) dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", ToUsername).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.SendMessageUnsuccess;
        }
        
        
        Message newMessage = new Message();
        newMessage.setId((long) -1);
        newMessage.setFromid(from);
        newMessage.setToid(to);
        newMessage.setMessage(Message);
        newMessage.setSubject(Subject);
        newMessage.setCreationdate(new Date());
        
        try {
            dAO.getEntityManager().persist(newMessage);
        } catch (Exception e) {
            return ResultMessage.SendMessageUnsuccess;
        }
        
        return ResultMessage.SendMessageSuccess;
    }

    
}
