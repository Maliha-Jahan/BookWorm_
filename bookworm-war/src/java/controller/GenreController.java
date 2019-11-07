/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Genre;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import business_logic.GenreFacade;
import java.util.List;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class GenreController {
    @EJB
    private GenreFacade genreFacade;
    private Genre genre = new Genre();
    private boolean flag = false;
    
    public GenreController() {
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public List<Genre> findAll(){
        if(flag == false) return this.genreFacade.findAll();
        else{
            if(this.genre.getGenreId() != null) return this.genreFacade.findById(this.genre.getGenreId());
        }
        return this.genreFacade.findAll();
    }
    
    public List<Genre> findAllGenre(){
        return this.genreFacade.findAll();
    }
    
    public String showGenre(){
        return "genre";
    }
}
