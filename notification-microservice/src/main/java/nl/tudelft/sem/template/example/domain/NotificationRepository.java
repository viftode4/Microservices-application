package nl.tudelft.sem.template.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    //get all entries with netId
    Iterable<Notification> findAllByNetId(String netId);

    //delete all entries with netId
    void deleteAllByNetId(String netId);
}
