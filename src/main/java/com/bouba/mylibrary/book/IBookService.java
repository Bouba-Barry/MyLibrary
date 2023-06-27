package com.bouba.mylibrary.book;

import java.util.List;

public interface IBookService {

    public Book saveBook(Book book);

    public Book updateBook(Book book);

    public void deleteBook(Long bookId);

    public List<Book> findBooksByTitleOrPartTitle(String title);

    public Book findBookByIsbn(String isbn);

    public boolean checkIfIdexists(Long id);

    public List<Book> getBooksByCategory(String codeCategory);
    public List<Book> allBooks();
}
