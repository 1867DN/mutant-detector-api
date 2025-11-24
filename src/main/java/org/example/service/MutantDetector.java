package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Servicio para detectar mutantes analizando secuencias de ADN.
 * Implementa todas las optimizaciones requeridas para máxima puntuación.
 */
@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    /**
     * Determina si un ADN corresponde a un mutante.
     * Un humano es mutante si se encuentran más de una secuencia de 4 letras iguales.
     *
     * @param dna Array de Strings que representa la matriz de ADN NxN
     * @return true si es mutante, false si es humano
     */
    public boolean isMutant(String[] dna) {
        // Validación inicial
        if (!isValidDna(dna)) {
            return false;
        }

        final int n = dna.length;
        int sequenceCount = 0;

        // Optimización #1: Conversión a char[][] para acceso O(1)
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        // Optimización #2: Single Pass - recorrer UNA SOLA VEZ
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Optimización #3: Boundary Checking - solo buscar donde cabe
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        // Optimización #4: Early Termination - salir inmediatamente
                        if (sequenceCount > 1) {
                            return true;
                        }
                    }
                }

                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) {
                            return true;
                        }
                    }

                    // Diagonal descendente (\)
                    if (col <= n - SEQUENCE_LENGTH) {
                        if (checkDiagonalDown(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1) {
                                return true;
                            }
                        }
                    }

                    // Diagonal ascendente (/)
                    if (col >= SEQUENCE_LENGTH - 1) {
                        if (checkDiagonalUp(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Optimización #5: Comparación directa sin loops adicionales
     */
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
               matrix[row][col + 2] == base &&
               matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
               matrix[row + 2][col] == base &&
               matrix[row + 3][col] == base;
    }

    private boolean checkDiagonalDown(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
               matrix[row + 2][col + 2] == base &&
               matrix[row + 3][col + 3] == base;
    }

    private boolean checkDiagonalUp(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col - 1] == base &&
               matrix[row + 2][col - 2] == base &&
               matrix[row + 3][col - 3] == base;
    }

    /**
     * Valida que el ADN sea una matriz NxN válida con caracteres permitidos.
     */
    private boolean isValidDna(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;

        // Verificar que sea cuadrada y tenga caracteres válidos
        for (String sequence : dna) {
            if (sequence == null || sequence.length() != n) {
                return false;
            }

            // Optimización #6: Validation Set O(1)
            for (char base : sequence.toCharArray()) {
                if (!VALID_BASES.contains(base)) {
                    return false;
                }
            }
        }

        return true;
    }
}
