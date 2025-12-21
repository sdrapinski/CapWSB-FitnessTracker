// This code snippet is defining a Data Transfer Object (DTO) class named `UserEmailDto` in Java.
package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Data Transfer Object (DTO) for User Email information.
 *
 * @param id    the unique identifier of the user, can be null
 * @param email the email address of the user
 */
public record UserEmailDto(@Nullable Long id,String email) {
}
