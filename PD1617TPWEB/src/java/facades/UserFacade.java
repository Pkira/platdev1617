/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.User;
import javax.ejb.EJB;
import controllers.IDAO;
import entities.Message;
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
        return dAO.getEntityManager().createNamedQuery("User.findAll").getResultList();
    }

    @Override
    public ResultMessage RegisterUser(String username, String password, String address){
    
        //validate input
        if(username == null || password == null || address == null)
            return ResultMessage.RegisterInvalid;
        
        long tryUser = 0;
        
        tryUser = (long)dAO.getEntityManager().createNamedQuery("User.GetIdByUsername").setParameter("username", username).getFirstResult();
        
        if(tryUser > 0)
            return ResultMessage.RegisterAllreadyExist;
        
        this.user = new User();
        this.user.setId((long)-1);
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
    public String getUsername(){
        return this.user.getUsername();
    }    
    
    @Override
    public boolean LogOff() {
        return AServer.LogOffUserFromActiveList(this.user.getUsername());
    }
    
    @Override
    public User SeeProfile(){
        return this.user;
    }
    
    @Override
    public ResultMessage UpdateProfile(String Address, String Password, String CurrentPassword){
        
        User user = (User)dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", Address).getSingleResult();            
        
        if(user == null)
            return ResultMessage.LoginUserNotFound;
        
        if(CurrentPassword.equals(user.getPassword())){
            
            boolean toUpdate = false;
            
            if(!Address.contentEquals("")){
                user.setAddress(Address);
                toUpdate = true;
            }
          
            if(!Password.equals("")){
                user.setPassword(Password);  
                toUpdate = true;
            }
            
            if(toUpdate)
                dAO.getEntityManager().persist(user);
            
            return ResultMessage.UpdateProfileInvalid; 
        }
        else
            return ResultMessage.UpdateProfileWrongPass;
    }
    
    @Override
    public double CheckBalance(){
        return user.getBalance();
    }
    
    public ResultMessage LoadBalance(double increment){
        
        if(increment > 0){
            user.setBalance(user.getBalance() + increment);
            
            dAO.getEntityManager().persist(user);
            
            return ResultMessage.LoadBalanceValid;
        }
        else
            return ResultMessage.LoadBalanceInvalid;  
    }
    
    @Override
    public List<Message> CheckMessages(){
        
        List<Message> messages = (List<Message>)user.getMessageCollection();
        return messages;
    }

    @Override
    public ResultMessage SendMessage(String Addressed, String Subject, String Message){
        
        User toUser = (User)dAO.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", Addressed).getSingleResult();
        
        if(toUser == null)
            return ResultMessage.SendMessageNoUser;
        
        Message newMessage = new Message();
        newMessage.setId((long) -1);
        newMessage.setFromid(this.user);
        newMessage.setToid(toUser);
        newMessage.setMessage(Message);
        newMessage.setSubject(Subject);
        
        dAO.getEntityManager().persist(newMessage);
        
        return ResultMessage.SendMessageSuccess;
    }

    @Override
    public boolean SuspendAccount() {
        
        this.user.setAccountSuspension(true);
        dAO.getEntityManager().persist(this.user);
        return true;
    }
    
    @Override
    public ResultMessage Login(String Username, String Password) {
                
        //validate input
        if(Username == null || Password == null)
            return ResultMessage.LoginInvalid;
        
        //check if user is already logged
        boolean isLogged = AServer.CheckUserState(Username);       
        
        if(!isLogged) 
        {
            this.user = (User)dAO.getEntityManager().createNamedQuery("User.Login").setParameter("username", Username).setParameter("password", Password).getSingleResult();
            
            if(this.user != null)
            {
                AServer.AddUserToActiveList(this.user);
                return ResultMessage.LoginSucess;
            }           
            else
                return ResultMessage.LoginUserNotFound;
        }
        else
           return ResultMessage.LoginAllreadyLogged;

    }
           
   
}
