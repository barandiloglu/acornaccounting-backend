// Updated AvailabilityService to include the getBookedTimes method
package backend.acornaccounting.backend.service;

import backend.acornaccounting.backend.model.Availability;
import backend.acornaccounting.backend.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public List<String> getAvailableTimes(String date, String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            // Parse the date with the correct format
            LocalDate requestedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            LocalDateTime now = LocalDateTime.now(zoneId);

            // Fetch booked times for the given date
            List<String> bookedTimes = availabilityRepository.findBookedTimesByDate(requestedDate);

            // Generate all time slots
            List<String> allTimeSlots = Arrays.asList(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00"
            );

            // Filter out booked and past times
            return allTimeSlots.stream()
                .filter(time -> !bookedTimes.contains(time) &&
                    (requestedDate.isAfter(now.toLocalDate()) ||
                    (requestedDate.isEqual(now.toLocalDate()) &&
                    LocalTime.parse(time).isAfter(now.toLocalTime()))))
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing availability", e);
        }
    }

    public List<String> getBookedTimes(LocalDate date) {
        try {
            // Fetch booked times from the repository for the given date
            return availabilityRepository.findBookedTimesByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching booked times", e);
        }
    }

    public void createAvailability(LocalDate date, String time, String status) {
        Availability availability = new Availability();
        availability.setDate(date);
        availability.setTime(time);
        availability.setStatus(status);
        availabilityRepository.save(availability);
    }
}
