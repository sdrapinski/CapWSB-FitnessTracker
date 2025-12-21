/**
 * The `UserServiceImpl` class implements `UserService` and `UserProvider` interfaces to provide
 * user-related functionalities using a `UserRepository`.
 */
package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> findUsersOlderThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findByBirthdateBefore(cutoffDate);
    }

    public User updateUser(Long id, User userWithUpdates) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userWithUpdates.getFirstName());
                    existingUser.setLastName(userWithUpdates.getLastName());
                    existingUser.setBirthdate(userWithUpdates.getBirthdate());
                    existingUser.setEmail(userWithUpdates.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }




}