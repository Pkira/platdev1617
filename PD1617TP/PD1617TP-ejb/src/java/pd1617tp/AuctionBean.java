
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import libraries.ResultMessage;


@Stateful
public class AuctionBean implements IAuction {

    long ID;
    
    @EJB
    private ISystem AServer;
    
     public ResultMessage CreateAuction(String Username, String Item, long id){
        return AServer.CreateAuction(Username, Item, id);
    }
}
