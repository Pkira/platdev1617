
package facades;

import entities.Item;
import java.util.List;
import javax.ejb.LocalBean;
import utils.ResultMessage;



@LocalBean
public interface IVisitor {
    
    ResultMessage AskReactivation(String name);
    List<Item> SeeLastSellItens();
}
