/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import model.Login;
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
public class LoginFacade extends AbstractFacade<Login> {
    @PersistenceContext(unitName = "bookworm-ejbPU")
    private EntityManager em;
    DataBaseConnection db = new DataBaseConnection();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }
    
    /*public int maxID() {
        int id = 0;
        try {
            db.rs = db.stmt.executeQuery("select max(user_id) from login");
            if(db.rs.next()) id = db.rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }*/
    
    public int maxID() {
        int id = 0;
        BigDecimal Bid = (BigDecimal) em.createQuery("SELECT max(l.userId) FROM Login l").getSingleResult();
        if(Bid == null) return id;
        id = Bid.intValue();
        return id;
    }
    
    public BigDecimal validateLogin(String Username, String Password){
        BigDecimal uid = null;
        List<Login> result = em.createNamedQuery("Login.validate", Login.class)
            .setParameter("userName", Username)
            .setParameter("password", Password)
            .getResultList();
        //return !result.isEmpty();
        if(!result.isEmpty()) uid = result.get(0).getUserId();
        return uid;
    }
    
    public List<Login> findByName(String uname){
        List<Login> result = em.createNamedQuery("Login.findByUserName", Login.class)
            .setParameter("userName", uname)
            .getResultList();
        return result;
    }
}
