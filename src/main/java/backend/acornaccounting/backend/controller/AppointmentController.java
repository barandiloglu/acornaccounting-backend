package backend.acornaccounting.backend.controller;

import backend.acornaccounting.backend.dto.AppointmentRequest;
import backend.acornaccounting.backend.dto.AppointmentResponse;
import backend.acornaccounting.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse response = service.createAppointment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> appointments = service.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
}
