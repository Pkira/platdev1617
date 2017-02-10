
package controllers;
import entities.Item;
import facades.IItem;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import utils.ResultMessage;


@Named(value = "itemController")
@RequestScoped
public class ItemController {

    @EJB
    private IItem itemFacade;
    
    private long id;
    private String name;
    private String category;
    private String description;
    private double price;
    private double buynow;
    private String image;
    private String owner;
    private long ownerid;
    private long auctionduration;
    
    public ItemController() {
       
    }
    
    public String ShowDetails(long Id) {
        
        Item item = itemFacade.GetItemById(Id);
        
        this.id = item.getId();
        this.name = item.getName();
        this.category = item.getCategoryid().getName();
        this.description = item.getDescription();
        this.price = item.getStartprice();
        this.buynow = item.getBuynowprice();
        this.image = item.getImage();
        this.owner = item.getOwnerid().getUsername();
        this.ownerid = item.getOwnerid().getId();
        
        return "ItemDetails.xhtml";
    }
    
    public String AddNewItem(long UserId) {
        
        this.ownerid = UserId;
        
        return "ItemAddNew.xhtml";
    }
    
    public void insertNewItem() {
        
        FacesContext context = FacesContext.getCurrentInstance();
         
        ResultMessage result = itemFacade.CreateItem(this.ownerid, this.name, this.category, this.description, this.price,this.buynow, this.auctionduration, this.image);
        
        if(result != ResultMessage.CreateItemSuccess)
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            return;
        }
        
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    
}