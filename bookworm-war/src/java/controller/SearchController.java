/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Book;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import business_logic.BookFacade;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class SearchController {
    @EJB
    private BookFacade bookFacade;
    public Book book = new Book();

    public SearchController() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
}
