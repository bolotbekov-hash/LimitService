package bakaibank.PracticeWork2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CardRequestDto {
    @NotNull(message = "ID клиента обязателен")
    private Long customerId;

    @NotBlank(message = "Номер карты обязателен")
    private String cardNumber;

    @NotBlank(message = "Номер счета обязателен")
    private String accountNumber;

    @NotBlank(message = "Тип карты обязателен")
    private String cardType;
}