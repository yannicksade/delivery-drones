package com.musala.delivery.drones.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public abstract class NameFieldValidator implements ConstraintValidator<NameFieldConstraint, String> {

    @Override
    public void initialize(NameFieldConstraint nameFieldConstraint){

    }
    @Override
    public boolean isValid(String nameField, ConstraintValidatorContext context) {
        return !nameField.isBlank() && nameField.matches("^[A-Za-z0-9-_]+$");
    }
}
