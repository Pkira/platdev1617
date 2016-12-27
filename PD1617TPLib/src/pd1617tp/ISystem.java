
package pd1617tp;

import libraries.ResultMessage;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.jms.Message;
import libraries.NewsLetter;


@LocalBean

public interface ISystem {
    
     ResultMessage LoginUser(String Username, String Password);   
     ResultMessage RegisterUser(String Username, String Password);
     boolean LogOffUser(String Username);
     String SeePerfil(String Username);
     ResultMessage UpdatePerfil(String Username, String Address);
     String CheckBalance(String Username);
     ResultMessage LoadBalance(String Username, double increment);
     NewsLetter GetNewsletter();
     ArrayList CheckMessage(String Username);
     ResultMessage SendMessage(String Username, String Addressed, String Subject, String Message);
}
