package com.financiera.loansandbooks.controller;

import com.financiera.loansandbooks.controller.Dtos.ReaderDTO;
import com.financiera.loansandbooks.model.loan.Loan;
import com.financiera.loansandbooks.model.reader.Reader;
import com.financiera.loansandbooks.service.LoanService;
import com.financiera.loansandbooks.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin({"*"})
@RequestMapping("/readers")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Reader> create(@RequestBody @Valid ReaderDTO body){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(readerService.createReader(body));
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAll(){
        return ResponseEntity.ok(readerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getById(@PathVariable Long id){
        return ResponseEntity.ok(readerService.findById(id));
    }

    @PatchMapping("/{id}/rehabilitate")
    public ResponseEntity<Reader> rehabilitate(@PathVariable Long id){
        return ResponseEntity.ok(readerService.rehabilitateReader(id));
    }

    @GetMapping("/{id}/loans/active")
    public ResponseEntity<List<Loan>> getActiveLoans(@PathVariable Long id){
        return ResponseEntity.ok(loanService.getActiveLoansByReader(id));
    }
}
