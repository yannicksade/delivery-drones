package com.musala.delivery.drones.constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameFieldConstraint {
    String message () default "Invalid field: it must only be composed of letters, numbers, hiphen and/or underscore";
    Class<?> [] groups() default {};
    //Class<? extends Payload> payload();
}
