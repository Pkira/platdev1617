//
package facades;

import controllers.IDAO;
import entities.Notification;
import entities.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import utils.ResultMessage;



@Stateless
public class VisitorFacade implements IVisitor {

    @EJB
    private IDAO dAO;

    @Override
    public ResultMessage AskReactivation(String Username){
         
         String message = "A visitor ask for the reactivation of the account with the Username: " + Username;
         
         User user = (User)dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", Username).getSingleResult();
         
         Notification notification = new Notification();
         notification.setId((long)-1);
         notification.setMessage(message);
         notification.setStatus(0);
         notification.setUserid(user);
         
         dAO.getEntityManager().persist(notification);
         
        return ResultMessage.AccountReactivationNoUser;
    }
}
