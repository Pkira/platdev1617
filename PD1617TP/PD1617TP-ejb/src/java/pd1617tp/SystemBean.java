
package pd1617tp;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import libraries.ResultMessage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import libraries.Auction;
import libraries.FactoryDB;
import libraries.Item;
import libraries.NewsLetter;
import libraries.NewsLetterItem;
import libraries.User;
import libraries.Messages;
import libraries.Notification;


@Singleton
public class SystemBean implements ISystem {
    
    private NewsLetter Newsletter = new NewsLetter();
    private HashMap<String,User> Users = new HashMap<>();
    private FactoryDB Factory = new FactoryDB();
    private HashMap<Long,Item> Itens = new HashMap<>();
    private HashMap<Integer,Messages> Messages = new HashMap<>();
    private HashMap<Long,Auction> Auctions = new HashMap<>();
    private HashMap<Long,Notification> Notifications = new HashMap<>();
    private int MessageID = 1;
    private long ItemID = 1;
    private long AuctionID = 1;
      
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
            
            boolean isUnverified = user.getAccountActivation();
            if(!isUnverified)    
                return ResultMessage.AccountNotActivated;
            
            boolean isSuspended = user.isAccountSuspension();
            if(isSuspended)    
                return ResultMessage.AccountSuspended;
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
    public ResultMessage UpdateProfile(String Username, String Address, String password, String CurrentPass){
        User user =Users.get(Username);
        
        if(CurrentPass.contentEquals(user.getPassword())){
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
        return ResultMessage.UpdateProfileWrongPass;    
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
            ObjectInputStream NewsletterLoad =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/Newsletter")));
            Newsletter = (NewsLetter) NewsletterLoad.readObject();  
            
            ObjectInputStream UsersLoad =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/Users")));
            Users = (HashMap<String,User>) UsersLoad.readObject(); 
            
            ObjectInputStream ItensLoad =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/Itens")));
            Itens = (HashMap<Long,Item>) ItensLoad.readObject(); 
            
            ObjectInputStream AuctionsLoad =new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/Auctions")));
            Auctions = (HashMap<Long,Auction>) AuctionsLoad.readObject(); 
 
        }
        catch (Exception ex){

        }
    }
    
    @PreDestroy
    public void saveState(){
        try
        { 
            ObjectOutputStream NewsletterSave =new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/Newsletter")));
            NewsletterSave.writeObject(Newsletter);
            
            ObjectOutputStream usersSave =new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/Users")));
            usersSave.writeObject(Users);    
            
            ObjectOutputStream itensSave =new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/Itens")));
            itensSave.writeObject(Itens);
            
            ObjectOutputStream AuctionsSave =new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/Auctions")));
            AuctionsSave.writeObject(Auctions);
            
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
        
        User AddressedUser = Users.get(Addressed);        
        
        if(AddressedUser != null){
            Messages msg = new Messages(MessageID, Message, Subject, Addressed, Username);
            Messages.put(MessageID, msg);
            MessageID++;
            AddressedUser.setMsgList(msg);
            
            return ResultMessage.SendMessageSuccess;
        }
        else
            return ResultMessage.SendMessageNoUser;            
    }

    @Override   // AINDA COM MUITAS CORRECÇÕES A FAZER
    public ResultMessage CreateItem(String Username, String Item, String Category, String Desc, double Price, double BuyNow, String Budget) {
        
        Item item = null;
        
        if(Item != ""){
            item = new Item(ItemID, Price, BuyNow, Item, Desc, Category, Username, Integer.parseInt(Budget));
            Itens.put(ItemID, item);
            Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " add a Item " + Item));
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
    
    @Override
    public ResultMessage CreateAuction(String Username, String Item, long id){
        Users.get(Username);
        Item item = null;
        Auction auction = null;
        
        if(!Itens.isEmpty()){
            if(id>0 && (int) id <= Itens.size()){
                item = Itens.get(id);
                if(item.getOwner() == Username)
                    item = null;
            }
            else
                if(!Item.isEmpty())
                    for(int i = 1; i <= Itens.size(); i++){
                        item = Itens.get(i);
                        if(item.getName()==Item)
                            if(item.getOwner()==Username)
                                i = Itens.size();
                             else
                                item = null;
                        else
                            item = null;
                    }
            
            if(item!=null)
            {
                auction = new Auction(AuctionID, item);
                Auctions.put(AuctionID, auction);
                Newsletter.addNewsToList(new NewsLetterItem("New Auction created: Id[" + AuctionID + "] for the Item " + id + " - " +Item));
                AuctionID++;
                return ResultMessage.AuctionCreated;
            }
            else
                return ResultMessage.AuctionNotCreated;
        }
        else
            return ResultMessage.AuctionNotCreated;
        
    }

    @Override
    public ArrayList<Notification> GetNotifications() {
        return (ArrayList<Notification>) this.Notifications.values();
    }

    @Override
    public ResultMessage ActivateAccount(String Username) {
        
        //validate input
        if(Username == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from list
        User user = Users.get(Username);
        
        if(user != null) 
        {
            boolean isActivated = user.getAccountActivation();
            if(isActivated)    
                return ResultMessage.AccountAllreadyActivated;
            else
            {
                user.setAccountActivation(true);
                
                Newsletter.addNewsToList(new NewsLetterItem("New user registed in system, welcome " + Username));
                
                return ResultMessage.AccountActivated;
            }
        }
        else
            return ResultMessage.LoginInvalidUsername;
    }
    
    @Override
    public ResultMessage SuspendAccount(String Username) {
        
        //validate input
        if(Username == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from list
        User user = Users.get(Username);
        
        if(user != null) 
        {
            boolean isSuspended = user.isAccountSuspension();
            if(isSuspended)    
                return ResultMessage.AccountAllreadySuspended;
            else
            {
                user.setAccountSuspension(true);
                
                Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " as been suspended"));
                
                return ResultMessage.AccountSuspended;
            }
        }
        else
            return ResultMessage.LoginInvalidUsername;
    }
    
    @Override
    public ResultMessage ReActivateAccount(String Username) {
        
        //validate input
        if(Username == null)
            return ResultMessage.LoginInvalidUsername;
        
        //get user from list
        User user = Users.get(Username);
        
        if(user != null) 
        {
            boolean isSuspended = user.isAccountSuspension();
            if(!isSuspended)    
                return ResultMessage.AccountNotSuspended;
            
            boolean isActivated = user.getAccountActivation();
            if(!isActivated)    
                return ResultMessage.AccountNotActivated;
            
            user.setAccountSuspension(false);
            
            Newsletter.addNewsToList(new NewsLetterItem("Removed suspension for User " + Username));
            
            return ResultMessage.AccountReActivated;
            
        }
        else
            return ResultMessage.LoginInvalidUsername;
    }
    
}
