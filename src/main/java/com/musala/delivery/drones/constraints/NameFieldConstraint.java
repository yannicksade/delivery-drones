package com.musala.delivery.drones.constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameFieldConstraint {
    String message () default "Invalid code";
    Class<?> [] groups() default {};
    //Class<? extends Payload> payload();
}
