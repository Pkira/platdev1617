/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.ejb.LocalBean;
import utils.ResultMessage;

/**
 *
 * @author Caike
 */
@LocalBean
public interface IAuction {
    
     ResultMessage CreateAuction(String Username, String Item, long id);
     
}
