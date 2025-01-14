package com.visitly.backend.service;

import com.visitly.backend.entity.CustomerEntity;
import com.visitly.backend.repository.CustomerRepository;
import com.visitly.backend.service.serviceImpl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomer() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setId(1L);
        customer1.setCustomerName("John Doe");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setId(2L);
        customer2.setCustomerName("Jane Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<CustomerEntity> customers = customerService.getAllCustomer();

        assertNotNull(customers);
        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Found() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setCustomerName("John Doe");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<CustomerEntity> result = customerService.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getCustomerName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerEntity> result = customerService.getCustomerById(1L);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setLocation("New York");

        CustomerEntity savedCustomer = new CustomerEntity();
        savedCustomer.setId(1L);
        savedCustomer.setCustomerName("John Doe");
        savedCustomer.setEmail("john.doe@example.com");
        savedCustomer.setLocation("New York");

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomer);

        CustomerEntity result = customerService.createCustomer(customer);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getCustomerName());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void testUpdateCustomer_Found() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setLocation("New York");

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(1L);
        existingCustomer.setCustomerName("Jane Doe");
        existingCustomer.setEmail("jane.doe@example.com");
        existingCustomer.setLocation("Los Angeles");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

        CustomerEntity result = customerService.updateCustomer(customer, 1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        assertEquals("New York", result.getLocation());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setLocation("New York");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.updateCustomer(customer, 1L));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}

