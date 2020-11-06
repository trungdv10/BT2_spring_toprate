package com.in28minutes.springboot.service;

import com.in28minutes.springboot.model.Customer;

import com.in28minutes.springboot.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    // tim tat ca khach hang
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }


    // get customer by id from list customer
    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    // delete customer by id
    public boolean deleteCustomerById (int id) {
        // nếu phần tử không tồn tại nhẩy vào exception
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        // kiểm tra phần tử xóa chưa
        if (getCustomerById(id) == null)
            return true;
        return false;
    }


}
