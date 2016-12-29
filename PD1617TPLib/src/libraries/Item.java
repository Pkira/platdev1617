package libraries;

import java.io.Serializable;

public class Item implements Serializable{

    long ID;
    double StartPrice;
    double BuyNowPrice;
    String Name;
    String Desc;
    String Category;
    String Owner;
    boolean State;
    long AuctionDuration;

    public Item() {
    }

    public Item(int ID) {
        this.ID = ID;
    }

    public Item(long ID, double StartPrice, double BuyNowPrice, String Name, String Desc, String Category, String Owner){
        this.ID = ID;
        this.StartPrice = StartPrice;
        this.BuyNowPrice = BuyNowPrice;
        this.Name = Name;
        this.Desc = Desc;
        this.Category = Category;
        this.State = false;
        this.Owner = Owner;
    }

    public long getID() {
        return ID;
    }

    public double getStartPrice() {
        return StartPrice;
    }

    public double getBuyNowPrice() {
        return BuyNowPrice;
    }

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public String getCategory() {
        return Category;
    }

    public boolean isState() {
        return State;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String Owner) {
        this.Owner = Owner;
    }
    
    public void setStartPrice(double StartPrice) {
        this.StartPrice = StartPrice;
    }

    public void setBuyNowPrice(double BuyNowPrice) {
        this.BuyNowPrice = BuyNowPrice;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setState(boolean State) {
        this.State = State;
    }
    
    
}
