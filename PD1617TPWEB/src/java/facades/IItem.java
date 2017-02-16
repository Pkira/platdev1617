
package facades;

import entities.Category;
import entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import utils.ResultMessage;


@LocalBean
public interface IItem {
    
    ResultMessage CreateItem(long UserId, String Name, long Category, String Desc, double Price, double BuyNow, long AuctionDuration, String Image);
    List<Item> SearchItem(String Name, String Category, String owner, double minPrice, double maxPrice);
    List<Item> ItemInSell(long UserId);
    List<Item> FollowItens(long UserId);
    ResultMessage FollowItem(Long ItemId, Long UserId);
    ResultMessage CancelFollowItem(long ItemId, long UserId);
    List<Item> UserItems(long UserId);
    Item GetItemById(long ItemId);
    List<Category> GetAllCategories();
    ResultMessage CancelItem(long ItemId, long UserId);
    List<Item> SearchItemByName(String Name);
}
