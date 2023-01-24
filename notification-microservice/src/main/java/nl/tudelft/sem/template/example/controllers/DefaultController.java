package nl.tudelft.sem.template.example.controllers;

import nl.tudelft.sem.template.example.domain.Notification;
import nl.tudelft.sem.template.example.domain.NotificationRepository;
import nl.tudelft.sem.template.example.models.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import javax.transaction.Transactional;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class DefaultController {

    @Transient
    private final transient NotificationRepository notificationRepository;

    @Autowired
    public DefaultController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @PostMapping("/createNotification")
    public ResponseEntity<String> createNotification(@RequestBody NotificationModel request) {

        try{
            Notification newOne = new Notification(request.getNetId(), request.getMessage());
            notificationRepository.save(newOne);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Notification not created");
        }

        return ResponseEntity.ok("Notification created!");
    }

    @GetMapping("/getNotifications/{name}")
    @Transactional
    public ResponseEntity<Iterable<Notification>> getNotifications(@PathVariable(value = "name") String netId) {
        ResponseEntity<Iterable<Notification>> response = ResponseEntity.ok(notificationRepository.findAllByNetId(netId));
        notificationRepository.deleteAllByNetId(netId);

        return response;
    }

}
