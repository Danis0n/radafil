package com.danis0n.validator;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ObjectValidator<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validate(T object) {
        Set<ConstraintViolation<T>> violations =
                validator.validate(object);

        if (!violations.isEmpty()) {
            Set<String> errorMessages = violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectAccessException(errorMessages.toString());
        }

    }

}
