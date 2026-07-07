package bakaibank.PracticeWork2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limit_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "old_sum")
    private BigDecimal oldSum;

    @Column(name = "new_sum", nullable = false)
    private BigDecimal newSum;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "old_operations")
    private Integer oldOperations;

    @Column(name = "new_operations")
    private Integer newOperations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "limit_id", nullable = false)
    private Limit limit;
}