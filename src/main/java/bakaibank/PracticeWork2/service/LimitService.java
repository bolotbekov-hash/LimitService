package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.repository.LimitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;

    @Transactional
    public Limit createLimit(Limit limit){
        return limitRepository.save(limit);
    }

    @Transactional
    public Limit updateLimit(Long id, Limit limitDetails){
        Limit limit = limitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тип лимита с ID " + id + " не найден"));

        limit.setType(limitDetails.getType());
        limit.setDefaultSum(limitDetails.getDefaultSum());
        limit.setDefaultOperationCount(limitDetails.getDefaultOperationCount());
        limit.setMaxSum(limitDetails.getMaxSum());
        limit.setMaxOperationCount(limitDetails.getMaxOperationCount());

        return limitRepository.save(limit);
    }
    @Transactional
    public List<Limit> getAllActiveLimits(){
        return limitRepository.findAll();
    }

    public List<Limit> getAllLimits() {
        return limitRepository.findAll();
    }
}
