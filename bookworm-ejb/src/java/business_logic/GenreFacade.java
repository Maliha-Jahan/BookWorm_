/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import model.Genre;
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
public class GenreFacade extends AbstractFacade<Genre> {
    @PersistenceContext(unitName = "bookworm-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenreFacade() {
        super(Genre.class);
    }
    
    public List<Genre> findById(BigDecimal id){
        List<Genre> result = em.createNamedQuery("Genre.findByGenreId", Genre.class)
            .setParameter("genreId", id)
            .getResultList();
        return result;
    }
    
    public List<Genre> findAllForOne(BigDecimal g_id){
        List<Genre> result = em.createNamedQuery("Genre.findByGenreId", Genre.class)
            .setParameter("genreId", g_id)
            .getResultList();
        return result;
    }
}
