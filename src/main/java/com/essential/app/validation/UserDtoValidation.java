package com.essential.app.validation;

import com.essential.app.resource.dto.UserDto;
import com.essential.app.validation.functional.ValidationResult;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.essential.app.validation.functional.ValidationResult.invalid;
import static com.essential.app.validation.functional.ValidationResult.valid;

public interface UserDtoValidation extends Function<UserDto, List<ValidationResult>> {

    static UserDtoValidation nameIsNotEmpty() {
        return execution(user -> !user.getName().trim().isEmpty(), "Name is empty.");
    }

    static UserDtoValidation eMailContainsAtSign() {
        return execution(userDto -> userDto.getEmail().contains("@"), "Missing @-sign.");
    }

    static UserDtoValidation execution(Predicate<UserDto> p, String message){
        return userDto -> p.test(userDto) ? List.of(valid()) : List.of(invalid(message));
    }

    default UserDtoValidation and(UserDtoValidation other) {
        return user -> List.of(this.apply(user).stream().findFirst().get(), other.apply(user).stream().findFirst().get());
    }
}
