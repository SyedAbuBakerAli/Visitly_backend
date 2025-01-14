package com.visitly.backend.controller;


import com.visitly.backend.entity.CustomerEntity;
import com.visitly.backend.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Authorization")
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/customers")
    public List<CustomerEntity> getAllCustomers(){
         return customerService.getAllCustomer();
    }

    @GetMapping("/customer/{id}")
    public Optional<CustomerEntity> getAllCustomers(@PathVariable("id") Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("/create")
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer){
        return customerService.createCustomer(customer);
    }

    @PutMapping("/update/{id}")
    public CustomerEntity updateCustomer(@RequestBody CustomerEntity customer,@PathVariable("id") Long id){
        return customerService.updateCustomer(customer,id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }
}
