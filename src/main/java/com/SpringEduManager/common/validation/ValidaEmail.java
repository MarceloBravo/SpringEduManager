package com.SpringEduManager.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class) // Vincula con la lógica

public @interface ValidaEmail {
    String message() default "El formato del email no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
