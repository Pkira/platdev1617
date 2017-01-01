
package pd1617tp;

import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class AuctionBean implements IAuction {

    long ID;
    
    @EJB
    private ISystem AServer;
}
