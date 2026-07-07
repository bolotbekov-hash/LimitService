package bakaibank.PracticeWork2.repository;

import bakaibank.PracticeWork2.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCustomerIdAndStatus(Long customerId, String status);
}
