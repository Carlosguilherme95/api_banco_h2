package com.example.demo.controllers;

import com.example.demo.dtos.CustomerRecordDto;
import com.example.demo.models.CustomerModel;
import com.example.demo.repositories.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerModel> createNewCustomer(@RequestBody @Valid CustomerRecordDto customerRecordDto) {
        CustomerModel customerModel = customerService.createCustomer(customerRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerModel);
    }
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        List<CustomerModel> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> findCustomerById(@PathVariable Long id) {
        CustomerModel customer = customerService.getUserById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id, @RequestBody CustomerModel updateCustomer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, updateCustomer));
    }

}
