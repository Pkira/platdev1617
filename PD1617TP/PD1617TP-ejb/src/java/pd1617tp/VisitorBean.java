
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import libraries.ResultMessage;



@Stateless
public class VisitorBean implements IVisitor {

    String Username;
    
    @EJB
    private ISystem AServer;
    
    @Override
    public ResultMessage Login(String Username, String Password) {  
        
        ResultMessage result = AServer.LoginUser(Username, Password);
        
        if(result == ResultMessage.LoginSucess)
            this.Username = Username;
            
        return result;
    }
    
    @Override
    public ResultMessage Register(String Username, String Password) {
        return AServer.RegisterUser(Username, Password);
    }
    
    
}
