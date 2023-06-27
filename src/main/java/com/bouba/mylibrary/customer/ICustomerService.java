package com.bouba.mylibrary.customer;

import java.util.List;

public interface ICustomerService {
    public List<Customer> allCustomer();
    public Customer saveCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public void deleteCustomer(Long id);

    public Customer findCustomerByEmail(String email);
    public boolean checkIfExist(Long id);

    public List<Customer> findCustomersByLastName(String last);
}
