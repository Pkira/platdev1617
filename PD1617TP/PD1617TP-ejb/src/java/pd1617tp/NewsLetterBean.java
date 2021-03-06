
package pd1617tp;

import pd1617tp.ISystem;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import libraries.NewsLetter;

@Stateless
public class NewsLetterBean implements INewsLetter {
  
    @EJB
    private ISystem AServer;
    
    @Override
    public NewsLetter GetNewsLetter() {
        return AServer.GetNewsletter();
    }

}
