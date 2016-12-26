

package libraries;

import java.io.Serializable;
import java.util.ArrayList;


public class NewsLetter implements Serializable{
    
    private ArrayList<NewsLetterItem> NewsList;
    
    public NewsLetter()
    {
        this.NewsList = new ArrayList<NewsLetterItem>();
    }

    public ArrayList<NewsLetterItem> getNewsList() {
        return NewsList;
    }

    public void setNewsList(ArrayList<NewsLetterItem> NewsList) {
        this.NewsList = NewsList;
    }
    
    public void addNewsToList(NewsLetterItem Item)
    {
        this.NewsList.add(Item);
    }
    
}
