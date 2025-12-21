/**
 * The UserController class in Java is responsible for handling HTTP requests related to user
 * operations, providing endpoints for retrieving, creating, updating, and deleting users.
 */
package pl.wsb.fitnesstracker.user.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.util.List;
import java.util.Optional;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves a list of all users in a simplified format.
     *
     * @return A list of UserSimpleDto representing all users.
     */
    @GetMapping
    public List<UserSimpleDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The ID of the user to retrieve.
     * @return An Optional containing the User if found, or empty if not found.
     */
    @GetMapping("/{userId}")
    public Optional<User> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    /**
     * Creates a new user based on the provided UserDto.
     *
     * @param userDto The data transfer object containing user information.
     * @return A ResponseEntity containing the created User and HTTP status code.
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        User userEntity = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    /**
     * Deletes a user by their unique identifier.
     *
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity with no content and HTTP status code 204.
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches for users by a fragment of their email address.
     *
     * @param emailFragment The fragment of the email to search for.
     * @return A list of UserEmailDto representing users whose email matches the fragment.
     */
    @GetMapping("/search/email")
    public List<UserEmailDto> searchUsersByEmail(@RequestParam String emailFragment) {

        var user = userService.getUserByEmail(emailFragment);
        if(user.isEmpty()){
            return List.of();
        }else{
            UserEmailDto userEntity = userMapper.toEmailDto(user.get());
            return List.of(userEntity);
        }
    }
    /**
     * Finds users older than a specified age.
     *
     * @param age The age threshold.
     * @return A list of UserSimpleDto representing users older than the specified age.
     */

    @GetMapping("/search/age")
    public List<UserSimpleDto> findUsersOlderThan(@RequestParam int age) {
        List<User> users = userService.findUsersOlderThan(age);
        return users.stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }
    /**
     * Updates an existing user with the provided information.
     *
     * @param userId  The ID of the user to update.
     * @param userDto The data transfer object containing updated user information.
     * @return A ResponseEntity containing the updated User or a not found status.
     */

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        try {
            User userUpdates = userMapper.toEntity(userDto);
            User updatedUser = userService.updateUser(userId, userUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}


