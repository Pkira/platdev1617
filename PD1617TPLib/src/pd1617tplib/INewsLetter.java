
package pd1617tplib;

import javax.ejb.Remote;
import libraries.NewsLetter;

@Remote
public interface INewsLetter {
    NewsLetter GetNewsLetter();
}
