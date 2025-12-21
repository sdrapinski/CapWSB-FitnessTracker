/**
 * The `UserMapper` class in Java provides methods to convert User entities to different types of User
 * DTOs and vice versa.
 */
package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

@Component
class UserMapper {

    /** Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /** Converts a User entity to a UserSimpleDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserSimpleDto
     */
    UserSimpleDto toSimpleDto(User user) {
        String username = user.getFirstName() + " " + user.getLastName();
        return new UserSimpleDto(user.getId(), username);
    }
    /** Converts a User entity to a UserEmailDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserEmailDto
     */

    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto (user.getId(), user.getEmail());
    }
    /** Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto to convert
     * @return the corresponding User entity
     */

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );
    }
}
