
package pd1617tp;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import libraries.ResultMessage;


@Stateless
public class ItemBean implements IItem {

    @EJB
    private ISystem AServer;
    
    @Override
    public ResultMessage CreateItem(String Owner, String Item, String Category, String Desc, double Price, double BuyNow, String Budget){
       return AServer.CreateItem(Owner, Item, Category, Desc, Price, BuyNow, Budget);
   }
    
    @Override
    public ArrayList SearchItem(String Item, String Category){
        return AServer.SearchItem(Item, Category);
    }
   
    @Override
    public ArrayList ItemInSell(String username){
        return AServer.ItemInSell(username);
    }
    
    @Override
    public List FollowItens(String username){
        return AServer.FollowItens(username);
    }

    @Override
    public ResultMessage FollowItem(Long Item, String Username) {
        return AServer.FollowItem(Item, Username);
    }
    
    @Override
    public ResultMessage CancelFollowItem(Long Item, String Username) {
        return AServer.CancelFollowItem(Item, Username);
    }
}
