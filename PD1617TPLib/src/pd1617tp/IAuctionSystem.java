
package pd1617tp;

import javax.ejb.LocalBean;


@LocalBean

public interface IAuctionSystem {
    
     ResultMessage LoginUser(String Username, String Password);   
     ResultMessage RegisterUser(String Username, String Password);
     boolean LogOffUser(String Username);
}
