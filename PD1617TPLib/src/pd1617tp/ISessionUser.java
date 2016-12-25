/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Pedro Salgado
 */
@Remote
public interface ISessionUser {
    
    ResultMessage Login(String name, String password);
    ResultMessage Register(String name, String password);
    boolean LogOff();
    
}
