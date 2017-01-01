
package pd1617tplib;

import javax.ejb.Remote;
import libraries.ResultMessage;
import java.util.ArrayList;

@Remote
public interface IUser {
    
    ResultMessage Login(String name, String password);
    ResultMessage Register(String name, String password);
    boolean CreateUsersBatch();
    boolean LogOff();
    String SeePerfil();
    ResultMessage UpdatePerfil(String Address);
    String CheckBalance();
    ResultMessage LoadBalance(double increment);
    ArrayList CheckMessage();
    ResultMessage SendMessage(String Addressed, String Subject, String Message);
}
