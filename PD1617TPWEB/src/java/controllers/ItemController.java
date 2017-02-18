
package controllers;
import entities.Category;
import entities.Item;
import facades.IAuction;
import facades.IItem;
import facades.IUser;
import facades.IVisitor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import utils.ResultMessage;


@Named(value = "itemController")
@RequestScoped
public class ItemController {

    @EJB
    private IItem itemFacade;
    
    @EJB
    private IAuction auctionFacade;
    
    @EJB
    private IVisitor visitorFacade;
    
    @Inject
    UserController userController ;
    
    private long id;
    private String name;
    private String searchname;
    private long categoryid;
    private String categoryname;
    private String description;
    private double price;
    private double buynow;
    private double minPrice;
    private double maxPrice;
    private String image;
    private String owner;
    private long ownerid;
    private long auctionduration;
    private boolean showcancelitem;
    
    private List<Item> resultSearch;
    
    public ItemController() {
       
        this.id = 0;
        this.name = "";
        this.searchname ="";
        this.categoryid = 0;
        this.categoryname = "";
        this.description = "";
        this.price = 0;
        this.buynow = 0;
        this.minPrice = 0;
        this.maxPrice = 0;
        this.image = "";
        this.owner = "";
        this.ownerid = 0;
        this.auctionduration = 0;
        this.showcancelitem = false;
    }
    
    
    public String ShowDetails(long Id) {
        
        Item item = itemFacade.GetItemById(Id);
        
        this.id = item.getId();
        this.name = item.getName();
        this.categoryid = 0;
        this.description = item.getDescription();
        this.price = item.getStartprice();
        this.buynow = item.getBuynowprice();
        this.image = item.getImage();
        this.owner = item.getOwnerid().getUsername();
        this.ownerid = item.getOwnerid().getId();
        this.categoryname = item.getCategoryid().getName();
        
        if(userController.getUserid() == this.ownerid || userController.getUsername().equals("admin"))
            this.showcancelitem = true;
            
        return "ItemDetails.xhtml";
    }
    
    public String AddNewItem(long UserId) {
        
        this.ownerid = UserId;
        
        return "ItemAddNew.xhtml";
    }
    
    public String insertNewItem() {
        
        FacesContext context = FacesContext.getCurrentInstance();
       
        ResultMessage result = itemFacade.CreateItem(this.ownerid, this.name, this.categoryid, this.description, this.price,this.buynow, this.auctionduration, this.image);
        
        if(result != ResultMessage.CreateItemSuccess)
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            return null;
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        
        return "UserItems.xhtml";
        
    }
    
    public String AddItemToAuction(long ItemId){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            result = auctionFacade.CreateAuction(ItemId);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        }
        if(result == ResultMessage.AuctionCreated)
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        }
        else
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        }
        
        return "UserItemsInAuction.xhtml";
        
    }
    
    public List<Category> getCategories(){
        
        List<Category> categories = itemFacade.GetAllCategories();
        
        return categories;
    }
    
    public List<Item> getLastItensSell(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Item> items = new ArrayList<Item>();
        try {
            items = visitorFacade.SeeLastSellItens();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error getting last selled items", null));
        }
        
        if(items.isEmpty())
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There are no items sell in the past days", null));
        
        return items;
        
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long category) {
        this.categoryid = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getBuynow() {
        return buynow;
    }

    public void setBuynow(double buynow) {
        this.buynow = buynow;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public long getAuctionduration() {
        return auctionduration;
    }

    public void setAuctionduration(long auctionduration) {
        this.auctionduration = auctionduration;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<Item> getResultSearch() {
        return resultSearch;
    }

    public void setResultSearch(List<Item> resultSearch) {
        this.resultSearch = resultSearch;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }

    public boolean getShowcancelitem() {
        return showcancelitem;
    }

    public void setShowcancelitem(boolean showcancelitem) {
        this.showcancelitem = showcancelitem;
    }
    
    

    public String searchItemByName() {

        FacesContext context = FacesContext.getCurrentInstance();
        resultSearch = new ArrayList();
        try {
            resultSearch = itemFacade.SearchItemByName(this.name);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error searching Item, please try again later.", null));
        }
        
        return "ItemSearch.xhtml";
    }
    
    public void searchItem() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            resultSearch = itemFacade.SearchItem(this.searchname, this.categoryname, this.owner, this.minPrice, this.maxPrice);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error searching Item, please try again later.", null));
        }
    }
    
    
}
