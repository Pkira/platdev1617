
package pd1617tp;

import libraries.ResultMessage;
import java.util.ArrayList;
import javax.ejb.Remote;
import javax.jms.Message;


@Remote
public interface IUser {
    
    ResultMessage Login(String name, String password);
    boolean LogOff();
    String SeeProfile();
    ResultMessage UpdateProfile(String Address);
    String CheckBalance();
    ResultMessage LoadBalance(double increment);
    ArrayList CheckMessage();
    ResultMessage SendMessage(String Addressed, String Subject, String Message);
    ResultMessage CreateItem(String Item, String Category, String Desc, double Price, double BuyNow, String Budget);
    String SearchItem(String Item, String Category);
}
