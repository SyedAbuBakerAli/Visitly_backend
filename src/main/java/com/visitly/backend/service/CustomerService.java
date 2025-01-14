package com.visitly.backend.service;

import com.visitly.backend.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<CustomerEntity> getAllCustomer();

    public Optional<CustomerEntity> getCustomerById(Long id);

    public CustomerEntity createCustomer(CustomerEntity customer);

    public CustomerEntity updateCustomer(CustomerEntity customer,Long id);

    public void deleteCustomer(Long id);
}
