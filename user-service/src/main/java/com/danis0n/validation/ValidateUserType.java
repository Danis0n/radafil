package com.danis0n.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserTypeValidator.class)
public @interface ValidateUserType {

    String message() default "Invalid userType: Is should be either lala Or local";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
