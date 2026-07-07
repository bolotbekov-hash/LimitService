package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.dto.CardDto;
import bakaibank.PracticeWork2.dto.CardRequestDto;
import bakaibank.PracticeWork2.entity.Card;
import bakaibank.PracticeWork2.entity.Customer;
import bakaibank.PracticeWork2.mapper.CardMapper;
import bakaibank.PracticeWork2.repository.CardRepository;
import bakaibank.PracticeWork2.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CustomerService customerService;
    private final CardMapper cardMapper;
    private final CardLimitService cardLimitService;

    @Transactional
    public CardDto createCard(CardRequestDto request){
        Customer customer = customerService.getCustomerEntityById(request.getCustomerId());

        Card card = Card.builder()
                .customer(customer)
                .cardNumber(request.getCardNumber())
                .accountNumber(request.getAccountNumber())
                .cardType(request.getCardType())
                .status("ACTIVE")
                .openedAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusYears(5))
                .build();

        Card savedCard = cardRepository.save(card);
        cardLimitService.assignDefaultLimitsToCard(savedCard);

        return cardMapper.toDto(savedCard);
    }

    @Transactional
    public CardDto updateCard(Long id, CardRequestDto request){
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта с ID " + id +  " не найден"));

        card.setCardType(request.getCardType());
        card.setCardNumber(request.getCardNumber());
        card.setAccountNumber(request.getAccountNumber());

        Card updatedCard = cardRepository.save(card);
        return cardMapper.toDto(updatedCard);
    }

    @Transactional
    public void changeCardStatus(Long id, String status){
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта с ID " + id + " не найден"));
        card.setStatus(status);
        cardRepository.save(card);
    }
    @Transactional
    public List<CardDto> getActiveCardsByCustomerId(Long customerId){
        return cardRepository.findByCustomerIdAndStatus(customerId, "ACTIVE").stream()
                .map(cardMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public CardDto getCardById(Long id){
        Card card = cardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Карта с ID" + id + "не найден"));
        return cardMapper.toDto(card);
    }
}
