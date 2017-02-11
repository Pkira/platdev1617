
package facades;

import controllers.IDAO;
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
    public ResultMessage CreateItem(long UserId, String Name, long category, String Desc, double Price, double BuyNow, long AuctionDuration, String Image){     

        if(UserId == 0 || Name.isEmpty() || category==0 || Desc.isEmpty() || Price == 0 || BuyNow == 0 || AuctionDuration == 0 || Image.isEmpty())
            return ResultMessage.CreateItemUnsuccess;

        User user = null;

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.CreateItemUnsuccess;
        }

        Category cat = null;
        try
        {
            cat = (Category)dAO.getEntityManager().createNamedQuery("Category.findById").setParameter("id", category).getSingleResult();
        }
        catch(Exception ex)
        {
            return ResultMessage.CreateItemUnsuccess;
        }            

        Item item = new Item((long)-1,Name,Desc,Price,BuyNow,AuctionDuration, Image);
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
    public List<Item> SearchItem(String Name, String Category){
        
        HashMap<Long,Item> items = new HashMap<>();
        
        // find by name
        for(Item i : (List<Item>)dAO.getEntityManager().createNamedQuery("Item.findByName").setParameter("name", Name).getResultList())
        {
            if(!items.containsKey(i.getId()))
                items.put(i.getId(), i);
        }
        
        // find by category
        for(Item i : (List<Item>)dAO.getEntityManager().createNamedQuery("Item.findByCategory").setParameter("name", Category).getResultList())
        {
            if(!items.containsKey(i.getId()))
                items.put(i.getId(), i);
        }
        
        return new ArrayList<Item>(items.values());
        
    }
   
    @Override
    public List<Item> UserItems(long UserId){
        
        if(UserId == 0)
            return null;
        
        HashMap<Long,Item> items = new HashMap<>();
        
        User user = (User)dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        
        for(Item i : (List<Item>)dAO.getEntityManager().createNamedQuery("Item.findByOwnerId").setParameter("ownerid", user).getResultList())
        {
            if(!items.containsKey(i.getId()))
                items.put(i.getId(), i);
        }
        
        return new ArrayList<Item>(items.values());
    }
    
    @Override
    public List<Item> ItemInSell(long UserId){
        
        HashMap<Long,Item> items = new HashMap<>();
        
        for(Item i : (List<Item>)dAO.getEntityManager().createNamedQuery("Item.findSellingItems").setParameter("userid", UserId).getResultList())
        {
            if(!items.containsKey(i.getId()))
                items.put(i.getId(), i);
        }
        
        return new ArrayList<Item>(items.values());
    }
    
    @Override
    public List<Item> FollowItens(long UserId){
        
        HashMap<Long,Item> items = new HashMap<>();
        List<UserItem> followingItens = new ArrayList();
        
        User user = null;
        
        try {

            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
    
        } catch (Exception e) {
            return new ArrayList<Item>();
        }
        
        try {
            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByUserId").setParameter("userid", user).getResultList();
        } catch (Exception e) {
            return new ArrayList<Item>(items.values());
        }
        
        for(UserItem i : followingItens)
        {
             if(!items.containsKey(i.getItemid()))
                items.put(i.getItemid().getId(), i.getItemid());
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
        
        List<UserItem> followingItens;
        
        try {
            
            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByUserId").setParameter("userid", user).getResultList();
            
        } catch (Exception e) {
            followingItens = new ArrayList<UserItem>();
        }
        
        for(UserItem i : followingItens)
        {
            if(i.getItemid().getId() == ItemId && i.getIsfollowing())
                return ResultMessage.FollowItemAlreadyFollow;
            
            if(i.getItemid().getId() == ItemId && !i.getIsfollowing())
            {
                i.setIsfollowing(true);
                dAO.getEntityManager().persist(i);
                return ResultMessage.FollowItemSucess;
            }
        }
        
        try{
            item = (Item)dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        }catch(Exception e){
            return ResultMessage.ItemNotExist;
        }

        follow.setId((long)-1);
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
            
            followingItens = (List<UserItem>) dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByUserId").setParameter("userid", user).getResultList();
            
        } catch (Exception e) {
            followingItens = new ArrayList<UserItem>();
        }
        
        for(UserItem i : followingItens)
        {
            if(i.getItemid().getId() == ItemId && i.getIsfollowing())
            {
                if(i.getIsbuying() || i.getIsselling())
                {
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
        
        try{
            item = (Item)dAO.getEntityManager().createNamedQuery("Item.findById").setParameter("id", ItemId).getSingleResult();
        }catch(Exception e){
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
    
    
}
