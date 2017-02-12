
package controllers;

import entities.Auction;
import facades.IAuction;
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
    
    public String BidItem (){
          //variaveis a receber se possível (Long UserId, Long value, Long AuctionId)
        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            //variaveis a enviar (Long UserId, Long value, Long AuctionId)
            result = auctionFacade.BidItem((long)1,(long)1,(long)7);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", null));
        }
        if(result == ResultMessage.BidSuccess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "AuctionList.xhtml";
        
    }
    
    public String BuyNowItem (){
          //variaveis a receber se possível (Long UserId, Long value, Long AuctionId)
        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
              //variaveis a enviar (Long UserId, Long value, Long AuctionId)
            result = auctionFacade.BuyNowItem((long)2,(long)1,(long)8);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", null));
        }
        if(result == ResultMessage.BuyNowSuccess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "AuctionList.xhtml";
        
    }
}
