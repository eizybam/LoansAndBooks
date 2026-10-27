package com.financiera.loansandbooks.service;

import com.financiera.loansandbooks.controller.Dtos.BookDTO;
import com.financiera.loansandbooks.exception.ResourceNotFoundException;
import com.financiera.loansandbooks.model.book.Book;
import com.financiera.loansandbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setStock(bookDTO.getStock());

        return bookRepository.save(book);
    }

    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " does not exist."));
    }

}
