package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.dto.LimitHistoryResponseDto;
import bakaibank.PracticeWork2.entity.Card;
import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.entity.LimitHistory;
import bakaibank.PracticeWork2.mapper.LimitHistoryMapper;
import bakaibank.PracticeWork2.repository.LimitHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LimitHistoryService {
    private final LimitHistoryRepository limitHistoryRepository;
    private final LimitHistoryMapper limitHistoryMapper;


    @Transactional
    public void recordHistory(Card card, Limit limit, BigDecimal oldSum, BigDecimal newSum, Integer operationsCount){
        LimitHistory history = LimitHistory.builder()
                .card(card)
                .limit(limit)
                .oldSum(oldSum)
                .newSum(newSum)
                .oldOperations(operationsCount)
                .newOperations(operationsCount)
                .changedAt(LocalDateTime.now())
                .build();

        limitHistoryRepository.save(history);
    }
    @Transactional
    public List<LimitHistoryResponseDto> getHistoryByCardId(Long cardId){
        return limitHistoryRepository.findByCardIdOrderByChangedAtDesc(cardId).stream()
                .map(limitHistoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
