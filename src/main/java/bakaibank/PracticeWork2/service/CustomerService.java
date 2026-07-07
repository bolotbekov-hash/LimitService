package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.dto.CustomerDto;
import bakaibank.PracticeWork2.dto.CustomerRequestDto;
import bakaibank.PracticeWork2.entity.Customer;
import bakaibank.PracticeWork2.mapper.CustomerMapper;
import bakaibank.PracticeWork2.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDto createCustomer(CustomerRequestDto request){
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .customerType(request.getCustomerType())
                .phoneNumber(request.getPhoneNumber())
                .inn(request.getInn())
                .createdAt(LocalDateTime.now())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerRequestDto request){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с ID" + id + "не найден!"));
        customer.setFullName(request.getFullName());
        customer.setCustomerType(request.getCustomerType());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setInn(request.getInn());

        Customer updateCustomer = customerRepository.save(customer);
        return customerMapper.toDto(updateCustomer);
    }

    @Transactional
    public List<CustomerDto> getAllCustomers(){
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public CustomerDto getCustomerById(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с ID " + "не найден"));
        return customerMapper.toDto(customer);
    }

    public Customer getCustomerEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с ID " + id + " не найден"));
    }
}
