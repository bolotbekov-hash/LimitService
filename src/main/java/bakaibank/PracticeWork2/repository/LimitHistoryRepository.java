package bakaibank.PracticeWork2.repository;

import bakaibank.PracticeWork2.entity.LimitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LimitHistoryRepository extends JpaRepository<LimitHistory, Long> {
    List<LimitHistory> findByCardIdOrderByChangedAtDesc(Long cardId);
}
