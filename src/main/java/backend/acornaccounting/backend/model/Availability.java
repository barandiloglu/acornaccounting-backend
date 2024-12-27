package backend.acornaccounting.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date; // The appointment date

    @Column(name = "time", nullable = false)
    private String time; // The appointment time (e.g., 09:00)

    @Column(name = "status", nullable = false)
    private String status; // BOOKED or AVAILABLE
}
