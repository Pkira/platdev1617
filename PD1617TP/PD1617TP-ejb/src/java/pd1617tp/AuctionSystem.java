
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
        
        user = new User();       
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
    
    @Override
    public String SeePerfil(String Username)
    {
        User user =Users.get(Username);
        
        String msg = "Username:  " + user.getName() + "\nAddress:  " + user.getAddress() + "\nBalance:  " + user.getBalance() + "\n";
        
        return msg;    
    }

    @Override
    public ResultMessage UpdatePerfil(String Username, String Address)
    {
        User user =Users.get(Username);
        
        if(Address != null){
            user.setAddress(Address);
            return ResultMessage.UpdatePerfilValid;
        }
        
        return ResultMessage.UpdatePerfilInvalid;    
    }
    
    @Override
    public String CheckBalance(String Username){
        
        User user =Users.get(Username);
        return "Your current balance is  " + user.getBalance() + " €\n";
    }
    
    @Override
    public ResultMessage LoadBalance(String Username, double increment){
        User user =Users.get(Username);
        
        if(increment > 0){
            user.setBalance(increment);
            return ResultMessage.LoadBalanceValid;
        }
        
        return ResultMessage.LoadBalanceInvalid;   
    }
}
