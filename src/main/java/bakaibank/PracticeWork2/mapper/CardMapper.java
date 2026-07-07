package bakaibank.PracticeWork2.mapper;

import bakaibank.PracticeWork2.dto.CardDto;
import bakaibank.PracticeWork2.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardDto toDto(Card card){
        if (card == null) return null;

        CardDto dto = new CardDto();
        dto.setId(card.getId());
        if(card.getCustomer() != null){
            dto.setCustomerId(card.getCustomer().getId());
        }
        dto.setCardNumber(card.getCardNumber());
        dto.setAccountNumber(card.getAccountNumber());
        dto.setCardType(card.getCardType());
        dto.setStatus(card.getStatus());
        dto.setOpenedAt(card.getOpenedAt());
        dto.setEndAt(card.getEndAt());
        dto.setClosetAt(card.getClosetAt());
        return dto;

    }
}
