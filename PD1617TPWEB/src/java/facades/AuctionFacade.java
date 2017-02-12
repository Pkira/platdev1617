
package facades;

import controllers.IDAO;
import entities.Auction;
import entities.AuctionLog;
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
    
    @Override
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
    public ResultMessage CreateAuction(long ItemId){
       
        //get Item from BD

        Item item = new Item();

        try{
            
            item = (Item)dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        }catch(Exception e){
            return ResultMessage.ItemNotExist;
        }

        List<Auction> allreadyexists = null;
        try{
            allreadyexists = dAO.getEntityManager().createNamedQuery("Auction.findByItem").setParameter("itemid", item).getResultList();
        }catch(Exception e){
            return ResultMessage.AuctionNotCreated; 
        }
        
        if(!allreadyexists.isEmpty())
            return ResultMessage.AuctionAlreadyCreated;


        Auction auction = new Auction();
        auction.setId((long)-1);
        auction.setItemid(item);
        auction.setItemstate(0);
        auction.setAuctionstate(1);
        auction.setLastbid(item.getStartprice());
        auction.setLastuserid(null);
        auction.setStartdate(new Date());
        
        Date enddate = new Date();        
        Calendar c = Calendar.getInstance();
        c.setTime(enddate);
        c.add(Calendar.HOUR, (int) item.getAuctionduration());
        enddate = c.getTime();
        auction.setEnddate(enddate);
        

        User user = null;

        try {

            user = item.getOwnerid();

        } catch (Exception ex) {
            return ResultMessage.UserNotExist;
        }

        try {
            Newsletter.addNewsletterItem("New Auction created with the item: " + item.getName());
        } catch (Exception e) {
            return ResultMessage.AuctionNotCreated; 
        }

        try {
            dAO.getEntityManager().persist(auction);
        } catch (Exception e) {
            return ResultMessage.AuctionNotCreated; 
        }

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
      
    @Override
    public ResultMessage BidItem(long UserId, long value, long AuctionId){
          
          Auction auction = new Auction();
          
          try{
              auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findById").setParameter("id", AuctionId).getSingleResult();
          }catch(Exception ex){
              return ResultMessage.AuctionNotExist;
          }
          
          if(auction.getAuctionstate() == 0)
              return ResultMessage.AuctionAlreadyFinish;
          
          User user = new User();
          
          try{
              user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
          }catch(Exception e){
              return ResultMessage.UserNotExist;
          }
          
          //Fazer verificação se o valor licitado é superior ao atual
          if(auction.getLastbid() < value && value <= user.getBalance()){
            
            Item item = auction.getItemid();
            
            auction.setLastbid(value);
            auction.setLastuserid(user.getId());
          
            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " have been binded.");
            dAO.getEntityManager().persist(auction);
          
            String text = "The item "  + item.getId() + " - " + item.getName() + " has been binding";
            InsertLog(auction, text);
          
            UpdateUseritemBid(item);
            
            return ResultMessage.BidSuccess;
          }
          
          return ResultMessage.BidInsuccess;
      }
    
    @Override
    public ResultMessage BuyNowItem(long UserId, long value, long AuctionId){
          
          Auction auction = new Auction();
          
          try{
              auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findById").setParameter("id", AuctionId).getSingleResult();
          }catch(Exception ex){
              return ResultMessage.AuctionNotExist;
          }
          
          if(auction.getAuctionstate() == 0)
              return ResultMessage.AuctionAlreadyFinish;
          
          User user = new User();
          
          try{
              user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
          }catch(Exception e){
              return ResultMessage.UserNotExist;
          }
          Item item = auction.getItemid();
          
          User seller = new User();
          
          seller = item.getOwnerid();
          
          // Fazer verificação se o valor licitado é superior ao atual
          if(item.getBuynowprice() <= value && value <= user.getBalance()){
            
            auction.setLastbid(value);
            auction.setLastuserid(user.getId());
            auction.setAuctionstate(0);
            auction.setItemstate(1);
            auction.setEnddate(new Date());
          
            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " have been selled.");
            dAO.getEntityManager().persist(auction);
            
            item.setOwnerid(user);
            dAO.getEntityManager().persist(item);
            
            user.setBalance(user.getBalance() - value);
            dAO.getEntityManager().persist(user);
            
            seller.setBalance(seller.getBalance() + value);
            dAO.getEntityManager().persist(seller);
            
            String text = "The item "  + item.getId() + " - " + item.getName() + " has been selled";
            InsertLog(auction, text);
          
            text = "The auction "  + auction.getId() + " have finished";
            InsertLog(auction, text);
            
            UpdateUseritemBuy(item);
          
            return ResultMessage.BuyNowSuccess;
          }
          
          return ResultMessage.BuyNowInsuccess;
      }
      
    private void InsertLog(Auction auction, String text){
        
        AuctionLog log = new AuctionLog();

        log.setId((long) -1);
        log.setAuctionid(auction);
        log.setCreationdate(new Date());
        log.setDescription(text);
        dAO.getEntityManager().persist(log);
        
    }
     
    private void WarningItemChanges(Item item, String text){
          
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
    
    private void UpdateUseritemBuy(Item item){
          
        List<UserItem> userItem = new ArrayList();
        UserItem seller = new UserItem();
        
        try{
            userItem = dAO.getEntityManager().createNamedQuery("UserItem.findByIsbuyingAndItemId").setParameter("itemid", item).getResultList();
        }catch(Exception e){
            
        }
        
        for(UserItem i : userItem){
            
            if(i.getIsfollowing()){
                i.setIsbuying(false);
                dAO.getEntityManager().persist(i);
            }
            
            if(!i.getIsfollowing()){
                dAO.getEntityManager().remove(i);
            }                
        }
        
        try{
            seller = (UserItem) dAO.getEntityManager().createNamedQuery("UserItem.findSellingByUserId").setParameter("itemid", item).getSingleResult();
        }catch(Exception ex){
            
        }
        
        seller.setIsselling(false);
        
        dAO.getEntityManager().persist(seller);
          
      }
    
    private void UpdateUseritemBid(Item item){
          
        List<UserItem> userItem = new ArrayList();
        User buying = new User();
        boolean flag = false;
        
        try{
            userItem = dAO.getEntityManager().createNamedQuery("UserItem.findByItemId").setParameter("itemid", item).getResultList();
        }catch(Exception e){
            
        }
        
        for(UserItem i : userItem){
            
            if(i.getUserid() == buying){
                if(!i.getIsbuying()){
                    i.setIsbuying(true);
                    dAO.getEntityManager().persist(i);
                }
                flag = true;
            }               
        }
        
        
        
        if(!flag){
            
            UserItem isBuy = new UserItem();
            
            isBuy.setCreationdate(new Date());
            isBuy.setId((long)-1);
            isBuy.setIsbuying(true);
            isBuy.setIsfollowing(false);
            isBuy.setIsselling(false);
            isBuy.setItemid(item);
            isBuy.setUserid(buying);
            
            dAO.getEntityManager().persist(isBuy);
        }
          
      }
}
