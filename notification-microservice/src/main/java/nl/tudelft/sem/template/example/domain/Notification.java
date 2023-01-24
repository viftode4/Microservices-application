package nl.tudelft.sem.template.example.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "net_id", nullable = false)
    private String netId;

    @Column(name = "message",  nullable = false)
    private String message;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    //constructor without id
    public Notification(String netId, String message) {
        this.netId = netId;
        this.message = message;
        this.time = new Timestamp(System.currentTimeMillis());
    }
}
