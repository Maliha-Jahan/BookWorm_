/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Bookshelf;
import model.BookshelfPK;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import business_logic.BookshelfFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class BookShelfController {
    @EJB
    private BookshelfFacade bookshelfFacade;
    public Bookshelf bookshelf = new Bookshelf();
    public BookshelfPK bookshelfPK = new BookshelfPK();
    private String mark = "All";
    //private String bookRate = "";
    
    public BookShelfController() {
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public BookshelfPK getBookshelfPK() {
        return bookshelfPK;
    }

    public void setBookshelfPK(BookshelfPK bookshelfPK) {
        this.bookshelfPK = bookshelfPK;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String setID(BigDecimal book_id){
        //this.bookshelfPK.setBookId(book_id);
        BigDecimal user_id;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            if(session.getAttribute("userid") != null){ 
                user_id = (BigDecimal)session.getAttribute("userid");
                this.bookshelfPK.setUserId(user_id);
                if(book_id != null) this.bookshelfPK.setBookId(book_id);
                if(this.bookshelfPK.getBookId() != null){
                    this.bookshelf.setBookshelfPK(this.bookshelfPK);
                    List<Bookshelf> result = this.bookshelfFacade.findAllForTwo(book_id, user_id);
                    if(!result.isEmpty()){
                        this.bookshelf = result.get(0);
                    }
                }
            }
        }
        return "ok";
    }
    
    public String add(BigDecimal book_id){
        BigDecimal user_id;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            if(session.getAttribute("userid") != null){ 
                user_id = (BigDecimal)session.getAttribute("userid");
                this.bookshelfPK.setUserId(user_id);
                if(book_id != null) this.bookshelfPK.setBookId(book_id);
                if(this.bookshelfPK.getBookId() != null){
                    BigDecimal b_id = this.bookshelfPK.getBookId();
                    this.bookshelf.setBookshelfPK(this.bookshelfPK);
                    //this.bookshelf.setBookRate(new BigInteger(bookRate));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+b_id+"  ,  "+user_id);
                    List<Bookshelf> result = this.bookshelfFacade.findAllForTwo(b_id, user_id);
                    if(!result.isEmpty()){System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result.get(0).getBookMark()); this.bookshelfFacade.edit(this.bookshelf);}
                    else {System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>empty");this.bookshelfFacade.create(bookshelf);}
                }
            }
        }
        return "book";
    }
    
    public String remove(Bookshelf bs){
        this.bookshelfFacade.remove(bs);
        return "book";
    }
    
    public void edit(){
        this.bookshelfFacade.edit(this.bookshelf);
    }
    
    public List<Bookshelf> findAllForOne(){
        BigDecimal bid = this.bookshelfPK.getBookId();
        if(bid != null) return this.bookshelfFacade.findAllForOne(bid);
        else return null;
    }
    
    public List<Bookshelf> findAllForUser(){
        BigDecimal uid = null;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            if(session.getAttribute("userid") != null){ 
                uid = (BigDecimal)session.getAttribute("userid");
            }
        }
        if(uid != null) {
            if(this.mark == null || this.mark.equals("") || this.mark.equals("All")) return this.bookshelfFacade.findAllForUser(uid);
            else{
                return this.bookshelfFacade.findByMark(mark, uid);
            }
        }
        else return null;
    }
    
    public String showMark(){
        return "bookshelf";
    }
}
