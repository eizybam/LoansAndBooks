package com.financiera.loansandbooks.controller;

import com.financiera.loansandbooks.controller.Dtos.BookDTO;
import com.financiera.loansandbooks.model.book.Book;
import com.financiera.loansandbooks.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin({"*"})
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody @Valid BookDTO body){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(body));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }
}
