package com.in28minutes.springboot.service;

import com.in28minutes.springboot.model.Customer;

import com.in28minutes.springboot.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private MessageSource messageSource;

    // đọc file tên tiếng anh
    public String getMessage(String code, HttpServletRequest request) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    // lấy toàn bộ khách hàng
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // lấy khách hàng theo id
    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    // xóa khách hàng theo id
    public boolean deleteCustomerById (int id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        if (getCustomerById(id) == null)
            return true;
        return false;
    }

}
