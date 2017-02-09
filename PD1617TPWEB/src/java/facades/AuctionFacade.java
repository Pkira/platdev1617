
package facades;

import controllers.IDAO;
import entities.Auction;
import entities.Item;
import entities.Newsletter;
import entities.User;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import utils.ResultMessage;


@Stateful
public class AuctionFacade implements IAuction {

    long ID;
    
    @EJB
    private IDAO dAO;
    
    @EJB
    private INewsLetter Newsletter;
    
    @EJB
    private ISystem AServer;
    
     public ResultMessage CreateAuction(Long UserId, Long ItemId){
       
          //validate input
        if (UserId != null || ItemId != null) {
            return ResultMessage.AuctionNotCreated;
        }

        //get user from BD
        User user = new User();
        try{
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        }catch(Exception e){
             return ResultMessage.LoginInvalidUsername;
        }

        //get Item from BD

        Item item = new Item();

        if (ItemId > 0) {
            try{
                item = (Item) dAO.getEntityManager().createNamedQuery("Item.OwnerAndId").setParameter("ownerid", user).setParameter("id", ItemId).getSingleResult();
            }catch(Exception ex){
                return ResultMessage.ItemNotExist;
            }
        }
        
        Auction auction = new Auction();
               
        auction.setId((long)-1);
        auction.setItemid(item);
        auction.setItemstate(0);
        auction.setAuctionstate(1);
        
        Date enddate = new Date();        
        Calendar c = Calendar.getInstance();
        c.setTime(enddate);
        c.add(Calendar.DATE, (int) item.getAuctionduration());
        enddate = c.getTime();
        auction.setEnddate(enddate);
        
        Newsletter.addNewsletterItem("New Auction created: Id[" + auction.getId() + "]");
        
        dAO.getEntityManager().persist(auction);
        
        return ResultMessage.AuctionCreated;
    }
     
}
