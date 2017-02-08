/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Salgado
 */
@Entity
@Table(name = "t_useritem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserItem.findAll", query = "SELECT u FROM UserItem u"),
    @NamedQuery(name = "UserItem.findById", query = "SELECT u FROM UserItem u WHERE u.id = :id"),
    @NamedQuery(name = "UserItem.findByIsselling", query = "SELECT u FROM UserItem u WHERE u.isselling = :isselling"),
    @NamedQuery(name = "UserItem.findByIsbuying", query = "SELECT u FROM UserItem u WHERE u.isbuying = :isbuying"),
    @NamedQuery(name = "UserItem.findByIsfollowing", query = "SELECT u FROM UserItem u WHERE u.isfollowing = :isfollowing"),
    @NamedQuery(name = "UserItem.findByCreationdate", query = "SELECT u FROM UserItem u WHERE u.creationdate = :creationdate"),
    @NamedQuery(name = "UserItem.findFolloingByUserId", query = "SELECT u FROM UserItem u WHERE u.userid = :userid AND u.isfollowing = true"),
    @NamedQuery(name = "UserItem.findSellingByUserId", query = "SELECT u FROM UserItem u WHERE u.userid = :userid AND u.isselling = true"),
})
public class UserItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isselling")
    private boolean isselling;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isbuying")
    private boolean isbuying;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isfollowing")
    private boolean isfollowing;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    @JoinColumn(name = "itemid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Item itemid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userid;

    public UserItem() {
    }

    public UserItem(Long id) {
        this.id = id;
    }

    public UserItem(Long id, boolean isselling, boolean isbuying, boolean isfollowing, Date creationdate) {
        this.id = id;
        this.isselling = isselling;
        this.isbuying = isbuying;
        this.isfollowing = isfollowing;
        this.creationdate = creationdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsselling() {
        return isselling;
    }

    public void setIsselling(boolean isselling) {
        this.isselling = isselling;
    }

    public boolean getIsbuying() {
        return isbuying;
    }

    public void setIsbuying(boolean isbuying) {
        this.isbuying = isbuying;
    }

    public boolean getIsfollowing() {
        return isfollowing;
    }

    public void setIsfollowing(boolean isfollowing) {
        this.isfollowing = isfollowing;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
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
        if (!(object instanceof UserItem)) {
            return false;
        }
        UserItem other = (UserItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserItem[ id=" + id + " ]";
    }
    
}
