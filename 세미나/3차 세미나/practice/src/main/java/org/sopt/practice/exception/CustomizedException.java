package org.sopt.practice.exception;

import org.sopt.practice.common.dto.ErrorMessage;

public class CustomizedException extends BusinessException {
    public CustomizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}