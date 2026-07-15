package bakaibank.PracticeWork2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    private String fullName;
    private LocalDateTime createdAt;
    private Integer customerType;
    private String phoneNumber;
    private String inn;
    private String email;
}