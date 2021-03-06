/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Caike
 */
@Entity
@Table(name = "t_auction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "Auction.findById", query = "SELECT a FROM Auction a WHERE a.id = :id"),
    @NamedQuery(name = "Auction.findByAuctionstate", query = "SELECT a FROM Auction a WHERE a.auctionstate = :auctionstate"),
    @NamedQuery(name = "Auction.findByItemstate", query = "SELECT a FROM Auction a WHERE a.itemstate = :itemstate"),
    @NamedQuery(name = "Auction.findByStartdate", query = "SELECT a FROM Auction a WHERE a.startdate = :startdate"), 
    @NamedQuery(name = "Auction.findByEnddate", query = "SELECT a FROM Auction a WHERE a.enddate = :enddate"),
    @NamedQuery(name = "Auction.findByLastbid", query = "SELECT a FROM Auction a WHERE a.lastbid = :lastbid"),
    @NamedQuery(name = "Auction.findByItem", query = "SELECT a FROM Auction a WHERE a.itemid = :itemid"),
    @NamedQuery(name = "Auction.findByItemAndAuctionState", query = "SELECT a FROM Auction a WHERE a.itemid = :itemid And a.auctionstate = :auctionstate"),
    @NamedQuery(name = "Auction.findLastItemSell", query = "SELECT a FROM Auction a WHERE a.auctionstate = 0 ORDER BY a.enddate DESC"),
    @NamedQuery(name = "Auction.findByLastuserid", query = "SELECT a FROM Auction a WHERE a.lastuserid = :lastuserid"),
    @NamedQuery(name = "Auction.findByAuctionstateAndLastUserId", query = "SELECT a FROM Auction a WHERE a.auctionstate = :auctionstate AND a.lastuserid = :lastuserid"),
    @NamedQuery(name = "Auction.findByAuctionstateAndSellerId", query = "SELECT a FROM Auction a WHERE a.auctionstate = :auctionstate AND a.sellerid = :sellerid"),
})
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctionstate")
    private int auctionstate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "itemstate")
    private int itemstate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastbid")
    private double lastbid;
    @JoinColumn(name = "itemid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Item itemid;
    @JoinColumn(name = "lastuserid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastuserid;
    @JoinColumn(name = "sellerid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User sellerid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auctionid")
    private Collection<AuctionLog> auctionLogCollection;
    
    @Transient 
    private long HoursLeft;

    public Auction() {
    }

    public Auction(Long id) {
        this.id = id;
    }

    public Auction(Long id, int auctionstate, int itemstate, Date startdate, Date enddate, double lastbid) {
        this.id = id;
        this.auctionstate = auctionstate;
        this.itemstate = itemstate;
        this.startdate = startdate;
        this.enddate = enddate;
        this.lastbid = lastbid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAuctionstate() {
        return auctionstate;
    }

    public void setAuctionstate(int auctionstate) {
        this.auctionstate = auctionstate;
    }

    public int getItemstate() {
        return itemstate;
    }

    public void setItemstate(int itemstate) {
        this.itemstate = itemstate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public double getLastbid() {
        return lastbid;
    }

    public void setLastbid(double lastbid) {
        this.lastbid = lastbid;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    public User getLastuserid() {
        return lastuserid;
    }

    public void setLastuserid(User lastuserid) {
        this.lastuserid = lastuserid;
    }

    public User getSellerid() {
        return sellerid;
    }

    public void setSellerid(User sellerid) {
        this.sellerid = sellerid;
    }

    @XmlTransient
    public Collection<AuctionLog> getAuctionLogCollection() {
        return auctionLogCollection;
    }

    public void setAuctionLogCollection(Collection<AuctionLog> auctionLogCollection) {
        this.auctionLogCollection = auctionLogCollection;
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
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Auction[ id=" + id + " ]";
    }
    
     public long getHoursLeft() {
        
         long now = new Date().getTime();
         long end = this.enddate.getTime();
         long dif = TimeUnit.MILLISECONDS.toHours((end - now));
         
         return dif;
    }

    public void setHoursLeft(long HoursLeft) {
        this.HoursLeft = HoursLeft;
    }
}
