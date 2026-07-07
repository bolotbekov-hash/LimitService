package bakaibank.PracticeWork2.mapper;

import bakaibank.PracticeWork2.dto.CardLimitResponseDto;
import bakaibank.PracticeWork2.entity.CardLimit;
import org.springframework.stereotype.Component;

@Component
public class CardLimitMapper {
    public CardLimitResponseDto toResponseDto(CardLimit cardLimit) {
        if (cardLimit == null) return null;

        CardLimitResponseDto dto = new CardLimitResponseDto();
        dto.setId(cardLimit.getId());

        if (cardLimit.getCard() != null) {
            dto.setCardId(cardLimit.getCard().getId());
        }

        if (cardLimit.getLimit() != null) {
            dto.setLimitId(cardLimit.getLimit().getId());
            dto.setLimitTypeName(cardLimit.getLimit().getType());
        }

        dto.setCurrentSum(cardLimit.getCurrentSum());
        dto.setOperationsCount(cardLimit.getOperationsCount());
        dto.setLastUpdatedAt(cardLimit.getLastUpdatedAt());

        return dto;
    }
}
