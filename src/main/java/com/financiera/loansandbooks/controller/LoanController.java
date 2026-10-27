package com.financiera.loansandbooks.controller;

import com.financiera.loansandbooks.controller.Dtos.LoanDTO;
import com.financiera.loansandbooks.model.loan.Loan;
import com.financiera.loansandbooks.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin({"*"})
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> request(@RequestBody @Valid LoanDTO body){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.requestLoan(body));
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll(){
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getById(@PathVariable Long id){
        return ResponseEntity.ok(loanService.findById(id));
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id){
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @PatchMapping("/{id}/renew")
    public ResponseEntity<Loan> renew(@PathVariable Long id){
        return ResponseEntity.ok(loanService.renewLoan(id));
    }
}
