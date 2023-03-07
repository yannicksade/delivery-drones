package com.musala.delivery.drones.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public abstract class CodeFieldValidator implements ConstraintValidator<CodeFieldConstraint, String> {

    @Override
    public void initialize(CodeFieldConstraint codeFieldConstraint){
    }
    @Override
    public boolean isValid(String codeField, ConstraintValidatorContext context) {
        return !codeField.isBlank() && codeField.matches("[A-Z_0-9]+");
    }
}
