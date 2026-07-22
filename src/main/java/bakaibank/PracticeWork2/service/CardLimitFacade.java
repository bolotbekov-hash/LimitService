package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.dto.CardLimitResponseDto;
import bakaibank.PracticeWork2.dto.LimitCheckResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class CardLimitFacade {
    private final CardLimitService cardLimitService;

    public LimitCheckResponseDto checkLimit(Long cardId, BigDecimal amount) {
        List<CardLimitResponseDto> limits = cardLimitService.getLimitsByCardId(cardId);

        if (limits == null || limits.isEmpty()) {
            log.warn("Для карты id {} не найдено ни одного лимита", cardId);
            return new LimitCheckResponseDto(false, "LIMIT_NOT_FOUND");
        }

        CardLimitResponseDto cardLimitResponseDto = limits.stream()
                .filter(dto -> dto.getLimitId() != null)
                .findFirst()
                .orElse(limits.get(0));

        BigDecimal currentSum = cardLimitResponseDto.getCurrentSum();

        if (currentSum == null) {
            log.error("Для карты id {} поле currentSum равно null", cardId);
            return new LimitCheckResponseDto(false, "INVALID_LIMIT_DATA");
        }

        if (amount.compareTo(currentSum) > 0) {
            log.info("Превышен лимит для карты {}: запрос {}, доступно {}", cardId, amount, currentSum);
            return new LimitCheckResponseDto(false, "LIMIT_EXCEEDED");
        }

        return new LimitCheckResponseDto(true, "ALLOWED");
    }

}
