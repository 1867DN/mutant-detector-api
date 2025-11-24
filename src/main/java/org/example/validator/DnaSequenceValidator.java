package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

/**
 * Implementación del validador para secuencias de ADN.
 */
public class DnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');
    private static final int MIN_SIZE = 4;  // Tamaño mínimo para formar secuencias de 4

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;
        
        // Verificar tamaño mínimo 4x4
        if (n < MIN_SIZE) {
            return false;
        }

        // Verificar que sea matriz cuadrada NxN
        for (String sequence : dna) {
            if (sequence == null || sequence.length() != n) {
                return false;
            }

            // Verificar caracteres válidos
            for (char base : sequence.toCharArray()) {
                if (!VALID_BASES.contains(base)) {
                    return false;
                }
            }
        }

        return true;
    }
}
