package com.in28minutes.springboot.controller;

import com.in28minutes.springboot.model.Customer;
import com.in28minutes.springboot.model.ResponseDelete;
import com.in28minutes.springboot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/list")
    public List<Customer> getCustomerList() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomerById(@PathVariable Integer customerId, HttpServletRequest request) {
        String nameEN = customerService.getMessage(String.valueOf(customerId), request);
        System.err.println(nameEN);

        // Lấy ra toàn bộ thông tin khách hàng theo id
        Customer customer1 = customerService.getCustomerById(customerId).get();
        int id=customer1.getId();
        String name=customer1.getName();
        String email=customer1.getEmail();
        int age = customer1.getAge();

        Customer customer = new Customer(id, name, nameEN, email, age);
        return customer;
    }

    @GetMapping("/customer/delete/{customerId}")
    public ResponseDelete deleteCustomerById(@PathVariable Integer customerId) {
        ResponseDelete responseDelete = new ResponseDelete();
        responseDelete.setSuccess(customerService.deleteCustomerById(customerId));
        return responseDelete;
    }
}
