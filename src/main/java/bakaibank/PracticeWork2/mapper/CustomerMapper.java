package bakaibank.PracticeWork2.mapper;

import bakaibank.PracticeWork2.dto.CustomerDto;
import bakaibank.PracticeWork2.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto toDto(Customer customer){
        if (customer == null) return null;

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setCustomerType(customer.getCustomerType());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setInn(customer.getInn());
        return dto;
    }


}
