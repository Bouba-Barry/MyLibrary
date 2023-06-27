package com.bouba.mylibrary.loan;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {

    public List<Loan> allLoans();

    public List<Loan> findAllLoansByEndDateBefore(LocalDate maxEndDate);

    public List<Loan> getAllOpenLoansOfThisCustomer(String email, String status);

    public Loan getOpenedLoan(SimpleLoanDTO simpleLoanDTO);

    public boolean checkIfLoanExists(SimpleLoanDTO simpleLoanDTO);

    public Loan saveLoan(Loan loan);

    public void closeLoan(Loan loan);
}
