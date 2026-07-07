package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.dto.LimitHistoryResponseDto;
import bakaibank.PracticeWork2.service.LimitHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/limit-history")
@RequiredArgsConstructor
public class LimitHistoryController {

    private final LimitHistoryService limitHistoryService;

    @GetMapping("/get/card/{cardId}")
    public ResponseEntity<List<LimitHistoryResponseDto>> getHistoryByCardId(@PathVariable Long cardId) {
        return ResponseEntity.ok(limitHistoryService.getHistoryByCardId(cardId));
    }

}
