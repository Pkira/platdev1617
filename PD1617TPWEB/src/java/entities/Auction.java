/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedro Salgado
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
    @NamedQuery(name = "Auction.findByEnddate", query = "SELECT a FROM Auction a WHERE a.enddate = :enddate")})
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
    @JoinColumn(name = "itemid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Item itemid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auctionid")
    private Collection<AuctionLog> auctionLogCollection;

    public Auction() {
    }

    public Auction(Long id) {
        this.id = id;
    }

    public Auction(Long id, int auctionstate, int itemstate, Date startdate, Date enddate) {
        this.id = id;
        this.auctionstate = auctionstate;
        this.itemstate = itemstate;
        this.startdate = startdate;
        this.enddate = enddate;
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

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
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
    
}
