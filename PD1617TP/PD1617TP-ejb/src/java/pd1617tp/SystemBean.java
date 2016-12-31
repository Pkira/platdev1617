
package pd1617tp;

import pd1617tp.ISystem;
import libraries.ResultMessage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import libraries.FactoryDB;
import libraries.Item;
import libraries.NewsLetter;
import libraries.NewsLetterItem;
import libraries.User;
import libraries.Messages;


@Singleton
public class SystemBean implements ISystem {

    private NewsLetter Newsletter = new NewsLetter();
    private HashMap<String,User> Users = new HashMap<>();
    private FactoryDB Factory = new FactoryDB();
    private ArrayList<Item> Itenss = new ArrayList<Item>();
    private HashMap<Long,Item> Itens = new HashMap<>();
    private int MessageID = 1;
    private long ItemID = 1;
    
      
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
        
        user = Factory.UserLogin(Username,Password);
        
        if(user != null)
        {
            user.setLogged(true);
            user.setLastAction();
            Users.put(Username,user);
            
            Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " successfully logged"));
            
            return ResultMessage.LoginSucess;
        }         
        else
        {
            return ResultMessage.LoginUserNotFound;
        }

    }
    
    @Override
    public ResultMessage RegisterUser(String Username, String Password){
        //validate input
        if(Username == null || Password == null)
            return ResultMessage.RegisterInvalid;
        
        //get user from list
        User user = Users.get(Username);
        
        // if dont exists 
        if(user == null)
        {
            user = new User(Username, Password,"");
            boolean result = Factory.UserRegister(user);
            
            if(result){
                Users.put(Username, user); 
                
                Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " successfully registed"));
                
                return ResultMessage.RegisterSucess;
            }
            else
            {
                return ResultMessage.RegisterInvalid;
            }
            
        }
        else
        {
            return ResultMessage.RegisterAllreadyExist;
        }
    }
    
    @Override
    public boolean LogOffUser(String Username){
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
    public String SeeProfile(String Username){
        User user =Users.get(Username);
        
        String msg = "Username:  " + user.getName() + "\nAddress:  " + user.getAddress() + "\nBalance:  " + user.getBalance() + "\n";
        
        return msg;    
    }

    @Override
    public ResultMessage UpdateProfile(String Username, String Address, String password){
        User user =Users.get(Username);
        
        if(!Address.contentEquals("")){
            user.setAddress(Address);
            if(!password.contentEquals(""))
                user.setPassword(password);
            return ResultMessage.UpdateProfileValid;
        }
        else
            if(!password.contentEquals("")){
                user.setPassword(password);
                return ResultMessage.UpdateProfileValid;
            }
                    
        return ResultMessage.UpdateProfileInvalid;    
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
        
    @PostConstruct
    public void loadstate(){
        try 
        {
            ObjectInputStream loadUsers =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/Users")));
            Users = (HashMap<String,User>) loadUsers.readObject(); 
            
            ObjectInputStream loadNewsLetter =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/NewsLetter")));
            Users = (HashMap<String,User>) loadNewsLetter.readObject();
        }
        catch (Exception ex){
        }
    }
    
    @PreDestroy
    public void saveState() {
        
        try 
        {
            ObjectOutputStream saveUsers = new ObjectOutputStream( new BufferedOutputStream(new FileOutputStream("/tmp/Users")));
            saveUsers.writeObject(Users);  
            
            ObjectOutputStream saveNewsLetter = new ObjectOutputStream( new BufferedOutputStream(new FileOutputStream("/tmp/NewsLetter")));
            saveNewsLetter.writeObject(Newsletter);  
        }
        catch(Exception ex){
        }
        
    }

    @Override
    public NewsLetter GetNewsletter(){
        return this.Newsletter;
    }
    
    @Override
    public ArrayList CheckMessage(String Username){
        
        User user = Users.get(Username);
        
        return user.getMsgList();
    }
    
    @Override
    public ResultMessage SendMessage(String Username, String Addressed, String Subject, String Message){
        
        User SenderUser = Users.get(Username);
        User AddressedUser = Users.get(Addressed);        
        
        if(AddressedUser != null){
            Messages msg = new Messages(MessageID, Message, Subject, Addressed, Username);
            MessageID++;
            AddressedUser.setMsgList(msg);
            
            return ResultMessage.SendMessageSuccess;
        }
        else
            return ResultMessage.SendMessageNoUser;            
    }

    @Override   // AINDA COM MUITAS CORRECÇÕES A FAZER
    public ResultMessage CreateItem(String Username, String Item, String Category, String Desc, double Price, double BuyNow, String Budget) {
        
        Item item = new Item(ItemID, Price, BuyNow, Item, Desc, Category, Username);
        String aux = "";
        if(item != null)
        {
            Itens.put(ItemID, item);
            ItemID++;
            return ResultMessage.CreateItemSuccess;
        }  
        
        return ResultMessage.CreateItemUnsuccess;
    }

    @Override
    public ArrayList SearchItem(String Item, String Category){
        
        ArrayList<Item> Aux = new ArrayList<>();
        Item help = new Item();
        if(!Itens.isEmpty())  
            for(long i = 1; i <= Itens.size(); i++)
            {
                help = Itens.get(i);
                if(help!=null)
                    if(help.getName().contains(Item))
                        if(help.getCategory().contains(Category))
                            Aux.add(help);
            }
        return Aux;
    }
}
