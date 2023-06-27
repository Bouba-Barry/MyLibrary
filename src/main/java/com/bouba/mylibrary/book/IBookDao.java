package com.bouba.mylibrary.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookDao extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    public Book findBookByIsbn(@Param("isbn") String isbn);


    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    public List<Book> findByTitleLikeIgnoreCase(@Param("title") String title);


    @Query("SELECT b "
            + "FROM Book b "
            + "INNER JOIN b.category cat "
            + "WHERE cat.code = :code"
    )
    public List<Book> findByCategory(@Param("code") String codeCategory);
}
