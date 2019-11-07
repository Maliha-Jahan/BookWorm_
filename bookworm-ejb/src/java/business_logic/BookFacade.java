/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import model.Book;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {
    @PersistenceContext(unitName = "bookworm-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public List<Book> searchByTitle(String BookName){
        String param = "%"+BookName.toUpperCase()+"%";
        List<Book> result = em.createNamedQuery("Book.searchByTitle", Book.class)
            .setParameter("x", param)
            .getResultList();
        return result;
    }
    
    public List<Book> searchByAuthor(String AuthorName){
        String param = "%"+AuthorName.toUpperCase()+"%";
        List<Book> result = em.createNamedQuery("Book.searchByAuthor", Book.class)
            .setParameter("x", param)
            .getResultList();
        return result;
    }
    
    public List<Book> findAllSort(String Choice){
        int choice = Integer.parseInt(Choice);
        switch(choice){
            case 1: 
                return em.createNamedQuery("Book.sortByTitle", Book.class).getResultList();
            case 2:
                return em.createNamedQuery("Book.sortByAuthor", Book.class).getResultList();
            case 3:
                return em.createNamedQuery("Book.sortByReleaseDate", Book.class).getResultList();
            case 4:
                super.findAll();
            default: break;
        }
        return super.findAll();
    }
    
    public int maxID() {
        int id = 0;
        BigDecimal Bid = (BigDecimal) em.createQuery("SELECT max(b.bookId) FROM Book b").getSingleResult();
        if(Bid == null) return id;
        id = Bid.intValue();
        return id;
    }
    
    public List<Book> mostLiked(){
        List<Book> books = (List<Book>) em.createQuery("SELECT b.book FROM Bookshelf b GROUP BY b.book ORDER BY AVG(b.bookRate) DESC").setMaxResults(10).getResultList();
        return books;
    }
    
    public Double avgRate(BigDecimal b_id){
        Double avg = (Double) em.createQuery("SELECT AVG(b.bookRate) FROM Bookshelf b WHERE b.bookshelfPK.bookId = :id")
                .setParameter("id", b_id)
                .getSingleResult();
        return avg;
    }
    
    public List<Book> mostRecent(){
        return em.createNamedQuery("Book.mostRecent", Book.class).setMaxResults(10).getResultList();
    }
}
