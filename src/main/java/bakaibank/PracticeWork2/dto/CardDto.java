package bakaibank.PracticeWork2.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CardDto {
    private Long id;
    private Long customerId;
    private String cardNumber;
    private String accountNumber;
    private String cardType;
    private String status;
    private LocalDateTime openedAt;
    private LocalDateTime endAt;
    private LocalDateTime closetAt;

}
