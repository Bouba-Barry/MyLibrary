package com.bouba.mylibrary.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class LoanServiceImpl implements LoanService{

    @Autowired
    private ILoanDao loanDao;

    @Override
    public List<Loan> allLoans() {
        return loanDao.findAll();
    }

    @Override
    public List<Loan> findAllLoansByEndDateBefore(LocalDate maxEndDate) {
        return loanDao.findAllLoansByEndDateBefore(maxEndDate);    }

    @Override
    public List<Loan> getAllOpenLoansOfThisCustomer(String email, String status) {
        return loanDao.getAllOpenLoanByCustomer(email,status);
    }

    @Override
    public Loan getOpenedLoan(SimpleLoanDTO simpleLoanDTO) {
        return loanDao.getLoanByCriteria(simpleLoanDTO.getBookId(), simpleLoanDTO.getCustomerId(), "OPEN");
    }

    @Override
    public boolean checkIfLoanExists(SimpleLoanDTO simpleLoanDTO) {
        Loan loan = loanDao.getLoanByCriteria(simpleLoanDTO.getBookId(), simpleLoanDTO.getCustomerId(), "OPEN");
        if(loan != null) {
            return true;
        }
        return false;

    }

    @Override
    public Loan saveLoan(Loan loan) {
        return loanDao.save(loan);
    }

    @Override
    public void closeLoan(Loan loan) {
       loan.setLoanStatus("CLOSE");
        loanDao.save(loan);
    }
}
