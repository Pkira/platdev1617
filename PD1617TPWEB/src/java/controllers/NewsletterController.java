/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Newsletter;
import facades.INewsLetter;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


@Named(value = "newsletterController")
@RequestScoped
public class NewsletterController {

    @EJB
    private INewsLetter newsletterFacade;
    
    public NewsletterController() {
    }
    
    public List<Newsletter> getNewsletter()
    {
        List<Newsletter> news = newsletterFacade.GetNewsLetter();
        
        return news;
    }
    
}
