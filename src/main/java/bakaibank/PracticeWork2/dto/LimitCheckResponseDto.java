package bakaibank.PracticeWork2.dto;

import lombok.Data;

@Data
public class LimitCheckResponseDto {
    private boolean allowed;
    private String reason;

    public LimitCheckResponseDto(boolean allowed, String reason) {
        this.allowed = allowed;
        this.reason = reason;
    }
}
