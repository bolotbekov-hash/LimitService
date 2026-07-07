package bakaibank.PracticeWork2.mapper;

import bakaibank.PracticeWork2.dto.LimitHistoryResponseDto;
import bakaibank.PracticeWork2.entity.LimitHistory;
import org.springframework.stereotype.Component;

@Component
public class LimitHistoryMapper {
    public LimitHistoryResponseDto toResponseDto(LimitHistory history) {
        if (history == null) return null;

        LimitHistoryResponseDto dto = new LimitHistoryResponseDto();
        dto.setId(history.getId());

        if (history.getCard() != null) {
            dto.setCardId(history.getCard().getId());
        }

        if (history.getLimit() != null) {
            dto.setLimitId(history.getLimit().getId());
            dto.setLimitTypeName(history.getLimit().getType());
        }

        dto.setOldSum(history.getOldSum());
        dto.setNewSum(history.getNewSum());
        dto.setOldOperations(history.getOldOperations());
        dto.setNewOperations(history.getNewOperations());
        dto.setChangedAt(history.getChangedAt());

        return dto;
    }
}
