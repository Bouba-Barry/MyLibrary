package com.bouba.mylibrary.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements  IBookService{

    @Autowired
    private IBookDao bookDao;
    @Override
    public Book saveBook(Book book) {

        return bookDao.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
       bookDao.deleteById(bookId);
    }

    @Override
    public List<Book> findBooksByTitleOrPartTitle(String title) {
        return bookDao.findByTitleLikeIgnoreCase(title);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookDao.findBookByIsbn(isbn);
    }

    @Override
    public boolean checkIfIdexists(Long id) {
        return bookDao.existsById(id);
    }

    @Override
    public List<Book> getBooksByCategory(String codeCategory) {
        return bookDao.findByCategory(codeCategory);
    }

    @Override
    public List<Book> allBooks() {
        return bookDao.findAll();
    }
}
