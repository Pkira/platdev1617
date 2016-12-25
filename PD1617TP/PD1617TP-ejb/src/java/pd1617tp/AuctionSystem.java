
package pd1617tp;

import javax.ejb.Singleton;


@Singleton
public class AuctionSystem implements IAuctionSystem {

    @Override
    public boolean LoginUser(String Username, String Password) {
        return false;
    }

}
