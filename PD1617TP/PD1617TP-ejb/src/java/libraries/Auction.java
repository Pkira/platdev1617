

package libraries;

import java.util.ArrayList;
import java.util.Date;



public class Auction {
    
    int ID;
    ArrayList<String> Action;
    Item insale = new Item();
    boolean state;
    Date StartDate, EndDate;

    public Auction() {
    }

    public Auction(int ID, Item sale) {
        this.ID = ID;
        this.insale = sale;
        this.Action = null;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<String> getAction() {
        return Action;
    }

    public Item getInsale() {
        return insale;
    }

    public boolean isState() {
        return state;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setAction(ArrayList<String> Action) {
        this.Action = Action;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }
    
    
}
