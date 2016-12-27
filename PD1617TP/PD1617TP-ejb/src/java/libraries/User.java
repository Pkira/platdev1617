package libraries;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User implements Serializable{

    int Id;
    
    String Username;
    String Password;
    String Address;   
    double balance;
    boolean state;
    
    ArrayList<Messages> MsgList;
    List<Item> SellList;
    List<Item> BuyList;
    List<Item> FollowList;
    
    boolean Advised;
    boolean Logged;
    long LastAction;

    
    public User(){}
            
    public User(String Username, String Password, String Address) {
        this.Username = Username;
        this.Password = Password;
        this.Address = Address;
        this.balance = 0;
        this.state = true;
        this.SellList = new ArrayList<Item>();
        this.BuyList = new ArrayList<Item>();
        this.FollowList = new ArrayList<Item>();
        this.MsgList = new ArrayList<Messages>();
        this.Logged = false;
        this.Advised = false;
        this.LastAction = 0;
    }

    public boolean isLogged()
    { 
        return Logged; 
    }
    
    public void LogOff()
    { 
        Logged = false; 
    }
    
    public void setLastAction()
    {
        this.LastAction = LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond();
    }
    
    public String getName() {
        return Username;
    }

    public String getAddress() {
        return Address;
    }

    public String getPassword() {
        return Password;
    }

    public double getBalance() {
        return balance;
    }
    
    public ArrayList<Messages> getMsgList() {
        return MsgList;
    }

    public boolean isState() {
        return state;
    }

    public List<Item> getSellList() {
        return SellList;
    }

    public List<Item> getBuyList() {
        return BuyList;
    }

    public List<Item> getFollowList() {
        return FollowList;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setMsgList(Messages MSG) {
        this.MsgList.add(MSG);
    }

    public void setBalance(double balance) {
        this.balance = this.balance + balance;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setSellList(List<Item> SellList) {
        this.SellList = SellList;
    }

    public void setBuyList(List<Item> BuyList) {
        this.BuyList = BuyList;
    }

    public void setFollowList(List<Item> FollowList) {
        this.FollowList = FollowList;
    }
    
     public void setLogged(boolean Logged) {
        this.Logged = Logged;
    }
    
    @Override
    public int hashCode(){
        return Username.hashCode(); //necessariamente
                                //qual e como? obviamente -> Name.hashCode()
    }
    
    @Override
    public boolean equals(Object x){
        if(x == null)
            return false;
        if(getClass() != x.getClass()) //instanceof seria solução fraca
            return false;              //(quebraria o contrato da equals)
        User j = (User) x;
        return Username.compareToIgnoreCase(j.Username) == 0;
    }
    
}
