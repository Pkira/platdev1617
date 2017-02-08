
package facades;

import controllers.IDAO;
import entities.Item;
import entities.Newsletter;
import entities.UserItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import utils.ResultMessage;


@Stateless
public class ItemFacade implements IItem {

    @EJB
    private IDAO dAO;

    @Override
    public ResultMessage CreateItem(String Username, String Name, String Category, String Desc, double Price, double BuyNow, long AuctionDuration, String Image){
       
        try
        {
            if(Username.isEmpty() || Name.isEmpty() || Category.isEmpty() || Desc.isEmpty() || Price == 0 || BuyNow == 0 || AuctionDuration == 0 || Image.isEmpty())
                return ResultMessage.CreateItemUnsuccess;
            
            Item item = new Item((long)-1,Name,Desc,Price,BuyNow,AuctionDuration, Image);

            //Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " add a Item " + Item));

            dAO.getEntityManager().persist(item);
            
            return ResultMessage.CreateItemSuccess;
            
        
        }
        catch(Exception ex)
        {

        }
        
        return ResultMessage.CreateItemUnsuccess;
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
        
        return (List<Item>) items.values();
        
    }
   
    @Override
    public List<Item> ItemInSell(long UserId){
        
        HashMap<Long,Item> items = new HashMap<>();
        
        for(Item i : (List<Item>)dAO.getEntityManager().createNamedQuery("Item.findByOwnerId").setParameter("userid", UserId).getResultList())
        {
            if(!items.containsKey(i.getId()))
                items.put(i.getId(), i);
        }
        
        return (List<Item>) items.values();
    }
    
    @Override
    public List<Item> FollowItens(long UserId){
        
        HashMap<Long,Item> items = new HashMap<>();
        
        for(UserItem i : (List<UserItem>)dAO.getEntityManager().createNamedQuery("UserItem.findFolloingByUserId").setParameter("userid", UserId).getResultList())
        {
             if(!items.containsKey(i.getItemid()))
                items.put(i.getItemid().getId(), i.getItemid());
        }
        
        return (List<Item>) items.values();
    }

    @Override
    public ResultMessage FollowItem(long Item, long Username) {
        return null;
    }
    
    @Override
    public ResultMessage CancelFollowItem(long Item, long Username) {
        return null;
    }
}
