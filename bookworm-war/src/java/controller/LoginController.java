/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Login;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import business_logic.LoginFacade;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class LoginController {
    @EJB
    private LoginFacade loginFacade;
    public Login login = new Login();
    private String OldP, NewP;
    boolean logout = false;
    
    public LoginController() {
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getOldP() {
        return OldP;
    }

    public void setOldP(String OldP) {
        this.OldP = OldP;
    }

    public String getNewP() {
        return NewP;
    }

    public void setNewP(String NewP) {
        this.NewP = NewP;
    }
    
    public List<Login> findAll(){
        return this.loginFacade.findAll();
    }
    
    public String remove(Login login){
        this.loginFacade.remove(login);
        this.login = new Login();
        return "all_users";
    }
    
    public String validateLogin(){
        String Uname = this.login.getUserName();
        BigDecimal Uid = this.loginFacade.validateLogin(Uname, this.login.getPassword());
        this.login.setUserId(Uid);
        //boolean valid = this.loginFacade.validateLogin(Uname, this.login.getPassword());
        if(Uid != null){
            HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest());
            HttpServletResponse response = (HttpServletResponse)(FacesContext.getCurrentInstance().getExternalContext().getResponse());
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(60*60);
            session.setAttribute("userid", Uid);
            Cookie c1 = new Cookie("userid", ""+Uid.intValue());
            c1.setMaxAge(60*60*24*30);
            response.addCookie(c1);
            logout = false;
            return "index";
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Incorrect Username and Passowrd", "Please enter correct username and Password"));
            return "login";
        }
    }
    
    public boolean checkLogin(){
        if(logout) return false;
        try{
            boolean flag1 = false, flag2 = false;
            HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest());
            HttpSession session = request.getSession(true);
            if(session != null){
                //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sess "+session.getAttribute("userid"));
                if(session.getAttribute("userid") != null) flag1 = true;
            }
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                System.out.println(">>>>>>>>>>>>>>>>>>not null c");
                for(Cookie cookie : cookies){
                    System.out.println(cookie.getName());
                    if(cookie.getName().equals("userid") && !(cookie.getValue().equals("invalid"))){
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>cook "+cookie.getValue());
                        flag2 = true;
                    }
                }
            }
            if(flag1 || flag2) return true;
        /*Map<String,Object> Cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        if (Cookies!=null){
            System.out.println(">>>>>>>>>>>>>>>>>>not null c2");
            Cookie c = (Cookie)Cookies.get("userid");
            if(c != null) System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>cook2"+c.getValue());
        }*/
            return false;
        }catch(Exception ex){
            System.out.println(">>>>>>>>>>>>>>>>>>>>error");
            ex.printStackTrace();
            return false;   
        }
    }
    
    public boolean checkAdmin(){
        if(logout) return false;
        BigDecimal uid = null;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            if(session.getAttribute("userid") != null){ 
                uid = (BigDecimal)session.getAttribute("userid");
            }
        }
        if(uid != null && uid.equals(new BigDecimal("1"))) return true;
        else return false;
    }
    
    public String logOut(){
        logout = true;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpServletResponse response = (HttpServletResponse)(FacesContext.getCurrentInstance().getExternalContext().getResponse());
        HttpSession session = request.getSession(true);
        if(session != null){
            session.removeAttribute("userid");
        }
        Cookie c1 = new Cookie("userid", "invalid");
        c1.setMaxAge(60*60*24*30);
        response.addCookie(c1);
        return "login";
    }
    
    public String retrieve(){
        BigDecimal user_id;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            if(session.getAttribute("userid") != null){ 
                user_id = (BigDecimal)session.getAttribute("userid");
                this.login = this.loginFacade.find(user_id);
            }
        }
        return "OK";
    }
    
    public String edit(){
        if(OldP != null && !OldP.equals("")){ //&& (NewP != null || !NewP.equals(""))){
            if(!this.login.getPassword().equals(OldP)){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Incorrect Passowrd", "Please enter correct Password"));
                return "accountsetting";
            }
            String uname = this.login.getUserName();
            if(uname != null && !uname.equals("")){
                List<Login> usersWithName = this. loginFacade.findByName(uname);
                if(!usersWithName.isEmpty()){
                    if(usersWithName.get(0).getUserId().intValue() != this.login.getUserId().intValue()){
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Username already taken by different user", "Choose different username"));
                        return "accountsetting";
                    }
                }
            
                if(NewP != null && !NewP.equals("")){
                    if(NewP.length() < 8 || NewP.length() > 20){
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Passowrd must be between 8-20 characters", "Please enter valid Password"));
                        return "accountsetting";
                    }
                    this.login.setPassword(NewP);
                }
                this.loginFacade.edit(this.login);
        }}
        return "accountsetting";
    }
}
