package br.com.alunoonline.api.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements
        ConstraintValidator<EmailValidation, String> {

    private String message;
    private static final String requiredDomain = "@fuji.com";

    @Override
    public void initialize(EmailValidation constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.contains(requiredDomain)) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(message + email + " não pertence ao domínio " + requiredDomain)
                .addConstraintViolation();
        return false;
    }

}
