
package pd1617tp;

import java.util.ArrayList;
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
}
