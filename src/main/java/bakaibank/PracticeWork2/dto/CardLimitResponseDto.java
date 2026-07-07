package bakaibank.PracticeWork2.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CardLimitResponseDto {
    private Long id;
    private Long cardId;
    private Long limitId;
    private String limitTypeName; // Название лимита
    private BigDecimal currentSum;
    private Integer operationsCount;
    private LocalDateTime lastUpdatedAt;
}
