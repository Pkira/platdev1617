
package facades;

import entities.Newsletter;
import java.util.List;
import javax.ejb.LocalBean;

@LocalBean
public interface INewsLetter {
    
    List<Newsletter> GetNewsLetter();
    void addNewsletterItem(String Message);
}
