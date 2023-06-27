package com.bouba.mylibrary.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private IBookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBook(){
        return new ResponseEntity<>(bookService.allBooks(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/byCategory/{code}")
    public ResponseEntity<List<Book>> getBookByCategory(@RequestParam("code") String code){
        return new ResponseEntity<>(bookService.getBooksByCategory(code), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/bookByIsbn")
    public ResponseEntity<Book> getAllBook(@RequestParam("isbn") String isbn){
        return new ResponseEntity<>(bookService.findBookByIsbn(isbn), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/delete")
    public void deleteBook(@RequestParam("id") Long id){
        if(bookService.checkIfIdexists(id)){
            bookService.deleteBook(id);
        }
    }

    @PostMapping("/newBook")
    public ResponseEntity<Book> newBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatusCode.valueOf(200));
    }

}
