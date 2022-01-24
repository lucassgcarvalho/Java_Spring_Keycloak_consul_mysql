package com.essential.app.validation;

import com.essential.app.model.User;
import com.essential.app.validation.functional.ValidationResult;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.essential.app.validation.functional.ValidationResult.invalid;
import static com.essential.app.validation.functional.ValidationResult.valid;

public interface UserValidation extends Function<User, List<ValidationResult>> {

    static UserValidation nameIsNotEmpty() {
        return execution(user -> !user.getName().trim().isEmpty(), "Name is empty.");
    }

    static UserValidation eMailContainsAtSign() {
        return execution(user -> user.getEmail().contains("@"), "Missing @-sign.");
    }

    static UserValidation execution(Predicate<User> p, String message){
        return user -> p.test(user) ? List.of(valid()) : List.of(invalid(message));
    }

    default UserValidation and(UserValidation other) {
        return user -> List.of(this.apply(user).stream().findFirst().get(), other.apply(user).stream().findFirst().get());
    }
}
