package bakaibank.PracticeWork2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDto {
    @NotBlank(message = "ФИО обязательно для заполнения")
    private String fullName;

    @NotNull(message = "Тип клиента обязателен")
    private Integer customerType;

    @NotBlank(message = "Номер телефона обязателен для заполнения")
    @Pattern(
            regexp = "^\\+996\\d{9}$",
            message = "Номер телефона должен начинаться с +996 и содержать 9 цифр после кода"
    )
    private String phoneNumber;

    // Валидация для ИНН (строго 14 цифр)
    @NotBlank(message = "ИНН обязателен для заполнения")
    @Size(min = 14, max = 14, message = "ИНН должен состоять ровно из 14 цифров")
    @Pattern(regexp = "^\\d{14}$", message = "ИНН должен содержать только цифры")
    private String inn;

    private String email;
}
