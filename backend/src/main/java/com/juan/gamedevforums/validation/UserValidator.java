package com.juan.gamedevforums.validation;

import com.juan.gamedevforums.web.dto.UserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "message.username", "username is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "LastName is required.");
    }

}
