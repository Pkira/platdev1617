
package facades;

import entities.Message;
import java.util.List;
import javax.ejb.Local;
import utils.ResultMessage;


@Local
public interface IMessage {

    List<Message> GetMessagesToUser(long UserId);
    ResultMessage SendMessage(long FromUserId, String ToUsername, String Subject, String Message);
}
