
package facades;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import utils.ResultMessage;


@Stateful
public class AuctionFacade implements IAuction {

    long ID;
    
    @EJB
    private ISystem AServer;
    
//     public ResultMessage CreateAuction(String Username, String Item, long id){
//        return AServer.CreateAuction(Username, Item, id);
//    }
//     
//    @Override
//    public ResultMessage ReportItem(String name, Long Id) {
//        return AServer.ReportItem(name, Id);
//    }
//    
//    @Override
//    public ResultMessage ReportUser(String name, String reported) {
//        return AServer.ReportUser(name, reported);
//    }
}
