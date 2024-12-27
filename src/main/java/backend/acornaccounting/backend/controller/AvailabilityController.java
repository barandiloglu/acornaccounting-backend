package backend.acornaccounting.backend.controller;

import backend.acornaccounting.backend.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    /**
     * Fetch available times for a specific date and timezone.
     *
     * @param date     The date to check availability (format: yyyy-MM-dd).
     * @param timezone The timezone to consider (e.g., "America/Toronto").
     * @return A list of available times or an error message.
     */
    @GetMapping
    public ResponseEntity<?> getAvailableTimes(
            @RequestParam("date") String date,
            @RequestParam("timezone") String timezone) {

        try {
            List<String> availableTimes = availabilityService.getAvailableTimes(date, timezone);
            return ResponseEntity.ok(availableTimes);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return ResponseEntity.status(500).body("Error fetching available times: " + e.getMessage());
        }
    }

    /**
     * Create or update an availability record.
     *
     * @param date   The date for the availability (format: MM-dd-yyyy).
     * @param time   The time for the availability (e.g., "09:00").
     * @param status The status of the time slot (e.g., "available", "booked").
     * @return A success or error message.
     */
    @PostMapping
    public ResponseEntity<?> createAvailability(
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("status") String status) {

        try {
            availabilityService.createAvailability(
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    time,
                    status
            );
            return ResponseEntity.ok("Availability created successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return ResponseEntity.status(500).body("Error creating availability: " + e.getMessage());
        }
    }
}
