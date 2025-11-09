package pl.wsb.fitnesstracker.statistics.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.user.api.User;

@Entity
@Table(name = "Statistics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "totalTrainings", nullable = false)
    private int totalTrainings;

    @Column(name = "totalDistance")
    private double totalDistance;

    @Column(name = "totalCaloriesBurned")
    private int totalCaloriesBurned;


    public Statistics(int totalTrainings, double totalDistance, int totalCaloriesBurned) {
        this.totalTrainings = totalTrainings;
        this.totalDistance = totalDistance;
        this.totalCaloriesBurned = totalCaloriesBurned;

    }
}