package com.financiera.loansandbooks.repository;

import com.financiera.loansandbooks.model.loan.Loan;
import com.financiera.loansandbooks.model.loan.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReaderIdAndStateIn(Long readerId, List<State> states);

    long countByReaderIdAndStateIn(Long readerId, List<State> states);
}
