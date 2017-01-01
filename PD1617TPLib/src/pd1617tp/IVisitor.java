
package pd1617tp;

import javax.ejb.Remote;
import libraries.ResultMessage;



@Remote
public interface IVisitor {
    
    ResultMessage Register(String name, String password);
    
}
