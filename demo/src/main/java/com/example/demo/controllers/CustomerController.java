package com.example.demo.controllers;

import com.example.demo.dtos.CustomerRecordDto;
import com.example.demo.models.CustomerModel;
import com.example.demo.repositories.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customers")
    public ResponseEntity<CustomerModel> createNewCustomer(@RequestBody @Valid CustomerRecordDto customerRecordDto) {
        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRecordDto, customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerRepository.save(customerModel));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value = "id") Long id) {
        Optional<CustomerModel> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        } else {
            return ResponseEntity.ok(customerOpt.get());
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid CustomerRecordDto customerRecordDto) {
        Optional<CustomerModel> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        var existingCustomer = customerOpt.get();
        BeanUtils.copyProperties(customerRecordDto, existingCustomer, "id"); // Evitar sobrescrever o ID
        return ResponseEntity.ok(customerRepository.save(existingCustomer));
    }
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") Long id) {
        Optional<CustomerModel> customerOpt = customerRepository.findById(id);

        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        customerRepository.delete(customerOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

