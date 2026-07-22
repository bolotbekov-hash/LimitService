package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.dto.LimitCheckRequestDto;
import bakaibank.PracticeWork2.dto.LimitCheckResponseDto;
import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.service.CardLimitFacade;
import bakaibank.PracticeWork2.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/limits")
@RequiredArgsConstructor
public class LimitController {
    private final LimitService limitService;
    private final CardLimitFacade cardLimitFacade;

    @PostMapping
    public ResponseEntity<Limit> createLimit(@RequestBody Limit limit) {
        return new ResponseEntity<>(limitService.createLimit(limit), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Limit> updateLimit(@PathVariable Long id, @RequestBody Limit limit) {
        return ResponseEntity.ok(limitService.updateLimit(id, limit));
    }

    @GetMapping("/get/active")
    public ResponseEntity<List<Limit>> getAllActiveLimits() {
        return ResponseEntity.ok(limitService.getAllActiveLimits());
    }

    @PostMapping("/check")
    public ResponseEntity<LimitCheckResponseDto> checkLimit(@RequestBody LimitCheckRequestDto request) {
        LimitCheckResponseDto response = cardLimitFacade.checkLimit(request.getCardId(), request.getAmount());

        return ResponseEntity.ok(response);
    }
}
