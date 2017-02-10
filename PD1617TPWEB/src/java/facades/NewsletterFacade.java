
package facades;

import controllers.IDAO;
import javax.ejb.Stateless;
import entities.Newsletter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

@Stateless
public class NewsletterFacade implements INewsLetter { 

    @EJB
    private IDAO dAO;      
    
    @Override
    public List<Newsletter> GetNewsLetter() {
        return dAO.getEntityManager().createNamedQuery("Newsletter.findAll").getResultList();
    }

    @Override
    public void addNewsletterItem(String Message) {
        
        Newsletter newNewsletter = new Newsletter();
        newNewsletter.setId((long)-1);
        newNewsletter.setMessage(Message);
        newNewsletter.setCreationdate(new Date());
        
        dAO.getEntityManager().persist(newNewsletter);
    }

}
