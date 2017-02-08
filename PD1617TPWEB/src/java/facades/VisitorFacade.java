//
package facades;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import utils.ResultMessage;



@Stateless
public class VisitorFacade implements IVisitor {
    
    @EJB
    private ISystem AServer;
    
//    @Override
//    public ResultMessage Register(String Username, String Password) {
//        return AServer.RegisterUser(Username, Password);
//    }
//    
//    @Override
//    public ResultMessage AskReactivation(String name){
//         return AServer.AskReactivation(name);
//    }
}
