/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import model.Book;
import model.Bookshelf;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class BookshelfFacade extends AbstractFacade<Bookshelf> {
    @PersistenceContext(unitName = "bookworm-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookshelfFacade() {
        super(Bookshelf.class);
    }
    
    public List<Bookshelf> findAllForOne(BigDecimal book_id){
        List<Bookshelf> result = em.createNamedQuery("Bookshelf.findByBookId", Bookshelf.class)
            .setParameter("bookId", book_id)
            .getResultList();
        return result;
    }
    
    public List<Bookshelf> findByMark(String mark, BigDecimal user_id){
        List<Bookshelf> result = em.createNamedQuery("Bookshelf.findByMark", Bookshelf.class)
            .setParameter("bookMark", mark)
            .setParameter("userId", user_id)
            .getResultList();
        return result;
    }
    
    public List<Bookshelf> findByID(String mark, BigDecimal user_id){
        List<Bookshelf> result = em.createNamedQuery("Bookshelf.findByMark", Bookshelf.class)
            .setParameter("bookMark", mark)
            .setParameter("userId", user_id)
            .getResultList();
        return result;
    }
    
    public List<Bookshelf> findAllForUser(BigDecimal user_id){
        List<Bookshelf> result = em.createNamedQuery("Bookshelf.findByUserId", Bookshelf.class)
            .setParameter("userId", user_id)
            .getResultList();
        return result;
    }
    
    public List<Bookshelf> findAllForTwo(BigDecimal book_id, BigDecimal user_id){
        List<Bookshelf> result = em.createNamedQuery("Bookshelf.findByBoth", Bookshelf.class)
            .setParameter("bookId", book_id)
            .setParameter("userId", user_id)
            .getResultList();
        //if(result.isEmpty()) return false;
        return result;
    }
}
