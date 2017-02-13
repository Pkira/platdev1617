
package controllers;

import entities.Auction;
import facades.IAuction;
import facades.IUser;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import utils.ResultMessage;


@Named(value = "auctionController")
@RequestScoped
public class AuctionController {

    @EJB
    private IAuction auctionFacade;
    
    private long auctionid;
    private long bidvalue;
    
    private Auction auction;

    public AuctionController() {
        
    }
    
    public List<Auction> getAll()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Auction> auctions = null;
        
        try {
            auctions = auctionFacade.GetAll();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error getting auctions list", null));
        }
        
        return auctions;
    }
    
    public String bidItemRedirect(long auctionid)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        this.auctionid = auctionid;
        
        Auction auctionDB = null;
                
        try {
            auctionDB = auctionFacade.GetAuctionById(auctionid);
            
            if(auctionDB == null)
            {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResultMessage.AuctionNotFound.Message(), null));
                return "AuctionList.xhtml";
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResultMessage.AuctionNotFound.Message(), null));
            return "AuctionList.xhtml";
        }
        
        this.auction = auctionDB;
        
        return "AuctionBidItem.xhtml";
    }
    
    public String bidItem(long userid){

        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            result = auctionFacade.BidItem(userid,bidvalue,auctionid);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", null));
        }
        if(result == ResultMessage.BidSuccess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "AuctionList.xhtml";
        
    }
    
    public String buyNowItem(long userid){

        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            result = auctionFacade.BuyNowItem(userid,auctionid);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", null));
        }
        if(result == ResultMessage.BuyNowSuccess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "AuctionList.xhtml";
        
    }

    public long getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(long auctionid) {
        this.auctionid = auctionid;
    }

    public long getBidvalue() {
        return bidvalue;
    }

    public void setBidvalue(long bidvalue) {
        this.bidvalue = bidvalue;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    
}
