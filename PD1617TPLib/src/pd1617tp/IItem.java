
package pd1617tp;

import javax.ejb.Remote;
import libraries.ResultMessage;


@Remote
public interface IItem {
    
    ResultMessage CreateItem(String Owner, String Item, String Category, String Desc, double Price, double BuyNow, String Budget);
    String SearchItem(String Item, String Category);
}
