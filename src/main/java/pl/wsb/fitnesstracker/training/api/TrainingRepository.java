package pl.wsb.fitnesstracker.training.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    default List<Training> getTrainingsByUserId(Long userId) {
        return findAll().stream()
                .filter(training -> training.getUser() != null && training.getUser().getId().equals(userId))
                .toList();

    }
}
