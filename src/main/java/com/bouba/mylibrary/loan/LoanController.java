package com.bouba.mylibrary.loan;


import com.bouba.mylibrary.book.Book;
import com.bouba.mylibrary.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/loan")
public class LoanController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);
    @Autowired
    private LoanServiceImpl loanService;

    @GetMapping("/")
    public ResponseEntity<List<SimpleLoanDTO>> getAllLoan(){
        List<Loan> loanList = loanService.allLoans();
        List<SimpleLoanDTO> loanDTOS = new ArrayList<>();
        for(Loan l: loanList){
            loanDTOS.add(mapLoanToSimpleLoanDTO(l));
        }
        return new ResponseEntity<>(loanDTOS, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/maxEndDate")
    public ResponseEntity<List<SimpleLoanDTO>> searchAllBooksLoanBeforeThisDate(@RequestParam("date") String  maxEndDateStr) {
        List<Loan> loans = loanService.findAllLoansByEndDateBefore(LocalDate.parse(maxEndDateStr));
        // on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
        loans.removeAll(Collections.singleton(null));
        List<SimpleLoanDTO> loanInfosDtos = new ArrayList<>();
        for(Loan l : loans){
            loanInfosDtos.add(mapLoanToSimpleLoanDTO(l));
        }
        return new ResponseEntity<List<SimpleLoanDTO>>(loanInfosDtos, HttpStatus.OK);
    }

    @PostMapping("/addLoan")
    public ResponseEntity<Boolean> createNewLoan(@RequestBody SimpleLoanDTO simpleLoanDTORequest) {
        boolean isLoanExists = loanService.checkIfLoanExists(simpleLoanDTORequest);
        if (isLoanExists) {
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
        }
        Loan LoanRequest = mapSimpleLoanDTOToLoan(simpleLoanDTORequest);
        Loan loan = loanService.saveLoan(LoanRequest);
        if (loan != null) {
            return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/customerLoans")
    public ResponseEntity<List<SimpleLoanDTO>> searchAllOpenedLoansOfThisCustomer(@RequestParam("email") String email) {
        List<Loan> loans = loanService.getAllOpenLoansOfThisCustomer(email, "OPEN");
        // on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
        loans.removeAll(Collections.singleton(null));
        List<SimpleLoanDTO> loanInfosDtos = new ArrayList<>();
        for(Loan l : loans){
            loanInfosDtos.add(mapLoanToSimpleLoanDTO(l));
        }
        return new ResponseEntity<List<SimpleLoanDTO>>(loanInfosDtos, HttpStatus.OK);
    }

    /**
     * Clôture le prêt de livre d'un client.
     * @param simpleLoanDTORequest
     * @return
     */
    @PostMapping("/closeLoan")
    public ResponseEntity<Boolean> closeLoan(@RequestBody SimpleLoanDTO simpleLoanDTORequest) {
        Loan existingLoan = loanService.getOpenedLoan(simpleLoanDTORequest);
        if (existingLoan == null) {
            return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
        }
        existingLoan.setLoanStatus("CLOSE");
        Loan loan = loanService.saveLoan(existingLoan);
        if (loan != null) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.NOT_MODIFIED);
    }

    private Loan mapSimpleLoanDTOToLoan(SimpleLoanDTO simpleLoanDTO) {
        Loan loan = new Loan();
        Book book = new Book();
        book.setId(simpleLoanDTO.getBookId());
        Customer customer = new Customer();
        customer.setId(simpleLoanDTO.getCustomerId());

       // loan.setLoanId(loanId);
        loan.setBeginDate(simpleLoanDTO.getBeginDate());
        loan.setEndDate(simpleLoanDTO.getEndDate());
        loan.setLoanStatus("OPEN");
        return loan;
    }

    private SimpleLoanDTO mapLoanToSimpleLoanDTO(Loan loan) {
        SimpleLoanDTO loanDTO = new SimpleLoanDTO();
        Book book = new Book();
        //book.setId(simpleLoanDTO.getBookId());
        book.setId(loan.getBook().getId());
        Customer customer = new Customer();
        customer.setId(loan.getCustomer().getId());
        //LoanId loanId = new LoanId(book, customer);
        //loan.setLoanId(loanId);
        loanDTO.setBookId(book.getId());
        loanDTO.setCustomerId(customer.getId());
        loanDTO.setEndDate(loan.getEndDate());
        loanDTO.setBeginDate(loan.getBeginDate());

        return loanDTO;
    }

}
