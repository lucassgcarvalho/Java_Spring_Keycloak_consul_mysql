package com.essential.app.validation.functional;

import java.util.Optional;

public record Invalid(String reason) implements ValidationResult {

    public boolean isValid() {
        return false;
    }

    public Optional<String> getReason() {
        return Optional.of(reason);
    }
}