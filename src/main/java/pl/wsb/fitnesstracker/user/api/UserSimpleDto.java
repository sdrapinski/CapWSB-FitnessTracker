// This code snippet is defining a Data Transfer Object (DTO) class named `UserSimpleDto` in Java.
package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for User simple information.
 *
 * @param id       the unique identifier of the user, can be null
 * @param username the username of the user
 */
public record UserSimpleDto(@Nullable Long id, String username) {
}
