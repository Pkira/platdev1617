
package pd1617tp;

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
    private ArrayList<Item> Itens = new ArrayList<Item>();
    private int MessageID = 1, ItemID = 1;
      
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
    public String SeeProfile(String Username)
    {
        User user =Users.get(Username);
        
        String msg = "Username:  " + user.getName() + "\nAddress:  " + user.getAddress() + "\nBalance:  " + user.getBalance() + "\n";
        
        return msg;    
    }

    @Override
    public ResultMessage UpdateProfile(String Username, String Address)
    {
        User user =Users.get(Username);
        
        if(Address != null){
            user.setAddress(Address);
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
        try (ObjectInputStream ois =
                new ObjectInputStream(
                    new BufferedInputStream(
                        new FileInputStream("/tmp/Users")))){
            Users = (HashMap<String,User>) ois.readObject();       
        }
        catch (Exception ex){
        }
    }
    
    @PreDestroy
    public void saveState() {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(
                    new BufferedOutputStream(
                        new FileOutputStream("/tmp/Users")))){
            oos.writeObject(Users);       
        }
        catch(Exception ex){
        }
        
    }

    @Override
    public NewsLetter GetNewsletter() {
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
        
        if(item != null)
        {
            Itens.add(item);
            ItemID++;
            return ResultMessage.CreateItemSuccess;
        }  
        
        return ResultMessage.CreateItemUnsuccess;
    }

    @Override
    public String SearchItem(String Item, String Category){
        
        String result = "Doesn't existe this itens";
        
        if(!Itens.isEmpty()){  
            for(int i = 0; i < Itens.size(); i++)
                if(Itens.get(i).getName().contains(Item))
                    if(Itens.get(i).getCategory().contains(Category)){
                        if(result.contains("Doesn't existe this itens"))
                            result = "";
                        result = result + "\nItem:\n Name: " + Itens.get(i).getName() + "\n";
                        result = result + " Category: " + Itens.get(i).getCategory() + "\n";
                        result = result + " Description: " + Itens.get(i).getDesc() + "\n";
                        result = result + " Start Price: " + Itens.get(i).getStartPrice() + "\n";
                        result = result + " Buy Now Price: " + Itens.get(i).getBuyNowPrice() + "\n";
                        result = result + " Owner: " + Itens.get(i).getOwner() + "\n\n";
                    }
            return result;
        }
        
        return "Doesn't existe itens";
    }

}
