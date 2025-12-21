// This code snippet is defining a Java interface named `UserRepository` that extends `JpaRepository`
// from Spring Data JPA. The interface includes two methods for querying user data from a database:
package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Query searching users born before the specified date.
     *
     * @param date the date to compare birthdates against
     * @return List of users born before the specified date
     */

    default List<User> findByBirthdateBefore(LocalDate date) {
        return findAll().stream()
                .filter(user -> user.getBirthdate() != null && user.getBirthdate().isBefore(date))
                .toList();
    }


}
