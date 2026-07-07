package bakaibank.PracticeWork2.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LimitHistoryResponseDto {
    private Long id;
    private Long cardId;
    private Long limitId;
    private String limitTypeName;
    private BigDecimal oldSum;
    private BigDecimal newSum;
    private Integer oldOperations;
    private Integer newOperations;
    private LocalDateTime changedAt;
}
