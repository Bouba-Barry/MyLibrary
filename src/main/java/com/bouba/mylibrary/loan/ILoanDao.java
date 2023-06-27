package com.bouba.mylibrary.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ILoanDao extends JpaRepository<Loan, Long> {


    @Query("SELECT lo "
            + "FROM Loan lo "
            + "INNER JOIN lo.customer c "
            + "WHERE c.email = ?1 "
            + "   AND lo.loanStatus = ?2 ")
    public List<Loan> getAllOpenLoanByCustomer(String email, String status);

    @Query(
            "SELECT lo From Loan lo Where lo.endDate = :maxEndDate"
    )
    public List<Loan> findAllLoansByEndDateBefore(LocalDate maxEndDate);

    @Query(   "SELECT lo "
            + "FROM Loan lo "
            + "INNER JOIN lo.book b "
            + "INNER JOIN lo.customer c "
            + "WHERE b.id =	?1 "
            + "   AND c.id = ?2 "
            + "   AND lo.loanStatus = ?3 ")
    public Loan getLoanByCriteria(Long bookId, Long customerId, String loanStatus);

}
