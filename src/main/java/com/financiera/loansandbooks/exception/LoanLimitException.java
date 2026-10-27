package com.financiera.loansandbooks.exception;

public class LoanLimitException extends RuntimeException {
    public LoanLimitException(String message) {
        super(message);
    }
}
