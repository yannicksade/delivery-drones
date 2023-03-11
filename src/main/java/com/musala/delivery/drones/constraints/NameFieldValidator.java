package com.musala.delivery.drones.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameFieldValidator implements ConstraintValidator<NameFieldConstraint, String> {

    @Override
    public void initialize(NameFieldConstraint nameFieldConstraint){
    }
    @Override
    public boolean isValid(String nameField, ConstraintValidatorContext context) {
        return nameField != null && nameField.matches("^[A-Za-z0-9-_]+$");
    }
}
