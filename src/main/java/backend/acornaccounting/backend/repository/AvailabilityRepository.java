package backend.acornaccounting.backend.repository;

import backend.acornaccounting.backend.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT a.time FROM Availability a WHERE a.date = :date AND a.status = 'BOOKED'")
    List<String> findBookedTimesByDate(LocalDate date);
}
