
package pd1617tp;

import java.util.ArrayList;
import javax.ejb.Remote;
import libraries.ResultMessage;


@Remote
public interface IItem {
    
    ResultMessage CreateItem(String Owner, String Item, String Category, String Desc, double Price, double BuyNow, String Budget);
    ArrayList SearchItem(String Item, String Category);
}
