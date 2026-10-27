package com.financiera.loansandbooks.service;

import com.financiera.loansandbooks.controller.Dtos.LoanDTO;
import com.financiera.loansandbooks.exception.InactiveReaderException;
import com.financiera.loansandbooks.exception.InvalidLoanStateException;
import com.financiera.loansandbooks.exception.LoanLimitException;
import com.financiera.loansandbooks.exception.NoStockException;
import com.financiera.loansandbooks.exception.ResourceNotFoundException;
import com.financiera.loansandbooks.model.book.Book;
import com.financiera.loansandbooks.model.loan.Loan;
import com.financiera.loansandbooks.model.loan.State;
import com.financiera.loansandbooks.model.reader.Reader;
import com.financiera.loansandbooks.repository.BookRepository;
import com.financiera.loansandbooks.repository.LoanRepository;
import com.financiera.loansandbooks.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private static final List<State> ACTIVE_STATES = List.of(State.LOANED, State.EXPIRED);
    private static final int MAX_LOANS = 3;
    private static final int MAX_LOANS_PREMIUM = 5;
    private static final int LOAN_DAYS = 14;
    private static final int RENEW_DAYS = 7;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Loan> getAll(){
        return loanRepository.findAll();
    }

    public Loan findById(Long id){
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan with ID " + id + " does not exist."));
    }

    @Transactional
    public Loan requestLoan(LoanDTO loanDTO){
        Reader reader = readerService.findById(loanDTO.getReaderId());
        if (!reader.isActive()) {
            throw new InactiveReaderException("Reader with ID " + reader.getId() + " is not active.");
        }

        Book book = bookService.findById(loanDTO.getBookId());
        if (book.getStock() < 1) {
            throw new NoStockException("Book with ID " + book.getId() + " has no copies available.");
        }

        int limit = reader.isPremium() ? MAX_LOANS_PREMIUM : MAX_LOANS;
        long activeLoans = loanRepository.countByReaderIdAndStateIn(reader.getId(), ACTIVE_STATES);
        if (activeLoans >= limit) {
            throw new LoanLimitException("Reader with ID " + reader.getId() + " has reached the limit of " + limit + " active loans.");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        LocalDate today = LocalDate.now();
        Loan loan = new Loan();
        loan.setReader(reader);
        loan.setBook(book);
        loan.setIssueDate(today);
        loan.setExpireDate(today.plusDays(LOAN_DAYS));
        loan.setState(State.LOANED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(Long id){
        Loan loan = findById(id);
        if (!ACTIVE_STATES.contains(loan.getState())) {
            throw new InvalidLoanStateException("Loan with ID " + id + " can not be returned because its state is " + loan.getState() + ".");
        }

        loan.setState(State.RETURNED);

        Book book = loan.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        if (LocalDate.now().isAfter(loan.getExpireDate())) {
            Reader reader = loan.getReader();
            reader.setActive(false);
            readerRepository.save(reader);
        }

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan renewLoan(Long id){
        Loan loan = findById(id);
        if (loan.getState() == State.EXPIRED || loan.getState() == State.RETURNED) {
            throw new InvalidLoanStateException("Loan with ID " + id + " can not be renewed because its state is " + loan.getState() + ".");
        }

        loan.setExpireDate(loan.getExpireDate().plusDays(RENEW_DAYS));
        return loanRepository.save(loan);
    }

    public List<Loan> getActiveLoansByReader(Long readerId){
        readerService.findById(readerId);
        return loanRepository.findByReaderIdAndStateIn(readerId, ACTIVE_STATES);
    }
}
