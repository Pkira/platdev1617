
package facades;

import javax.ejb.LocalBean;
import utils.ResultMessage;



@LocalBean
public interface IVisitor {
    
    ResultMessage AskReactivation(String name);
    
}
