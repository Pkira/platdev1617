/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Auction;
import java.util.List;
import javax.ejb.LocalBean;
import utils.ResultMessage;

/**
 *
 * @author Caike
 */
@LocalBean
public interface IAuction {
    
    Auction GetAuctionById(long AuctionId);
    List<Auction> GetAll();
    ResultMessage CreateAuction(long ItemId);
    ResultMessage BidItem(long UserId, double value, long AuctionId);
    ResultMessage BuyNowItem(long UserId, long AuctionId);
    List<Auction> GetUserBuyingAuctions(long UserId);
}
