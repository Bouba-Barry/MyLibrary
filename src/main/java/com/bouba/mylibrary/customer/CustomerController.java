package com.bouba.mylibrary.customer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        List<Customer> customers = customerService.allCustomer();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer c: customers){
            customerDTOS.add(mapCustomerToCustomerDTO(c));
        }
        return new ResponseEntity<>(customerDTOS, HttpStatusCode.valueOf(200));
    }
    @PostMapping("/newCustomer")
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO cust){
        Customer existingCustomer = customerService.findCustomerByEmail(cust.getEmail());
        if (existingCustomer != null) {
            return new ResponseEntity<CustomerDTO>(HttpStatus.CONFLICT);
        }
        Customer customerRequest = mapCustomerDTOToCustomer(cust);
        customerRequest.setCreationDate(LocalDate.now());
        Customer customerResponse = customerService.saveCustomer(customerRequest);
        if (customerResponse != null) {
            CustomerDTO customerDTO = mapCustomerToCustomerDTO(customerResponse);
            return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/updateCustomer")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO cust){
        Customer custToUpdate = customerService.findCustomerByEmail(cust.getEmail());
        if(custToUpdate != null){
            customerService.updateCustomer(custToUpdate);
            CustomerDTO custToReturn = mapCustomerToCustomerDTO(custToUpdate);
            return new ResponseEntity<CustomerDTO>(custToReturn, HttpStatus.OK);
        }else{
            return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteCustomer/{custId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long custId) {
        customerService.deleteCustomer(custId);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }


    private CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        CustomerDTO customerDTO = mapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam("email") String email){
        System.out.println("customer email = "+email);
        System.out.println("customer email = "+email);
        System.out.println("customer email = "+email);
        Customer customer = customerService.findCustomerByEmail(email);
        System.out.println("customer id = "+ customer.getId());
        if(!customerService.checkIfExist(customer.getId())){
            return new ResponseEntity<CustomerDTO>(HttpStatus.NO_CONTENT);        }
        else{
            CustomerDTO customerDTO = mapCustomerToCustomerDTO(customer);
            return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/searchByLastName")
    public ResponseEntity<List<CustomerDTO>> getCustomersByLastName(@RequestParam("lastName") String lastName){
        List<Customer> customers = customerService.findCustomersByLastName(lastName);
        List<CustomerDTO> customToReturn = new ArrayList<>();
        if(customers != null){
            for(Customer c : customers){
                customToReturn.add(mapCustomerToCustomerDTO(c));
            }
            return new ResponseEntity<>(customToReturn, HttpStatusCode.valueOf(200));
        }
        else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
    }

   /** @PutMapping("/sendMailToCustomer")
    public ResponseEntity<Boolean> sendMailToCustomers(@RequestBody  MailDTO mail, UriComponentsBuilder uriComponentBuilder){
       // Customer customer = customerService.findCustomerById(loanMailDto.getCustomerId());
        Customer customer = customerService.findCustomerByEmail(mail.getEmail());
        if (customer == null) {
            String errorMessage = "The selected Customer for sending email is not found in the database";
            LOGGER.info(errorMessage);
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        } else if (customer != null && customer.getEmail().isEmpty()) {
            String errorMessage = "No existing email for the selected Customer for sending email to";
            LOGGER.info(errorMessage);
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        }

        SimpleMailMessage mailToSend = new SimpleMailMessage();
        mailToSend.setFrom(mailToSend.getFrom());
        mailToSend.setTo(customer.getEmail());
        mailToSend.setSentDate(new Date());
        mailToSend.setSubject(mail.getMailSubject());

        try {
            javaMailSender.send(mailToSend);
        } catch (MailException e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }**/
    private Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO) {
        ModelMapper mapper = new ModelMapper();
        Customer customer = mapper.map(customerDTO, Customer.class);
        return customer;
    }
}
