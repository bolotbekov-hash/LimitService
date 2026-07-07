package bakaibank.PracticeWork2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "limits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "default_sum")
    private BigDecimal defaultSum;

    @Column(name = "default_operation_count")
    private Integer defaultOperationCount;

    @Column(name = "max_sum")
    private BigDecimal maxSum;

    @Column(name = "max_operation_count")
    private Integer maxOperationCount;
}