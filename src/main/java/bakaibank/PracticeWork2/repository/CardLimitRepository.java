package bakaibank.PracticeWork2.repository;

import bakaibank.PracticeWork2.entity.CardLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardLimitRepository extends JpaRepository<CardLimit, Long> {
    List<CardLimit> findByCardId(Long cardId);

    Optional<CardLimit> findByCardIdAndLimitId(Long cardId, Long limitId);
}
