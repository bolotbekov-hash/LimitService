package bakaibank.PracticeWork2.controller;

import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/limits")
@RequiredArgsConstructor
public class LimitController {
    private final LimitService limitService;

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
}
