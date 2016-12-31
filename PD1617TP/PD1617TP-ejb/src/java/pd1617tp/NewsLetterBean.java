
package pd1617tp;

import pd1617tplib.ISystem;
import pd1617tplib.INewsLetter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import libraries.NewsLetter;


@Stateless(name="INewsLetter", mappedName="ejb/INewsLetter")
public class NewsLetterBean implements INewsLetter {
  
    @EJB
    private ISystem AServer;
    
    @Override
    public NewsLetter GetNewsLetter() {
        return AServer.GetNewsletter();
    }

}
