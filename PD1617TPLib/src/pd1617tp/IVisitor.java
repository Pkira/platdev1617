
package pd1617tp;

import javax.ejb.Remote;
import libraries.ResultMessage;



@Remote
public interface IVisitor {
    
    ResultMessage Login(String name, String password);
    ResultMessage Register(String name, String password);
    
}
