package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class WorkoutSessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(WorkoutSession workoutSession) {
        entityManager.persist(workoutSession);
    }

    public void saveSql(WorkoutSession workoutSession) {
        String sql = "INSERT INTO Workout_Session (training_id, timestamp, startLatitude, startLongitude, endLatitude, endLongitude, altitude) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        entityManager.createNativeQuery(sql)
                .setParameter(1, workoutSession.getTrainingId().getId())
                .setParameter(2, workoutSession.getTimestamp())
                .setParameter(3, workoutSession.getStartLatitude())
                .setParameter(4, workoutSession.getStartLongitude())
                .setParameter(5, workoutSession.getEndLatitude())
                .setParameter(6, workoutSession.getEndLongitude())
                .setParameter(7, workoutSession.getAltitude())
                .executeUpdate();
    }
}
