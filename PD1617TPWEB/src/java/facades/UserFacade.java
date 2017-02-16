/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.User;
import entities.Item;
import entities.Auction;
import javax.ejb.EJB;
import controllers.IDAO;
import entities.Message;
import entities.Notification;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import utils.ResultMessage;

@Stateful
public class UserFacade implements IUser {

    @EJB
    private IDAO dAO;

    @EJB
    private ISystem AServer;

    private User user;

    @Override
    public List<User> getAll() {

        try {
            return dAO.getEntityManager().createNamedQuery("User.findAll").getResultList();
        } catch (Exception e) {
            return new ArrayList<User>();
        }
    }

    @Override
    public ResultMessage RegisterUser(String username, String password, String address) {

        //validate input
        if (username == null || password == null || address == null) {
            return ResultMessage.RegisterInvalid;
        }

        long tryUser = 0;

        try {
            tryUser = (long) dAO.getEntityManager().createNamedQuery("User.GetIdByUsername").setParameter("username", username).getFirstResult();
        } catch (Exception e) {

        }

        if (tryUser > 0) {
            return ResultMessage.RegisterAllreadyExist;
        }

        this.user = new User();
        this.user.setId((long) -1);
        this.user.setUsername(username);
        this.user.setPassword(password);
        this.user.setAddress(address);
        this.user.setBalance(0);
        this.user.setAccountActivation(false);
        this.user.setAccountSuspension(false);
        this.user.setCreationdate(new Date());

        dAO.getEntityManager().persist(this.user);

        return ResultMessage.RegisterSucess;
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean LogOff() {
        return AServer.LogOffUserFromActiveList(this.user.getUsername());
    }

    @Override
    public User SeeProfile() {
        return this.user;
    }

    @Override
    public ResultMessage UpdateProfile(String Address, String Password, String CurrentPassword) {

        if (CurrentPassword.equals(user.getPassword())) {

            boolean toUpdate = false;

            if (!Address.contentEquals("")) {
                user.setAddress(Address);
                toUpdate = true;
            }

            if (!Password.equals("")) {
                user.setPassword(Password);
                toUpdate = true;
            }

            try {
                if (toUpdate) {
                    dAO.getEntityManager().merge(user);
                }
            } catch (Exception e) {
                return ResultMessage.UpdateProfileInvalid;
            }

            return ResultMessage.UpdateProfileValid;
        } else {
            return ResultMessage.UpdateProfileWrongPass;
        }
    }

    @Override
    public double CheckBalance() {
        return user.getBalance();
    }

    @Override
    public ResultMessage LoadBalance(double increment) {

        if (increment > 0) {
            user.setBalance(user.getBalance() + increment);

            try {
                dAO.getEntityManager().merge(user);
            } catch (Exception e) {
            }

            return ResultMessage.LoadBalanceValid;
        } else {
            return ResultMessage.LoadBalanceInvalid;
        }
    }

    @Override
    public boolean SuspendAccount() {

        this.user.setAccountSuspension(true);

        try {
            dAO.getEntityManager().persist(this.user);
        } catch (Exception e) {
        }

        return true;
    }

    @Override
    public ResultMessage Login(String Username, String Password) {

        //validate input
        if (Username == null || Password == null) {
            return ResultMessage.LoginInvalid;
        }

        //check if user is already logged
        boolean isLogged = AServer.CheckUserState(Username);

        if (!isLogged) {
            try {
                this.user = (User) dAO.getEntityManager().createNamedQuery("User.Login").setParameter("username", Username).setParameter("password", Password).getSingleResult();
            } catch (Exception e) {
                return ResultMessage.LoginUserNotFound;
            }

            if (!this.user.getAccountActivation()) {
                return ResultMessage.AccountNotActivated;
            }

            if (this.user.getAccountSuspension()) {
                return ResultMessage.AccountSuspended;
            }

            AServer.AddUserToActiveList(this.user);
            return ResultMessage.LoginSucess;

        } else {
            return ResultMessage.LoginAllreadyLogged;
        }

    }

    @Override
    public ResultMessage ReportItem(long UserId, long ItemId) {

        User user = null;

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", UserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ReportInsuccess;
        }

        Notification notification = new Notification();
        notification.setId((long) -1);
        notification.setMessage("User [" + user.getUsername() + "] with Id:" + UserId + " have reported the Item with Id: " + ItemId);
        notification.setStatus(0);
        notification.setUserid(user);
        notification.setCreationdate(new Date());

        try {
            dAO.getEntityManager().persist(notification);
        } catch (Exception e) {
            return ResultMessage.ReportInsuccess;
        }

        return ResultMessage.ReportSuccess;

    }

    @Override
    public ResultMessage ReportUser(long FromUserId, long ToUserId) {

        User FromUser = null;
        try {
            FromUser = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", FromUserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ReportInsuccess;
        }

        User ToUser = null;
        try {
            ToUser = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", ToUserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.ReportInsuccess;
        }

        Notification notification = new Notification();
        notification.setId((long) -1);
        notification.setMessage("User [" + FromUser.getUsername() + "] with Id: " + FromUserId + " have reported the User " + ToUser.getUsername() + " with Id: " + ToUserId);
        notification.setStatus(0);
        notification.setUserid(FromUser);
        notification.setCreationdate(new Date());

        try {
            dAO.getEntityManager().persist(notification);
        } catch (Exception e) {
            return ResultMessage.ReportInsuccess;
        }

        return ResultMessage.ReportSuccess;

    }

    @Override
    public List<Item> ItensBuyHistoric(long userid) {

        List<Auction> auctionHistoric = new ArrayList();
        List<Item> historic = new ArrayList();
        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", userid).getSingleResult();
        } catch (Exception ex) {
            return historic;
        }

        try {
            auctionHistoric = dAO.getEntityManager().createNamedQuery("Auction.findByAuctionstateAndLastUserId").setParameter("auctionstate", 0).setParameter("lastuserid", user).getResultList();
        } catch (Exception e) {
            return historic;
        }

        for (Auction i : auctionHistoric) {
            if (i.getLastuserid() != i.getSellerid()) {
                historic.add(i.getItemid());
            }
        }

        return historic;
    }

    @Override
    public List<Item> ItensSellHistoric(long userid) {

        List<Auction> auctionHistoric = new ArrayList();
        List<Item> historic = new ArrayList();
        User user = new User();

        try {
            user = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", userid).getSingleResult();
        } catch (Exception ex) {
            return historic;
        }

        try {
            auctionHistoric = dAO.getEntityManager().createNamedQuery("Auction.findByAuctionstateAndSellerId").setParameter("auctionstate", 0).setParameter("sellerid", user).getResultList();
        } catch (Exception e) {
            return historic;
        }

        for (Auction i : auctionHistoric) {
            if (i.getLastuserid() != i.getSellerid()) {
                historic.add(i.getItemid());
            }
        }

        return historic;
    }
    
    @Override
    public ResultMessage AskAccountSuspencion(long FromUserId) {

        User FromUser = null;
        try {
            FromUser = (User) dAO.getEntityManager().createNamedQuery("User.findById").setParameter("id", FromUserId).getSingleResult();
        } catch (Exception e) {
            return ResultMessage.AskAccountSuspencionInsucess;
        }
        
        

        Notification notification = new Notification();
        notification.setId((long) -1);
        notification.setMessage("User [" + FromUser.getUsername() + "] with Id:" + FromUserId + " have requested the suspencion of his account");
        notification.setStatus(0);
        notification.setUserid(FromUser);
        notification.setCreationdate(new Date());

        try {
            dAO.getEntityManager().persist(notification);
        } catch (Exception e) {
            return ResultMessage.AskAccountSuspencionInsucess;
        }

        return ResultMessage.AskAccountSuspencionSucess;

    }
    
}
