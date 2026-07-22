package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.dto.CardDto;
import bakaibank.PracticeWork2.dto.CardRequestDto;
import bakaibank.PracticeWork2.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardRequestDto request){
        return new ResponseEntity<>(cardService.createCard(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable Long id, @Valid @RequestBody CardRequestDto request){
        return ResponseEntity.ok(cardService.updateCard(id, request));
    }

    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<List<CardDto>> getActiveCardsByCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(cardService.getActiveCardsByCustomerId(customerId));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getCardById(id));
    }

}
