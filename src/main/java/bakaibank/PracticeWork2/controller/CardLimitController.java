package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.dto.CardLimitResponseDto;
import bakaibank.PracticeWork2.dto.UpdateLimitRequestDto;
import bakaibank.PracticeWork2.service.CardLimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card-limits")
@RequiredArgsConstructor
public class CardLimitController {
    private final CardLimitService cardLimitService;

    @PutMapping("update/{cardLimitId}")
    public ResponseEntity<CardLimitResponseDto> updateLimit(
            @PathVariable Long cardLimitId,
            @Valid @RequestBody UpdateLimitRequestDto request){
        return ResponseEntity.ok(cardLimitService.updateCardLimit(cardLimitId, request));
    }

    @GetMapping("/get/card/{cardId}")
    public ResponseEntity<List<CardLimitResponseDto>> getLimitsByCardId(@PathVariable Long cardId){
        return ResponseEntity.ok(cardLimitService.getLimitsByCardId(cardId));
    }


}
