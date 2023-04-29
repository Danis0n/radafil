package com.danis0n.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserTypeValidator implements ConstraintValidator<ValidateUserType, String> {

    @Override
    public boolean isValid(String userType, ConstraintValidatorContext constraintValidatorContext) {
        List<String> userTypes = Arrays.asList("lala", "local");
        return userTypes.contains(userType);
    }
}
