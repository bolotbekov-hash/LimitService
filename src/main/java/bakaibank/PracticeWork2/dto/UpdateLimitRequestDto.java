package bakaibank.PracticeWork2.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateLimitRequestDto {
    @NotNull(message = "Сумма лимита обязательна")
    @Min(value = 0, message = "Лимит не может быть отрицательным")
    @Max(value = 1000000, message = "Максимальный лимит — 1 000 000")
    private BigDecimal newSum;

    @NotNull(message = "Колчество операций обязательна")
    @Min(value = 0, message = "Количество операций не может быть отрицательным")
    @Max(value = 100, message = "Максимальное количство операций — 100")
    private Integer newOperationCount;
}
