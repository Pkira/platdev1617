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
    double Balance;
    boolean AccountActivation;
    boolean AccountSuspension;
    
    ArrayList<Messages> MsgList;
    List<Long> SellList;
    List<Long> BuyList;
    List<Long> FollowList;
    
    boolean Advised;
    boolean Logged;
    long LastAction;

    
    public User(){}
            
    public User(String Username, String Password, String Address) {
        this.Username = Username;
        this.Password = Password;
        this.Address = Address;
        this.Balance = 0;
        this.AccountActivation = false;
        this.SellList = new ArrayList<>();
        this.BuyList = new ArrayList<>();
        this.FollowList = new ArrayList<>();
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
        return Balance;
    }
    
    public ArrayList<Messages> getMsgList() {
        return MsgList;
    }

    public boolean getAccountActivation() {
        return AccountActivation;
    }

    public List<Long> getSellList() {
        return SellList;
    }

    public List<Long> getBuyList() {
        return BuyList;
    }

    public List<Long> getFollowList() {
        return FollowList;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setMsgList(Messages MSG) {
        this.MsgList.add(MSG);
    }

    public void setBalance(double balance) {
        this.Balance = this.Balance + balance;
    }

    public void setAccountActivation(boolean state) {
        this.AccountActivation = state;
    }

    public void setSellList(List<Long> SellList) {
        this.SellList = SellList;
    }

    public void setBuyList(List<Long> BuyList) {
        this.BuyList = BuyList;
    }

    public void setFollowList(List<Long> FollowList) {
        this.FollowList = FollowList;
    }
    
     public void setLogged(boolean Logged) {
        this.Logged = Logged;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isAccountSuspension() {
        return AccountSuspension;
    }

    public void setAccountSuspension(boolean AccountSuspension) {
        this.AccountSuspension = AccountSuspension;
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
