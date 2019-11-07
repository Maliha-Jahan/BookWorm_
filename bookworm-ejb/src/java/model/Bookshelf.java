/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "BOOKSHELF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bookshelf.findAll", query = "SELECT b FROM Bookshelf b"),
    @NamedQuery(name = "Bookshelf.findByUserId", query = "SELECT b FROM Bookshelf b WHERE b.bookshelfPK.userId = :userId"),
    @NamedQuery(name = "Bookshelf.findByBookId", query = "SELECT b FROM Bookshelf b WHERE b.bookshelfPK.bookId = :bookId"),
    @NamedQuery(name = "Bookshelf.findByBookMark", query = "SELECT b FROM Bookshelf b WHERE b.bookMark = :bookMark"),
    @NamedQuery(name = "Bookshelf.findByBookRate", query = "SELECT b FROM Bookshelf b WHERE b.bookRate = :bookRate"),
    @NamedQuery(name = "Bookshelf.findByBookReview", query = "SELECT b FROM Bookshelf b WHERE b.bookReview = :bookReview"),
    @NamedQuery(name = "Bookshelf.findByBoth", query = "SELECT b FROM Bookshelf b WHERE b.bookshelfPK.userId = :userId and b.bookshelfPK.bookId = :bookId"),
    @NamedQuery(name = "Bookshelf.findByMark", query = "SELECT b FROM Bookshelf b WHERE b.bookshelfPK.userId = :userId and b.bookMark = :bookMark")
})
public class Bookshelf implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookshelfPK bookshelfPK;
    @Size(max = 20)
    @Column(name = "BOOK_MARK")
    private String bookMark;
    @Column(name = "BOOK_RATE")
    private BigInteger bookRate;
    @Size(max = 500)
    @Column(name = "BOOK_REVIEW")
    private String bookReview;
    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID", insertable = false, updatable = false)
    //@JoinTable(name="user_books", joinColumns={@JoinColumn(name="BOOK_ID")}, inverseJoinColumns={@JoinColumn(name="USER_ID")})
    private Book book;
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    //@JoinTable(name="user_books", joinColumns={@JoinColumn(name="USER_ID")}, inverseJoinColumns={@JoinColumn(name="BOOK_ID")})
    private Login login;

    public Bookshelf() {
    }

    public Bookshelf(BookshelfPK bookshelfPK) {
        this.bookshelfPK = bookshelfPK;
    }

    public Bookshelf(BigDecimal userId, BigDecimal bookId) {
        this.bookshelfPK = new BookshelfPK(userId, bookId);
    }

    public BookshelfPK getBookshelfPK() {
        return bookshelfPK;
    }

    public void setBookshelfPK(BookshelfPK bookshelfPK) {
        this.bookshelfPK = bookshelfPK;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }

    public BigInteger getBookRate() {
        return bookRate;
    }

    public void setBookRate(BigInteger bookRate) {
        this.bookRate = bookRate;
    }

    public String getBookReview() {
        return bookReview;
    }

    public void setBookReview(String bookReview) {
        this.bookReview = bookReview;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookshelfPK != null ? bookshelfPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookshelf)) {
            return false;
        }
        Bookshelf other = (Bookshelf) object;
        if ((this.bookshelfPK == null && other.bookshelfPK != null) || (this.bookshelfPK != null && !this.bookshelfPK.equals(other.bookshelfPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bookshelf[ bookshelfPK=" + bookshelfPK + " ]";
    }
    
}
