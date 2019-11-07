/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Login;
import model.Profile;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import business_logic.ProfileFacade;
import java.util.*;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import business_logic.LoginFacade;


/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class SignupController {
    @EJB
    private LoginFacade loginFacade;
    @EJB
    private ProfileFacade profileFacade;
    
    public Profile profile = new Profile();
    public Login login = new Login();
    
    public SignupController() {
        
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    public List<Profile> findAll(){
        return this.profileFacade.findAll();
    }
    
    public String add(){
        if(!this.loginFacade.findByName(this.login.getUserName()).isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                "User name already exists. Give a unique name.", "Give a unique name."));
            this.login = new Login();
            return "signup";
        }
        if(this.login.getPassword().length() < 8 || this.login.getPassword().length() > 20){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
            "Passowrd must be between 8-20 characters", "Please enter valid Password"));
            this.login = new Login();
            return "signup";
        }
        this.login.setUserId(new BigDecimal(this.loginFacade.maxID() + 1));
        this.loginFacade.create(this.login);
        this.profile.setUserId(this.login.getUserId());
        this.profileFacade.create(this.profile);
        //this.login = new Login();
        //this.profile = new Profile();
        return "login";
    }
    
    public String edit(Profile profile){
        this.profile = profile;
        return "profile";
    }
    
    public String retrieve(){
        BigDecimal user_id;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sessProf "+session.getAttribute("userid"));
            if(session.getAttribute("userid") != null){ 
                user_id = (BigDecimal)session.getAttribute("userid");
                this.profile = this.profileFacade.find(user_id);
            }
        }
        return "OK";
    }
    
    public String edit(){
        this.profileFacade.edit(this.profile);
        return "profile";
    }
}
