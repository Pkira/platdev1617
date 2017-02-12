//
package facades;

import controllers.IDAO;
import entities.Auction;
import entities.Item;
import entities.Notification;
import entities.User;
import java.util.ArrayList;
import java.util.List;
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
    
    @Override
    public List<Item> SeeLastSellItens(){
        
        List<Auction> LastAuctions = new ArrayList();        
        List<Item> LastSells = new ArrayList();
        
        
        try{
           LastAuctions = dAO.getEntityManager().createNamedQuery("Auction.findLastItemSell").setMaxResults(3).getResultList();
        }catch(Exception e){
            return LastSells;
        }
        
        for(Auction i : LastAuctions)
            LastSells.add(i.getItemid());
        
        return LastSells;
    }

    private Object setMaxResults(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
