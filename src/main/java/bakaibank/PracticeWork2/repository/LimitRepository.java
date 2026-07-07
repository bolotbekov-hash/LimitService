package bakaibank.PracticeWork2.repository;

import bakaibank.PracticeWork2.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByType(String type);
}
