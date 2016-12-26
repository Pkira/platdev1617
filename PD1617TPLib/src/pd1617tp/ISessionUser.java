
package pd1617tp;

import java.util.ArrayList;
import javax.ejb.Remote;
import libraries.NewsLetter;


@Remote
public interface ISessionUser {
    
    ResultMessage Login(String name, String password);
    ResultMessage Register(String name, String password);
    boolean CreateUsersBatch();
    boolean LogOff();
    String SeePerfil();
    ResultMessage UpdatePerfil(String Address);
    String CheckBalance();
    ResultMessage LoadBalance(double increment);
    NewsLetter GetNewsletter();
}
