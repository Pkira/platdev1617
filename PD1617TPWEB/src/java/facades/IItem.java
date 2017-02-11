
package facades;

import entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import utils.ResultMessage;


@LocalBean
public interface IItem {
    
    ResultMessage CreateItem(long UserId, String Name, String Category, String Desc, double Price, double BuyNow, long AuctionDuration, String Image);
    List<Item> SearchItem(String ItemName, String CategoryName);
    List<Item> ItemInSell(long UserId);
    List<Item> FollowItens(long UserId);
    ResultMessage FollowItem(Long ItemId, Long UserId);
    ResultMessage CancelFollowItem(long ItemId, long UserId);
    List<Item> UserItems(long UserId);
    Item GetItemById(long ItemId);
}
