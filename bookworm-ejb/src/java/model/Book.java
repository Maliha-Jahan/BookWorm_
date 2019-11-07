/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "BOOK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookId = :bookId"),
    @NamedQuery(name = "Book.findByBookName", query = "SELECT b FROM Book b WHERE b.bookName = :bookName"),
    @NamedQuery(name = "Book.findByReleaseDate", query = "SELECT b FROM Book b WHERE b.releaseDate = :releaseDate"),
    @NamedQuery(name = "Book.findByAuthorName", query = "SELECT b FROM Book b WHERE b.authorName = :authorName"),
    @NamedQuery(name = "Book.findByDescription", query = "SELECT b FROM Book b WHERE b.description = :description"),
    @NamedQuery(name = "Book.searchByTitle", query = "SELECT b FROM Book b WHERE upper(b.bookName) like :x"),
    @NamedQuery(name = "Book.searchByAuthor", query = "SELECT b FROM Book b WHERE upper(b.authorName) like :x"),
    @NamedQuery(name = "Book.sortByTitle", query = "SELECT b FROM Book b order by b.bookName"),
    @NamedQuery(name = "Book.sortByAuthor", query = "SELECT b FROM Book b order by b.authorName"),
    @NamedQuery(name = "Book.sortByReleaseDate", query = "SELECT b FROM Book b order by b.releaseDate"),
    @NamedQuery(name = "Book.mostRecent", query = "SELECT b FROM Book b ORDER BY b.releaseDate DESC")
})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOK_ID")
    private BigDecimal bookId;
    @Size(max = 60)
    @Column(name = "BOOK_NAME")
    private String bookName;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    @Size(max = 50)
    @Column(name = "AUTHOR_NAME")
    private String authorName;
    @Size(max = 500)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinTable(name = "BOOK_GENRE", joinColumns = {
        @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GENRE_ID", referencedColumnName = "GENRE_ID")})
    @ManyToMany
    private List<Genre> genreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Bookshelf> bookshelfList;

    public Book() {
    }

    public Book(BigDecimal bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getBookId() {
        return bookId;
    }

    public void setBookId(BigDecimal bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @XmlTransient
    public List<Bookshelf> getBookshelfList() {
        return bookshelfList;
    }

    public void setBookshelfList(List<Bookshelf> bookshelfList) {
        this.bookshelfList = bookshelfList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Book[ bookId=" + bookId + " ]";
    }
    
}
