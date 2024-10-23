package com.example.demo.service;


import com.example.demo.dtos.CustomerRecordDto;
import com.example.demo.models.CustomerModel;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerModel createCustomer(CustomerRecordDto customerRecordDto){
        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRecordDto, customerModel);
        return customerRepository.save(customerModel);
    }

    public List<CustomerModel> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void deleteCustomer (Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerModel getUserById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerModel updateCustomer(Long id, CustomerModel updateCustomer) {
        CustomerModel existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existingCustomer.setName(updateCustomer.getName());
        existingCustomer.setCpf(updateCustomer.getCpf());
        return customerRepository.save(existingCustomer);
        }
}
