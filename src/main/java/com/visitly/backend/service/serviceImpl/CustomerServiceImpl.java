package com.visitly.backend.service.serviceImpl;

import com.visitly.backend.entity.CustomerEntity;
import com.visitly.backend.repository.CustomerRepository;
import com.visitly.backend.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Override
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findAll();

    }

    @Override
    public Optional<CustomerEntity> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) {
       CustomerEntity customerEntity = new CustomerEntity();
       customerEntity.setCustomerName(customer.getCustomerName());
       customerEntity.setEmail(customer.getEmail());
       customerEntity.setLocation(customer.getLocation());
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity updateCustomer(CustomerEntity customer, Long id) {
       Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
       if(customerEntity.isPresent()){
           CustomerEntity saveCustomer = customerEntity.get();
           saveCustomer.setCustomerName(customer.getCustomerName());
           saveCustomer.setLocation(customer.getLocation());
           saveCustomer.setEmail(customer.getEmail());
           return customerRepository.save(saveCustomer);
       }else{
           throw new EntityNotFoundException("Entity Not Found");
       }
    }

    @Override
    public void deleteCustomer(Long id) {
         customerRepository.deleteById(id);
    }

}
