/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp;

import javax.ejb.Remote;
import libraries.ResultMessage;

/**
 *
 * @author Caike
 */
@Remote
public interface IAuction {
    
     ResultMessage CreateAuction(String Username, String Item, long id);
     ResultMessage ReportItem(String name, Long Id);
     ResultMessage ReportUser(String name, String reported);
}
