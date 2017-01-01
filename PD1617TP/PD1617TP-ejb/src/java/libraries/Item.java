package libraries;

import java.util.Date;

public class Item {

    int ID, StartPrice, BuyNowPrice;
    String Name, Desc, Category;
    boolean State;
    Date budget;

    public Item() {
    }

    public Item(int ID) {
        this.ID = ID;
    }

    public Item(long ItemID, double Price, double BuyNow, String Item, String Desc, String Category, String Username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getID() {
        return ID;
    }

    public int getStartPrice() {
        return StartPrice;
    }

    public int getBuyNowPrice() {
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

    public Date getBudget() {
        return budget;
    }

    public void setStartPrice(int StartPrice) {
        this.StartPrice = StartPrice;
    }

    public void setBuyNowPrice(int BuyNowPrice) {
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
