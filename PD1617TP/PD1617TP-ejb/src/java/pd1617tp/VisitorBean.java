
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import libraries.ResultMessage;



@Stateless
public class VisitorBean implements IVisitor {
    
    @EJB
    private ISystem AServer;
    
    @Override
    public ResultMessage Register(String Username, String Password) {
        return AServer.RegisterUser(Username, Password);
    }
    
    
}
