package bakaibank.PracticeWork2.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "card_limit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "limit_id", nullable = false)
    private Limit limit;

    @Column(name = "current_sum", nullable = false)
    private BigDecimal currentSum;

    @Column(name = "operations_count")
    private Integer operationsCount;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
}
