package bakaibank.PracticeWork2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LimitCheckRequestDto {
    private Long cardId;
    private BigDecimal amount;
}