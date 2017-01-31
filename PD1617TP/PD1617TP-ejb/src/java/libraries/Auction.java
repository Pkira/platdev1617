

package libraries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class Auction {
    
    long ID;
    ArrayList<String> AuctionLog;
    Item insale = new Item();
    long AuctionState;       
    long ItemState;
    Calendar StartDate, EndDate;

    public Auction() {
        this.ID = 0;
    }

    public Auction(long ID, Item sale) {
        this.ID = ID;
        this.insale = sale;
        this.AuctionLog = null;
        this.StartDate = Calendar.getInstance();
        this.EndDate = this.StartDate;
        this.EndDate.add(Calendar.HOUR_OF_DAY, sale.getAuctionDuration());
        this.ItemState = 0;
        this.AuctionState = 0;
    }

    public long getID() {
        return ID;
    }

    public ArrayList<String> getAuctionLong() {
        return AuctionLog;
    }

    public Item getInsale() {
        return insale;
    }

    public long isAuctionState() {
        return AuctionState;
    }

    public Calendar getStartDate() {
        return StartDate;
    }

    public Calendar getEndDate() {
        return EndDate;
    }

    public void setAction(ArrayList<String> Action) {
        this.AuctionLog = Action;
    }

    public void setAuctionState(long AuctionState) {
        this.AuctionState = AuctionState;
    }

    public void setStartDate(Calendar StartDate) {
        this.StartDate = StartDate;
    }

    public void setEndDate(Calendar EndDate) {
        this.EndDate = EndDate;
    }

    public long getItemState() {
        return ItemState;
    }

    public void setItemState(long ItemState) {
        this.ItemState = ItemState;
    }
    
    
    
}
