
package pd1617tp;

import java.util.HashMap;
import javax.ejb.Singleton;
import libraries.User;


@Singleton
public class AuctionSystem implements IAuctionSystem {

    private HashMap<String,User> Users = new HashMap<>();
      
    @Override
    public ResultMessage LoginUser(String Username, String Password) {
        
        //validate input
        if(Username == null || Password == null)
            return ResultMessage.LoginInvalid;
        
        //get user from list
        User user = Users.get(Username);
        
        //check if user is already logged
        if(user != null) 
        {
            boolean isLogged = user.isLogged();
            if(isLogged)    
                return ResultMessage.LoginAllreadyLogged;
        }
        
        boolean trylogin = user.Login(Username,Password);
        
        if(trylogin)
        {
            Users.put(Username, user);
            return ResultMessage.LoginSucess;
        }         
        else
        {
            return ResultMessage.LoginInvalid;
        }
            
        
    }
    
    @Override
    public ResultMessage RegisterUser(String Username, String Password)
    {
        //validate input
        if(Username == null || Password == null)
            return ResultMessage.RegisterInvalid;
        
        //get user from list
        User user = Users.get(Username);
        
        // if dont exists 
        if(user == null)
        {
            user = new User(Username, Password,"");
            user.Regist(Username, Password);   
            Users.put(Username, user); 
            
            return ResultMessage.RegisterSucess;
        }
        else
        {
            return ResultMessage.RegisterAllreadyExist;
        }
    }
    
    @Override
    public boolean LogOffUser(String Username)
    {
        //validate input
        if(Username == null)
            return false;
        
        //get user from list
        User user = Users.get(Username);
        
        // if exists 
        if(user != null)
        {
            user.LogOff();   
            return true;
        }

        return false;
        
    }

}
