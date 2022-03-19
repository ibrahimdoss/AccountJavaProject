package com.SpringBoot.account.service;

import com.SpringBoot.account.dto.CustomerDto;
import com.SpringBoot.account.dto.converter.CustomerDtoConverter;
import com.SpringBoot.account.exception.CustomerNotFoundException;
import com.SpringBoot.account.model.Customer;
import com.SpringBoot.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }


    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(
                ()-> new CustomerNotFoundException("Customer could not find by id:" + id));
    }

    public CustomerDto getCustomerById(String customerId){
        return converter.convertToCustomerDto(findCustomerById(customerId));
    }


    public List<CustomerDto> getAllCustomer(){
        return customerRepository.findAll()
                .stream()
                .map(converter::convertToCustomerDto)
                .collect(Collectors.toList());
    }

}

//protected paket içidir yani sadece CustomerService'in kullanıldığı yerlerde kullanılabilir.