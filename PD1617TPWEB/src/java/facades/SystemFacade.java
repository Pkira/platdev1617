
package facades;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import utils.ResultMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import entities.Auction;
import entities.Item;
import entities.Newsletter;
import entities.User;
import entities.Message;
import entities.Notification;
import javax.ejb.EJB;


@Singleton
public class SystemFacade implements ISystem {
   
    private HashMap<String,User> Users = new HashMap<>();
    private HashMap<Long,Item> Itens = new HashMap<>();
    private HashMap<Integer,Message> Messages = new HashMap<>();
    private HashMap<Long,Auction> Auctions = new HashMap<>();
    private HashMap<Long,Notification> Notifications = new HashMap<>();
      
    @Override
    public ResultMessage AddUserToActiveList(User user) {
                
        //validate input
        if(user == null )
            return ResultMessage.LoginInvalid;
        
        //get user from list
        User isAlreadyActive = Users.get(user.getUsername());
        
        //check if user is already logged
        if(isAlreadyActive != null) 
        {
            boolean isLogged = user.isLogged();
            if(isLogged)    
                return ResultMessage.LoginAllreadyLogged;
            
            boolean isUnverified = user.getAccountActivation();
            if(!isUnverified)    
                return ResultMessage.AccountNotActivated;
            
            boolean isSuspended = user.getAccountSuspension();
            if(isSuspended)    
                return ResultMessage.AccountSuspended;
        }
        else
        {
            user.setLogged(true);
            user.setLastAction();
            Users.put(user.getUsername(),user);

            return ResultMessage.LoginSucess;
        }         

        return ResultMessage.LoginUserNotFound;
        

    }
    
    @Override
    public boolean CheckUserState(String Username) {
                
        //validate input
        if(Username == null )
            return false;
        
        //get user from list
        User isAlreadyActive = Users.get(Username);
        
        //check if user is already logged
        if(isAlreadyActive != null) 
        {
            return true;           
        }
        else
        {
            return false;
        }         

    }
        
    @Override
    public boolean LogOffUserFromActiveList(String Username){
        //validate input
        if(Username == null)
            return false;
        
        //get user from list
        User user = Users.get(Username);
        
        // if exists 
        if(user != null)
        {
            Users.remove(Username);
            return true;
        }

        return false;
        
    }
    
//
//    @Override  
//    public ResultMessage CreateItem(String Username, String Item, String Category, String Desc, double Price, double BuyNow, String Budget) {
//        
//        try{
//        
//        if(!Item.isEmpty()){
//            Item item = new Item(ItemID, Price, BuyNow, Item, Desc, Category, Username, Integer.parseInt(Budget));
//            Itens.put(ItemID, item);
//            Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " add a Item " + Item));
//            ItemID++;
//            return ResultMessage.CreateItemSuccess;
//        }  
//        
//        }
//        catch(Exception ex)
//        {
//
//        }
//        
//        return ResultMessage.CreateItemUnsuccess;
// 
//    }
//
//    @Override
//    public ArrayList SearchItem(String Item, String Category){
//        
//        ArrayList<Item> Aux = new ArrayList<>();
//        Item help = new Item();
//        if(!Itens.isEmpty())  
//            for(long i = 1; i <= Itens.size(); i++)
//            {
//                help = Itens.get(i);
//                if(help.getID()!=0)
//                    if(help.getName().contains(Item))
//                        if(help.getCategory().contains(Category))
//                            Aux.add(help);
//            }
//        return Aux;
//    }
//    
//    @Override
//    public ResultMessage CreateAuction(String Username, String Item, long id){
//        
//        //validate input
//        if (Username.isEmpty() || Item.isEmpty()) {
//            return ResultMessage.AuctionNotCreated;
//        }
//
//        //get user from list
//        User user = Users.get(Username);
//
//        //check if user is already logged
//        if (user != null) {
//            Item item = new Item();
//            Auction auction = new Auction();
//
//            if (!Itens.isEmpty()) {
//                if (id > 0 && (int) id <= Itens.size()) {
//                    item = Itens.get(id);
//
//                    if (item.getID() != 0 && !item.getOwner().contains(Username)) {
//                        item = new Item();
//                    }
//                } else if (!Item.isEmpty()) {
//                    for (int i = 1; i <= Itens.size(); i++) {
//                        item = Itens.get(i);
//                        if (item.getID() != 0 && item.getName().contentEquals(Item)){
//                            if (item.getOwner().contentEquals(Username)){
//                                i = Itens.size();
//                            } else {
//                                item = new Item();
//                            }
//                        } else {
//                            item = new Item();
//                        }
//                    }
//                }
//
//                if (item.getID() != 0) {
//                    auction = new Auction(AuctionID, item);
//                    Auctions.put(AuctionID, auction);
//                    AuctionID++;
//                    Newsletter.addNewsToList(new NewsLetterItem("New Auction created: Id[" + AuctionID + "]"));
//                    item.setState(true);
//                    return ResultMessage.AuctionCreated;
//                } else {
//                    return ResultMessage.AuctionNotCreated;
//                }
//            } else {
//                return ResultMessage.AuctionNotCreated;
//            }
//        }
//
//        return ResultMessage.AuctionNotCreated; 
//    }
//
//    @Override
//    public ArrayList<Notification> GetNotifications() {
//        return (ArrayList<Notification>) this.Notifications.values();
//    }
//    
//    @Override
//    public ResultMessage AskReactivation(String name){
//         
//         String notification = "A visitor ask for the reactivation of the account " + name;
//         User user = Users.get(name);
//         Notification note;
//         if(user != null)
//            if(!user.getAccountActivation()){
//                note = new Notification(name, notification, NotificationID);
//                Notifications.put(NotificationID, note);
//                NotificationID++;
//                return ResultMessage.AccountReactivation;
//            }
//            else
//                return ResultMessage.AccountReactivationFail;
//                 
//        return ResultMessage.AccountReactivationNoUser;
//    }
//
//    @Override
//    public ResultMessage ActivateAccount(String Username) {
//        
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isActivated = user.getAccountActivation();
//            if(isActivated)    
//                return ResultMessage.AccountAllreadyActivated;
//            else
//            {
//                user.setAccountActivation(true);
//                
//                Newsletter.addNewsToList(new NewsLetterItem("New user registed in system, welcome " + Username));
//                
//                return ResultMessage.AccountActivated;
//            }
//        }
//        else
//            return ResultMessage.LoginInvalidUsername;
//    }
//    
//    @Override
//    public ResultMessage SuspendAccount(String Username) {
//        
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isSuspended = user.isAccountSuspension();
//            if(isSuspended)    
//                return ResultMessage.AccountAllreadySuspended;
//            else
//            {
//                user.setAccountSuspension(true);
//                
//                Newsletter.addNewsToList(new NewsLetterItem("User " + Username + " as been suspended"));
//                
//                return ResultMessage.AccountSuspended;
//            }
//        }
//        else
//            return ResultMessage.LoginInvalidUsername;
//    }
//    
//    @Override
//    public ResultMessage ReActivateAccount(String Username) {
//        
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            boolean isSuspended = user.isAccountSuspension();
//            if(!isSuspended)    
//                return ResultMessage.AccountNotSuspended;
//            
//            boolean isActivated = user.getAccountActivation();
//            if(!isActivated)    
//                return ResultMessage.AccountNotActivated;
//            
//            user.setAccountSuspension(false);
//            
//            Newsletter.addNewsToList(new NewsLetterItem("Removed suspension for User " + Username));
//            
//            return ResultMessage.AccountReActivated;
//            
//        }
//        else
//            return ResultMessage.LoginInvalidUsername;
//    }
//    
//    @Override
//    public ResultMessage ChangePassword(String Username, String Password) {
//        
//        //validate input
//        if(Username == null)
//            return ResultMessage.LoginInvalidUsername;
//        
//        //get user from list
//        User user = Users.get(Username);
//        
//        if(user != null) 
//        {
//            user.setPassword(Password);        
//            return ResultMessage.AccountPasswordChanged;
//            
//        }
//        else
//            return ResultMessage.LoginInvalidUsername;
//    }
//    
//    @Override
//    public String SeeUserProfile(String Username){
//        
//        User user = Users.get(Username);
//        
//        if(user != null)
//            return "Username:  " + user.getName() + "\nAddress:  " + user.getAddress() + "\nBalance:  " + user.getBalance() + "\n";
//        else
//            return "User doesn't exist";
//    }
//    
//    @Override
//    public ArrayList ItemInSell(String username){
//        
//        ArrayList<Item> it = new ArrayList();
//        Item aux = new Item();
//        
//        if(!Itens.isEmpty()){
//            for(long i = 1; i <= Itens.size(); i++){
//                aux = Itens.get(i);
//                if(aux.getOwner().contains(username) && aux.isState()){
//                        it.add(aux);
//                    }
//                }
//            }            
//        
//        
//        return it;
//                
//    }    
//    
//    @Override
//    public List FollowItens(String username){
//        
//        User user = Users.get(username);
//        List<Item> ItensFollow = new ArrayList<>();
//        List<Long> ItensIdFollow = new ArrayList<>();
//        
//        if(user != null){
//            if(!user.getFollowList().isEmpty()){
//                ItensIdFollow = user.getFollowList();
//                for(int i = 0; i < ItensIdFollow.size(); i++){
//                    ItensFollow.add(Itens.get(ItensIdFollow.get(i)));
//                }
//            }
//        }
//        
//        return ItensFollow;
//    }
//
//    @Override
//    public ResultMessage FollowItem(Long Item, String Username) {
//        
//        User user = Users.get(Username);
//        List<Long> IdItens = new ArrayList<>();
//        boolean exist = false;
//        
//        if(Itens.containsKey(Item) && user != null){        
//        
//           IdItens = user.getFollowList();
//           if(!IdItens.isEmpty())
//               for(int i = 0; i < IdItens.size(); i++){
//                   if(Item.equals(IdItens.get(i))){
//                       exist = true;
//                   }
//               }
//           if(!exist && !user.getName().equals(Itens.get(Item).getOwner())){
//               IdItens.add(Item);
//               user.setFollowList(IdItens);
//               return ResultMessage.FollowItemSucess;
//           }else{
//               return ResultMessage.FollowItemAlreadyFollow;
//           }
//        }   
//        return ResultMessage.ItemNotExist;
//    }
//
//    @Override
//    public ResultMessage CancelFollowItem(Long Item, String Username) {
//        
//        User user = Users.get(Username);
//        List<Long> IdItens = new ArrayList<>();
//        boolean exist = false;
//        
//        if(user != null){        
//        
//           IdItens = user.getFollowList();
//           if(!IdItens.isEmpty())
//               for(int i = 0; i < IdItens.size(); i++){
//                   if(Item.equals(IdItens.get(i))){
//                       exist = true;
//                   }
//               }
//           if(exist){
//               IdItens.remove(Item);
//               user.setFollowList(IdItens);
//               return ResultMessage.CancelFollowItemSucess;
//           }else{
//               return ResultMessage.CancelFollowItemAlreadyNotFollow;
//           }
//        }   
//        return ResultMessage.CancelFollowItemError;
//    }
//    
//    @Override
//    public ResultMessage ReportItem(String name, Long Id){
//         
//         String notification = "User " + name + " have reported the item with the Id " + Id;
//         Notification note;
//        if (Itens.get(Id) != null) {
//            note = new Notification(name, notification, NotificationID);
//            Notifications.put(NotificationID, note);
//            NotificationID++;
//            return ResultMessage.ReportSuccess;
//        } else {
//            return ResultMessage.ReportInsuccess;
//        }
//    }
//    
//    @Override
//    public ResultMessage ReportUser(String name, String reported){
//         
//         String notification = "User " + name + " have reported the user " + reported;
//         Notification note;
//        if (Users.get(reported) != null) {
//            note = new Notification(name, notification, NotificationID);
//            Notifications.put(NotificationID, note);
//            NotificationID++;
//            return ResultMessage.ReportSuccess;
//        } else {
//            return ResultMessage.ReportInsuccess;
//        }
//    }
}
