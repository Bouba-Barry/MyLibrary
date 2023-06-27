package com.bouba.mylibrary.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.lastName LIKE %:name%")
    public List<Customer> findCustomersByLastName(@Param("name") String name);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findCustomerByEmail(@Param("email") String email);

}
