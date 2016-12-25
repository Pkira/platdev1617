package libraries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User {

    int id;
    String name, address, password;
    ArrayList<String> MsgList;
    double balance;
    boolean state;
    List<Item> SellList;
    List<Item> BuyList;
    List<Item> FollowList;

    public User() {
    }

    public User(int id, String name, String address, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.password = password;
        this.balance = 0;
        this.state = true;
        this.SellList = null;
        this.BuyList = null;
        this.FollowList = null;
        this.MsgList = null;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getMsgList() {
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
        this.address = address;
    }

    public void setMsgList(ArrayList<String> MsgList) {
        this.MsgList = MsgList;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
    
    
    
}
