
package pd1617tp;

import javax.ejb.Remote;
import libraries.NewsLetter;

@Remote
public interface INewsLetter {
    NewsLetter GetNewsLetter();
}
