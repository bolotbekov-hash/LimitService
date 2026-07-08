package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.dto.CardLimitResponseDto;
import bakaibank.PracticeWork2.dto.CardRequestDto;
import bakaibank.PracticeWork2.dto.UpdateLimitRequestDto;
import bakaibank.PracticeWork2.entity.Card;
import bakaibank.PracticeWork2.entity.CardLimit;
import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.mapper.CardLimitMapper;
import bakaibank.PracticeWork2.repository.CardLimitRepository;
import bakaibank.PracticeWork2.repository.LimitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardLimitService {
    private final CardLimitRepository cardLimitRepository;
    private final CardLimitMapper cardLimitMapper;
    private final LimitService limitService;
    private final LimitHistoryService limitHistoryService;

    @Transactional
    public void assignDefaultLimitsToCard(Card card){
        List<Limit> defaultLimits = limitService.getAllLimits();

        for (Limit limit : defaultLimits){
            BigDecimal initialSum = limit.getDefaultSum() != null ? limit.getDefaultSum() : BigDecimal.ZERO;
            Integer initialOperations = limit.getDefaultOperationCount() != null ? limit.getDefaultOperationCount() : 0;

            CardLimit cardLimit = CardLimit.builder()
                    .card(card)
                    .limit(limit)
                    .currentSum(initialSum)
                    .operationsCount(initialOperations)
                    .lastUpdatedAt(LocalDateTime.now())
                    .build();

            cardLimitRepository.save(cardLimit);


            limitHistoryService.recordHistory(
                    card,
                    limit,
                    BigDecimal.ZERO,
                    initialSum,
                    initialOperations
            );
        }
    }

    @Transactional
    public CardLimitResponseDto updateCardLimit(Long cardLimitId, UpdateLimitRequestDto request){
        CardLimit cardLimit = cardLimitRepository.findById(cardLimitId)
                .orElseThrow(() -> new RuntimeException("Лимит карты с ID " + cardLimitId + " не найден"));
        Card card = cardLimit.getCard();

        if(!"ACTIVE".equals(card.getStatus())){
            throw new RuntimeException("Изменение лимита невозможно: карта заблокирована или неактивна");
        }
        BigDecimal newSum = request.getNewSum();

        if(newSum.compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("Лимит не может быть отрицательным");
        }

        BigDecimal maxAllowedLimit = new BigDecimal("1000000");
        if(newSum.compareTo(maxAllowedLimit) > 0){
            throw new RuntimeException("Максимальный лимит - 1 000 000");
        }

        Integer newOperationCount = request.getNewOperationCount();
        if (newOperationCount != null && newOperationCount < 0) {
            throw new RuntimeException("Количество операций не может быть отрицательным");
        }

        BigDecimal oldSum = cardLimit.getCurrentSum();
        Integer currentOperations = cardLimit.getOperationsCount();

        cardLimit.setCurrentSum(newSum);
        if (newOperationCount != null) {
            cardLimit.setOperationsCount(newOperationCount);
        }
        cardLimit.setLastUpdatedAt(LocalDateTime.now());
        CardLimit updatedCardLimit = cardLimitRepository.save(cardLimit);

        limitHistoryService.recordHistory(
                card,
                cardLimit.getLimit(),
                oldSum,
                newSum,
                newOperationCount != null ? newOperationCount : currentOperations
        );

        return cardLimitMapper.toResponseDto(updatedCardLimit);

    }

    @Transactional
    public List<CardLimitResponseDto> getLimitsByCardId(Long cardId){
        return cardLimitRepository.findByCardId(cardId).stream()
                .map(cardLimitMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
