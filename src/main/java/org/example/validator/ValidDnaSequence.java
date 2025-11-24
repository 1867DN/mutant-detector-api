package org.example.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación custom para validar secuencias de ADN.
 * Valida que sea matriz NxN con caracteres A, T, C, G únicamente.
 */
@Documented
@Constraint(validatedBy = DnaSequenceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {

    String message() default "Secuencia de ADN inválida. Debe ser una matriz NxN con caracteres A, T, C, G únicamente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
