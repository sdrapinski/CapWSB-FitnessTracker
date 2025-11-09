package pl.wsb.fitnesstracker.healthmetrics.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "weight")
    private double weight;

    @Column(name = "height")
    private double height;

    @Column(name = "heartRate")
    private int heartRate;


    public HealthMetrics(Date date, double weight, double height, int heartRate) {
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.heartRate = heartRate;
    }
}
