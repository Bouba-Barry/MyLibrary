package com.bouba.mylibrary.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public List<Customer> allCustomer() {
        return customerDao.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerDao.deleteById(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerDao.findCustomerByEmail(email);
    }

    @Override
    public boolean checkIfExist(Long id) {
        boolean ret = false;
        if(customerDao.findById(id) != null){
            ret = true;
        }
        else{
            ret = false;
        }
        return ret;
    }

    @Override
    public List<Customer> findCustomersByLastName(String last) {
        return customerDao.findCustomersByLastName(last);
    }
}
