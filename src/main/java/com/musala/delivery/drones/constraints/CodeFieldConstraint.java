package com.musala.delivery.drones.constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CodeFieldValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeFieldConstraint {
    String message () default "Invalid field: it must only be composed of UPPERCASE letters, underscores and numbers";
    Class<?> [] groups() default {};
    //Class<? extends Payload> payload();
}
