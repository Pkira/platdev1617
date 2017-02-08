/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedro Salgado
 */
@Entity
@Table(name = "t_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByName", query = "SELECT i FROM Item i WHERE i.name = :name"),
    @NamedQuery(name = "Item.findByDescription", query = "SELECT i FROM Item i WHERE i.description = :description"),
    @NamedQuery(name = "Item.findByStartprice", query = "SELECT i FROM Item i WHERE i.startprice = :startprice"),
    @NamedQuery(name = "Item.findByBuynowprice", query = "SELECT i FROM Item i WHERE i.buynowprice = :buynowprice"),
    @NamedQuery(name = "Item.findByAuctionduration", query = "SELECT i FROM Item i WHERE i.auctionduration = :auctionduration")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startprice")
    private double startprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "buynowprice")
    private double buynowprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctionduration")
    private long auctionduration;
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryid;
    @JoinColumn(name = "ownerid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User ownerid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemid")
    private Collection<Auction> auctionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemid")
    private Collection<UserItem> userItemCollection;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(Long id, String name, String description, double startprice, double buynowprice, long auctionduration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startprice = startprice;
        this.buynowprice = buynowprice;
        this.auctionduration = auctionduration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartprice() {
        return startprice;
    }

    public void setStartprice(double startprice) {
        this.startprice = startprice;
    }

    public double getBuynowprice() {
        return buynowprice;
    }

    public void setBuynowprice(double buynowprice) {
        this.buynowprice = buynowprice;
    }

    public long getAuctionduration() {
        return auctionduration;
    }

    public void setAuctionduration(long auctionduration) {
        this.auctionduration = auctionduration;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
    }

    public User getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(User ownerid) {
        this.ownerid = ownerid;
    }

    @XmlTransient
    public Collection<Auction> getAuctionCollection() {
        return auctionCollection;
    }

    public void setAuctionCollection(Collection<Auction> auctionCollection) {
        this.auctionCollection = auctionCollection;
    }

    @XmlTransient
    public Collection<UserItem> getUserItemCollection() {
        return userItemCollection;
    }

    public void setUserItemCollection(Collection<UserItem> userItemCollection) {
        this.userItemCollection = userItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Item[ id=" + id + " ]";
    }
    
}
