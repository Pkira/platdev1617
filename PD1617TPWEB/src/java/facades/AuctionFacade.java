
package facades;

import controllers.IDAO;
import entities.Auction;
import entities.Item;
import entities.Message;
import entities.Newsletter;
import entities.User;
import entities.UserItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    public List<Auction> GetAll(){
    
        List<Auction> auctions = null;
        try {
            auctions = dAO.getEntityManager().createNamedQuery("Auction.findAll").getResultList();
        } catch (Exception e) {
            return new ArrayList<Auction>();
        }
        
        return auctions;
    }
    
      @Override
      public ResultMessage CreateAuction(Long ItemId){
       
        //get Item from BD

        Item item = new Item();

        try{
            
            item = (Item)dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        }catch(Exception e){
            return ResultMessage.ItemNotExist;
        }
        
        Auction auction = new Auction();
        
        //verify if already exist a auction for this item
        try{
            auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findByItem").setParameter("itemid", item).getSingleResult();
        }catch(Exception e){
        }
        if(auction.getId() == null){
            // if not create the auction.
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
            
            User user = null;
            
            try {

                user = item.getOwnerid();

            } catch (Exception ex) {
                return ResultMessage.UserNotExist;
            }
            
            Newsletter.addNewsletterItem("New Auction created: Id[" + auction.getId() + "]");

            dAO.getEntityManager().persist(auction);
            
            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " has been put to sale in a auction");
            
            UserItem userItem = new UserItem();

            try {

                userItem = (UserItem) dAO.getEntityManager().createNamedQuery("UserItem.findByUserIdAndItemId").setParameter("userid", user).setParameter("itemid", item).getSingleResult();

            } catch (Exception exc) {
            }
            
            if(userItem.getId() == null){                
                
                userItem.setId((long) -1);
                userItem.setIsbuying(false);
                userItem.setIsfollowing(false);
                userItem.setIsselling(true);
                userItem.setItemid(item);
                userItem.setUserid(user);
                userItem.setCreationdate(new Date());
                
                dAO.getEntityManager().persist(userItem);
                return ResultMessage.AuctionCreated;
            }

            userItem.setIsselling(true);
            dAO.getEntityManager().persist(userItem);
            return ResultMessage.AuctionCreated;
        }
                
        return ResultMessage.AuctionAlreadyCreated;
    }
     
      public void WarningItemChanges(Item item, String text){
          
          List<UserItem> usersItem = new ArrayList();
          List<User> usersfollowing = new ArrayList();

          try {
              usersItem = dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByItemId").setParameter("itemid", item).getResultList();
          } catch (Exception e) {
          }

          if (!usersItem.isEmpty()) {
              
              User user = new User();

              try {
                  user = (User) dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", "admin").getSingleResult();
              } catch (Exception ex) {

              }
              
              for (UserItem i : usersItem) {
                  if (i.getIsfollowing()) {
                      Message msg = new Message((long) -1, "Alterations in item: " + item.getId() + " - " + item.getName(), text, new Date(), 0);
                      msg.setFromid(user);
                      msg.setToid(i.getUserid());
                      dAO.getEntityManager().persist(msg);
                  }
              }
          }
          
          
      }
}
