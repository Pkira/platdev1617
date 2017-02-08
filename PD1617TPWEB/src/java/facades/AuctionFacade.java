
package facades;

import entities.Auction;
import entities.Item;
import entities.Newsletter;
import entities.User;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import utils.ResultMessage;


@Stateful
public class AuctionFacade implements IAuction {

    long ID;
    
    @EJB
    private ISystem AServer;
    
     public ResultMessage CreateAuction(String Username, String Item, long id){
       
//          //validate input
//        if (Username.isEmpty() || Item.isEmpty()) {
//            return ResultMessage.AuctionNotCreated;
//        }
//
//        //get user from list
//        User user = Users.get(Username);
//
//        //check if user is already logged
//        if (user != null) {
//            Item item = new Item();
//            Auction auction = new Auction();
//
//            if (!Itens.isEmpty()) {
//                if (id > 0 && (int) id <= Itens.size()) {
//                    item = Itens.get(id);
//
//                    if (item.getID() != 0 && !item.getOwner().contains(Username)) {
//                        item = new Item();
//                    }
//                } else if (!Item.isEmpty()) {
//                    for (int i = 1; i <= Itens.size(); i++) {
//                        item = Itens.get(i);
//                        if (item.getID() != 0 && item.getName().contentEquals(Item)){
//                            if (item.getOwner().contentEquals(Username)){
//                                i = Itens.size();
//                            } else {
//                                item = new Item();
//                            }
//                        } else {
//                            item = new Item();
//                        }
//                    }
//                }
//
//                if (item.getID() != 0) {
//                    auction = new Auction(AuctionID, item);
//                    Auctions.put(AuctionID, auction);
//                    AuctionID++;
//                    Newsletter.addNewsToList(new NewsLetterItem("New Auction created: Id[" + AuctionID + "]"));
//                    item.setState(true);
//                    return ResultMessage.AuctionCreated;
//                } else {
//                    return ResultMessage.AuctionNotCreated;
//                }
//            } else {
//                return ResultMessage.AuctionNotCreated;
//            }
//        }

        return ResultMessage.AuctionNotCreated; 
    }
     
}
