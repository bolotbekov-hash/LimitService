package bakaibank.PracticeWork2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationEventDto {
    private Long cardId;
    private Long clientId;
    private String cardNumber;
    private String limitType;
    private BigDecimal oldLimit;
    private BigDecimal newLimit;
    private LocalDateTime changedAt;
    private String clientEmail;
}