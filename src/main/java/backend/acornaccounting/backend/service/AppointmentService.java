package backend.acornaccounting.backend.service;

import backend.acornaccounting.backend.dto.AppointmentRequest;
import backend.acornaccounting.backend.dto.AppointmentResponse;
import backend.acornaccounting.backend.model.Appointment;
import backend.acornaccounting.backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private AvailabilityService availabilityService;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setFirstName(request.getFirstName());
        appointment.setLastName(request.getLastName());
        appointment.setEmail(request.getEmail());
        appointment.setPhone(request.getPhone());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(request.getStatus() != null ? request.getStatus() : false);

        // Save appointment
        Appointment savedAppointment = repository.save(appointment);

        // Update availability table
        availabilityService.createAvailability(
            request.getAppointmentDate(),
            request.getAppointmentTime().toString(),
            "BOOKED"
        );

        return mapToResponse(savedAppointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setFirstName(appointment.getFirstName());
        response.setLastName(appointment.getLastName());
        response.setEmail(appointment.getEmail());
        response.setPhone(appointment.getPhone());
        response.setAppointmentDate(appointment.getAppointmentDate().toString());
        response.setAppointmentTime(appointment.getAppointmentTime().toString());
        return response;
    }
}
