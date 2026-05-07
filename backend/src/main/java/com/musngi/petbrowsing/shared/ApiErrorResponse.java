package com.musngi.petbrowsing.shared;

import java.util.List;

public record ApiErrorResponse(
        String code,
        String message,
        List<FieldValidationError> errors
) {

    public record FieldValidationError(String field, String message) {
    }
}

