/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Genre;
import model.Book;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import business_logic.BookFacade;
import business_logic.GenreFacade;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class BookController {
    @EJB
    private GenreFacade genreFacade;
    @EJB
    private BookFacade bookFacade;   
    private Book b= new Book(); 
    private Book book= new Book(); 
    public String choice;
    public String choice_Search;
    public String param;
    private String[] genre;
    private boolean edit = false;
    
    public BookController() {
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getChoice_Search() {
        return choice_Search;
    }

    public void setChoice_Search(String choice_Search) {
        this.choice_Search = choice_Search;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Book getB() {
        return b;
    }

    public void setB(Book b) {
        this.b = b;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    
    public Double getAvgRate(BigDecimal book_id) {
        if(book_id != null && !book_id.equals("")){
            return this.bookFacade.avgRate(book_id);
        }
        else{return this.bookFacade.avgRate(this.book.getBookId());}
    }
    
    public List<Book> findAll(){
        this.param = null;
        if(!edit){this.b = new Book(); this.genre = null;}
        if(this.choice == null || this.choice.equals("")) return this.bookFacade.findAll();
        else return this.bookFacade.findAllSort(this.choice);
    }
    
    public String initiateEdit(Book book){
        this.b.setAuthorName(book.getAuthorName());
        this.b.setBookId(book.getBookId());
        this.b.setBookName(book.getBookName());
        this.b.setReleaseDate(book.getReleaseDate());
        this.b.setDescription(book.getDescription());
        List<Genre> genreList = book.getGenreList();
        String g[] = new String[genreList.size()];
        for(int i=0; i<g.length; i++){
            g[i] = ""+genreList.get(i).getGenreId();
        }
        this.genre = g;
        this.edit = true;
        return "all_books";
    }
    
    public String add(){
        List<Genre> genreList = new ArrayList<>();
        for(String g_id: genre){
            List<Genre> g = this.genreFacade.findById(new BigDecimal(g_id));
            if(!g.isEmpty()) genreList.add(g.get(0));
        }
        this.b.setGenreList(genreList);
        if(this.b.getBookId() == null){
            this.b.setBookId(new BigDecimal(this.bookFacade.maxID() + 1));
            this.bookFacade.create(this.b);
        }
        else{
            this.bookFacade.edit(this.b);
        }
        this.b = new Book();
        this.edit = false;
        this.genre = null;
        return "all_books";
    }
    
    /*public String edit(){
        List<Genre> genreList = new ArrayList<>();
        for(String g_id: genre){
            List<Genre> g = this.genreFacade.findById(new BigDecimal(g_id));
            if(!g.isEmpty()) genreList.add(g.get(0));
        }
        this.b.setGenreList(genreList);
        this.bookFacade.edit(this.b);
        return "profile";
    }*/
    
    public String remove(Book book){
        this.bookFacade.remove(book);
        this.b = new Book();
        this.genre = null;
        return "all_books";
    }
    
    public List<Book> search(){
        if(this.choice_Search == null || this.choice_Search.equals("")) return this.bookFacade.searchByTitle(this.param);
        else{
            if(this.choice_Search.equals("2")) return this.bookFacade.searchByAuthor(this.param);
            else return this.bookFacade.searchByTitle(this.param);
        }
    }
    
    public String goToResult(){
        if(this.param == null || "".equals(this.param)) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Search field can't be empty", "Please enter proper input"));
            return "index";
        }
        return "search_results";
    }
    
    public String goToBooks(){
        return "all_books";
    }
    
    public String getGenre(List<Genre> genreList){
        if(genreList.isEmpty()){
            return "";
        }
        String all_genre = "";
        for (Genre genre:genreList){
            all_genre = all_genre + ", " + genre.getGenreName();
        }
        return all_genre.substring(2);
    }
    
    public String find(BigDecimal book_id){
        if(book_id != null) this.book = this.bookFacade.find(book_id);
        return "OK";
    }
    
    public List<Book> mostLiked(){
        return this.bookFacade.mostLiked();
    }
    
    public List<Book> mostRecent(){
        return this.bookFacade.mostRecent();
    }
}
