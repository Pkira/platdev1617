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
    public List<Auction> GetAll() {

        List<Auction> auctions = null;
        List<Auction> auctionsToSend = new ArrayList();

        try {
            auctions = dAO.getEntityManager().createNamedQuery("Auction.findByAuctionstate").setParameter("auctionstate", 1).getResultList();
        } catch (Exception e) {
            return new ArrayList<Auction>();
        }

        for (Auction a : auctions) {
            if (a.getEnddate().before(new Date())) {
                TerminateAuction(a, a.getItemid());
            } else {
                auctionsToSend.add(a);
            }
        }

        return auctionsToSend;
    }

    @Override
    public Auction GetAuctionById(long AuctionId) {

        Auction auction = null;
        try {
            auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findById").setParameter("id", AuctionId).getSingleResult();
        } catch (Exception e) {
            return null;
        }

        return auction;
    }

    @Override
    public List<Auction> GetUserBuyingAuctions(long UserId) {

        List<Auction> auctions = new ArrayList<Auction>();

        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return new ArrayList<Auction>();
        }

        List<UserItem> userItems = null;

        try {

            userItems = dAO.getEntityManager().createNamedQuery("UserItem.findByIsbuyingANDUserId").setParameter("userid", user).getResultList();

        } catch (Exception exc) {
        }

        for (UserItem ui : userItems) {
            Item item = ui.getItemid();

            Auction auction = null;
            try {
                auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findByItem").setParameter("itemid", item).getSingleResult();
            } catch (Exception e) {
                return new ArrayList<Auction>();
            }

            auctions.add(auction);
        }

        return auctions;
    }

    @Override
    public ResultMessage CreateAuction(long ItemId) {

        //get Item from BD
        Item item = new Item();

        try {

            item = (Item) dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ItemNotExist;
        }

        List<Auction> allreadyexists = null;
        try {
            allreadyexists = dAO.getEntityManager().createNamedQuery("Auction.findByItemAndAuctionState").setParameter("itemid", item).setParameter("auctionstate", 1).getResultList();
        } catch (Exception e) {
            return ResultMessage.AuctionNotCreated;
        }

        if (!allreadyexists.isEmpty()) {
            return ResultMessage.AuctionAlreadyCreated;
        }

        Auction auction = new Auction();
        auction.setId((long) -1);
        auction.setItemid(item);
        auction.setItemstate(0);
        auction.setAuctionstate(1);
        auction.setLastbid(item.getStartprice());
        auction.setSellerid(item.getOwnerid());
        auction.setLastuserid(item.getOwnerid());
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

        List<UserItem> allreadyExists = new ArrayList<UserItem>();

        try {

            allreadyExists = dAO.getEntityManager().createNamedQuery("UserItem.findByUserIdAndItemId").setParameter("userid", user).setParameter("itemid", item).getResultList();

        } catch (Exception exc) {
            return ResultMessage.AuctionNotCreated;
        }

        if (allreadyExists.isEmpty()) {

            UserItem userItem = new UserItem();
            userItem.setId((long) -1);
            userItem.setIsbuying(false);
            userItem.setIsfollowing(false);
            userItem.setIsselling(true);
            userItem.setItemid(item);
            userItem.setUserid(user);
            userItem.setCreationdate(new Date());

            try {
                dAO.getEntityManager().persist(userItem);
            } catch (Exception e) {
                return ResultMessage.AuctionNotCreated;
            }
            return ResultMessage.AuctionCreated;
        }

        UserItem userItem = allreadyExists.get(0);
        userItem.setIsselling(true);
        dAO.getEntityManager().persist(userItem);
        return ResultMessage.AuctionCreated;

    }

    @Override
    public ResultMessage BidItem(long UserId, long value, long AuctionId) {

        Auction auction = new Auction();

        try {
            auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findById").setParameter("id", AuctionId).getSingleResult();
        } catch (Exception ex) {
            return ResultMessage.AuctionNotExist;
        }

        if (UserId == auction.getItemid().getOwnerid().getId()) {
            return ResultMessage.ItemOwner;
        }

        if (auction.getAuctionstate() == 0) {
            return ResultMessage.AuctionAlreadyFinish;
        }

        if (auction.getEnddate().before(new Date())) {
            TerminateAuction(auction, auction.getItemid());
            return ResultMessage.AuctionAlreadyFinish;
        }

        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.UserNotExist;
        }
        
        if(user.getUsername().equals("admin"))
            return ResultMessage.ErrorAdmin;

        //Fazer verificação se o valor licitado é superior ao atual
        if (auction.getLastbid() < value && value <= user.getBalance()) {

            Item item = auction.getItemid();

            auction.setLastbid(value);
            auction.setLastuserid(user);

            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " have been binded.");
            dAO.getEntityManager().persist(auction);

            String text = "The item " + item.getId() + " - " + item.getName() + " has been binding";
            InsertLog(auction, text);

            UpdateUseritemBid(item, user);

            return ResultMessage.BidSuccess;
        }

        return ResultMessage.BidInsuccess;
    }

    @Override
    public ResultMessage BuyNowItem(long UserId, long AuctionId) {

        Auction auction = new Auction();

        try {
            auction = (Auction) dAO.getEntityManager().createNamedQuery("Auction.findById").setParameter("id", AuctionId).getSingleResult();
        } catch (Exception ex) {
            return ResultMessage.AuctionNotExist;
        }

        if (UserId == auction.getItemid().getOwnerid().getId()) {
            return ResultMessage.ItemOwner;
        }

        if (auction.getAuctionstate() == 0) {
            return ResultMessage.AuctionAlreadyFinish;
        }

        if (auction.getEnddate().before(new Date())) {
            TerminateAuction(auction, auction.getItemid());
            return ResultMessage.AuctionAlreadyFinish;
        }

        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.UserNotExist;
        }
        
        if(user.getUsername().equals("admin"))
            return ResultMessage.ErrorAdmin;
        
        Item item = auction.getItemid();

        User seller = new User();

        seller = item.getOwnerid();

        // Fazer verificação se o valor licitado é superior ao atual
        if (item.getBuynowprice() <= user.getBalance()) {

            UserItem useritens = new UserItem();

            try {
                useritens = (UserItem) dAO.getEntityManager().createNamedQuery("UserItem.findSellingByItemId").setParameter("itemid", item).getSingleResult();
            } catch (Exception ex) {
            }

            useritens.setIsselling(false);
            dAO.getEntityManager().persist(useritens);

            auction.setLastbid(item.getBuynowprice());
            auction.setLastuserid(user);
            auction.setAuctionstate(0);//mudar para false
            auction.setItemstate(2);//2 - vendido buyNow
            auction.setEnddate(new Date());
            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " have been selled.");
            dAO.getEntityManager().persist(auction);

            item.setOwnerid(user);
            dAO.getEntityManager().persist(item);

            user.setBalance(user.getBalance() - item.getBuynowprice());
            dAO.getEntityManager().persist(user);

            seller.setBalance(seller.getBalance() + item.getBuynowprice());
            dAO.getEntityManager().persist(seller);

            String text = "The item " + item.getId() + " - " + item.getName() + " has been selled";
            InsertLog(auction, text);

            text = "The auction " + auction.getId() + " have finished";
            InsertLog(auction, text);

            UpdateUseritemBuy(item);
            Newsletter.addNewsletterItem("The Auction with the item: " + item.getId() + " - " + item.getName() + " have been finished");
            return ResultMessage.BuyNowSuccess;
        }

        return ResultMessage.BuyNowInsuccess;
    }

    private void TerminateAuction(Auction auction, Item item) {

        UpdateUseritemBuy(item);

        User user = new User();

        if (auction.getLastuserid() != null && auction.getLastuserid() != auction.getSellerid()) {

            User seller = auction.getSellerid();
            user = auction.getLastuserid();

            auction.setItemstate(1);//vendido por bid

            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " have been selled.");
            dAO.getEntityManager().persist(auction);

            item.setOwnerid(user);
            dAO.getEntityManager().persist(item);

            user.setBalance(user.getBalance() - item.getBuynowprice());
            dAO.getEntityManager().persist(user);

            seller.setBalance(seller.getBalance() + item.getBuynowprice());
            dAO.getEntityManager().persist(seller);
            Newsletter.addNewsletterItem("The Auction with the item: " + item.getId() + " - " + item.getName() + " have been finished");
        } else {
            auction.setItemstate(0);//não foi vendido
            WarningItemChanges(item, "The item " + item.getId() + " - " + item.getName() + " doesn't have been selled.");
        }

        auction.setAuctionstate(0);
        dAO.getEntityManager().persist(auction);

    }

    private void InsertLog(Auction auction, String text) {

        AuctionLog log = new AuctionLog();

        log.setId((long) -1);
        log.setAuctionid(auction);
        log.setCreationdate(new Date());
        log.setDescription(text);
        dAO.getEntityManager().persist(log);

    }

    private void WarningItemChanges(Item item, String text) {

        List<UserItem> usersItem = new ArrayList();
        List<User> usersfollowing = new ArrayList();

        try {
            usersItem = dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByItemId").setParameter("itemid", item).getResultList();
        } catch (Exception e) {
            return;
        }

        if (!usersItem.isEmpty()) {

            User user = new User();

            try {
                user = (User) dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", "admin").getSingleResult();
            } catch (Exception ex) {
                return;
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

    private void UpdateUseritemBuy(Item item) {

        List<UserItem> userItem = new ArrayList();
        UserItem seller = new UserItem();

        try {
            userItem = dAO.getEntityManager().createNamedQuery("UserItem.findByIsbuyingAndItemId").setParameter("itemid", item).getResultList();
        } catch (Exception e) {

        }

        for (UserItem i : userItem) {

            if (i.getIsfollowing()) {
                i.setIsbuying(false);
                dAO.getEntityManager().persist(i);
            } else {
                dAO.getEntityManager().remove(i);
            }
        }

        try {
            seller = (UserItem) dAO.getEntityManager().createNamedQuery("UserItem.findSellingByItemId").setParameter("itemid", item).getSingleResult();
        } catch (Exception ex) {
            return;
        }

        if (!seller.getIsbuying() && !seller.getIsfollowing()) {
            dAO.getEntityManager().remove(seller);
        } else {
            seller.setIsselling(false);
            dAO.getEntityManager().persist(seller);
        }

    }

    private void UpdateUseritemBid(Item item, User buying) {

        List<UserItem> userItem = new ArrayList();
        boolean flag = false;

        try {
            userItem = dAO.getEntityManager().createNamedQuery("UserItem.findByItemId").setParameter("itemid", item).getResultList();
        } catch (Exception e) {

        }

        for (UserItem i : userItem) {

            if (i.getUserid() == buying) {
                if (!i.getIsbuying()) {
                    i.setIsbuying(true);
                    dAO.getEntityManager().persist(i);
                }
                flag = true;
            }
        }

        if (!flag) {

            UserItem isBuy = new UserItem();

            isBuy.setCreationdate(new Date());
            isBuy.setId((long) -1);
            isBuy.setIsbuying(true);
            isBuy.setIsfollowing(false);
            isBuy.setIsselling(false);
            isBuy.setItemid(item);
            isBuy.setUserid(buying);

            dAO.getEntityManager().persist(isBuy);
        }

    }
}
