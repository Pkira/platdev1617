package facades;

import controllers.IDAO;
import entities.Auction;
import entities.Category;
import entities.Item;
import entities.Newsletter;
import entities.User;
import entities.UserItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;
import utils.ResultMessage;

@Stateful
public class ItemFacade implements IItem {

    @EJB
    private IDAO dAO;

    @Override
    public ResultMessage CreateItem(long UserId, String Name, long category, String Desc, double Price, double BuyNow, long AuctionDuration, String Image) {

        if (UserId == 0 || Name.isEmpty() || category == 0 || Desc.isEmpty() || Price == 0 || BuyNow == 0 || AuctionDuration == 0 || Image.isEmpty()) {
            return ResultMessage.CreateItemUnsuccess;
        }

        User user = null;

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.CreateItemUnsuccess;
        }

        Category cat = null;
        try {
            cat = (Category) dAO.getEntityManager().createNamedQuery("Category.findById").setParameter("id", category).getSingleResult();
        } catch (Exception ex) {
            return ResultMessage.CreateItemUnsuccess;
        }

        Item item = new Item((long) -1, Name, Desc, Price, BuyNow, AuctionDuration, Image);
        item.setOwnerid(user);
        item.setCategoryid(cat);

        try {
            dAO.getEntityManager().persist(item);
        } catch (Exception e) {
            return ResultMessage.CreateItemUnsuccess;
        }

        return ResultMessage.CreateItemSuccess;

    }

    @Override
    public List<Item> SearchItem(String Name, String Category, String owner, double minPrice, double maxPrice) {

        List<Item> items = new ArrayList();
        List<User> user = new ArrayList();
        String query = "SELECT i FROM Item i";
        boolean and = false;

        if (!Category.isEmpty()) {
            query = query + " INNER JOIN i.categoryid c ON c.name ";
        }

        if (!owner.isEmpty()) {  
            
            query = query + " INNER JOIN i.ownerid o ON o.username like '" + owner + "'";
        }
        
        if (!Category.isEmpty()) {
            if (!and) {
                query = query + " WHERE c.name like '" + Category + "'";
                and = true;
            } else {
                query = query + " AND c.name like '" + Category + "'";
            }
        }
        
        if (!owner.isEmpty()) {
            if (!and) {
                query = query + " WHERE o.username like '" + owner + "'";
                and = true;
            } else {
                query = query + " AND o.username like '" + owner + "'";
            }
        }
        
        if (!Name.isEmpty()) {
            if (!and) {
                query = query + " WHERE i.name like '" + Name + "'";
                and = true;
            } else {
                query = query + " AND i.name like '" + Name + "'";
            }
        }

        if (maxPrice == 1 || maxPrice <= minPrice) {
            try {
                maxPrice = (double) dAO.getEntityManager().createNamedQuery("Item.findMaxStartprice").getSingleResult();
            } catch (Exception e) {
                return items;
            }
        }

        if (!and) {
            query = query + " WHERE i.startprice >= " + minPrice + " AND i.startprice <= " + maxPrice;
        } else {
            query = query + " AND i.startprice >= " + minPrice + " AND i.startprice <= " + maxPrice;
        }
        
        try {            
            items = dAO.getEntityManager().createQuery(query).getResultList();
        } catch (Exception e) {
            String a = e.getMessage();
            return items;
        }

        return items;

    }

    @Override
    public List<Item> UserItems(long UserId) {

        User user = null;

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return null;
        }

        List<Item> items = null;
        try {
            items = (List<Item>) dAO.getEntityManager().createNamedQuery("Item.findByOwnerIdNotInSell").setParameter("ownerid", user).getResultList();
        } catch (Exception e) {
            return new ArrayList<Item>();
        }

        return items;
    }

    @Override
    public List<Item> ItemInSell(long UserId) {

        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return null;
        }

        HashMap<Long, Item> items = new HashMap<>();

        List<UserItem> itemsBD = new ArrayList();
        try {
            itemsBD = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findSellingByUserId").setParameter("userid", user).getResultList();
        } catch (Exception e) {
            return null;
        }

        for (UserItem i : itemsBD) {
            if (!items.containsKey(i.getItemid().getId())) {
                items.put(i.getItemid().getId(), i.getItemid());
            }
        }

        return new ArrayList<Item>(items.values());
    }

    @Override
    public List<Item> FollowItens(long UserId) {

        HashMap<Long, Item> items = new HashMap<>();
        List<UserItem> followingItens = new ArrayList();

        User user = null;

        try {

            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();

        } catch (Exception e) {
            return new ArrayList<Item>();
        }

        try {
            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findByIsfollowingANDUserId").setParameter("userid", user).getResultList();
        } catch (Exception e) {
            return new ArrayList<Item>(items.values());
        }

        for (UserItem i : followingItens) {
            if (!items.containsKey(i.getItemid())) {
                items.put(i.getItemid().getId(), i.getItemid());
            }
        }

        return new ArrayList<Item>(items.values());
    }

    @Override
    public ResultMessage FollowItem(Long ItemId, Long UserId) {

        Item item = new Item();
        UserItem follow = new UserItem();
        User user = null;

        try {

            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();

        } catch (Exception e) {
            return ResultMessage.UserNotExist;
        }

        if(user.getUsername().equals("admin"))
            return ResultMessage.ErrorAdmin;
        
        List<UserItem> followingItens;

        try {

            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findByUserId").setParameter("userid", user).getResultList();

        } catch (Exception e) {
            followingItens = new ArrayList<UserItem>();
        }

        for (UserItem i : followingItens) {
            if (i.getItemid().getId() == ItemId && i.getIsfollowing()) {
                return ResultMessage.FollowItemAlreadyFollow;
            }

            if (i.getItemid().getId() == ItemId && !i.getIsfollowing()) {
                i.setIsfollowing(true);
                dAO.getEntityManager().merge(i);
                return ResultMessage.FollowItemSucess;
            }
        }

        try {
            item = (Item) dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ItemNotExist;
        }

        follow.setId((long) -1);
        follow.setIsbuying(false);
        follow.setIsfollowing(true);
        follow.setIsselling(false);
        follow.setItemid(item);
        follow.setUserid(user);
        follow.setCreationdate(new Date());
        dAO.getEntityManager().persist(follow);
        return ResultMessage.FollowItemSucess;

    }

    @Override
    public ResultMessage CancelFollowItem(long ItemId, long UserId) {

        Item item = new Item();
        UserItem follow = new UserItem();
        User user = null;

        try {

            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();

        } catch (Exception e) {
            return ResultMessage.UserNotExist;
        }

        List<UserItem> followingItens;

        try {

            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findByIsfollowingANDUserId").setParameter("userid", user).getResultList();

        } catch (Exception e) {
            followingItens = new ArrayList<UserItem>();
        }

        for (UserItem i : followingItens) {
            if (i.getItemid().getId() == ItemId && i.getIsfollowing()) {
                if (i.getIsbuying() || i.getIsselling()) {
                    i.setIsfollowing(false);
                    dAO.getEntityManager().persist(i);
                    return ResultMessage.CancelFollowItemSucess;
                }

                dAO.getEntityManager().remove(i);
                return ResultMessage.CancelFollowItemSucess;
            }
        }

        return ResultMessage.CancelFollowItemAlreadyNotFollow;
    }

    @Override
    public Item GetItemById(long ItemId) {

        Item item = new Item();

        try {
            item = (Item) dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        } catch (Exception e) {
            return item;
        }
        return item;
    }

    @Override
    public List<Category> GetAllCategories() {

        List<Category> categories = null;

        try {
            categories = dAO.getEntityManager().createNamedQuery("Category.findAll").getResultList();
        } catch (Exception e) {
            return new ArrayList<Category>();
        }

        return categories;
    }

    @Override
    public ResultMessage CancelItem(long ItemId, long UserId) {

        User user = null;

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.UserNotExist;
        }

        Item item = null;

        try {
            item = (Item) dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ItemNotExist;
        }

        if ( (user != null && item != null) && (item.getOwnerid().getId() == user.getId() || user.getUsername().equals("admin"))) 
        {
            
            //dAO.getEntityManager().getTransaction().begin();
            
            
            try {
                    
                //remove user items
                List<UserItem> userItems = dAO.getEntityManager().createNamedQuery("UserItem.findByItemId").setParameter("itemid", item).getResultList();

                for (UserItem ui : userItems) {
                    dAO.getEntityManager().remove(ui);
                }

                //remove auction
                List<Auction> auctions = dAO.getEntityManager().createNamedQuery("Auction.findByItem").setParameter("itemid", item).getResultList();

                if (!auctions.isEmpty()) {
                    dAO.getEntityManager().remove(auctions.get(0));
                }

                //remove item
                dAO.getEntityManager().remove(item);
            
            } catch (Exception e) {
                return ResultMessage.CancelItemInsucess;
            }
            
            //dAO.getEntityManager().getTransaction().commit();
            //dAO.getEntityManager().close();
            
            return ResultMessage.CancelItemSucess;
        }

        return ResultMessage.CancelItemInsucess;
    }

}
