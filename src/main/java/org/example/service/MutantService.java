package org.example.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para gestión de análisis de ADN mutante.
 * Incluye persistencia con estrategia de hash para deduplicación.
 */
@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Analiza un ADN y persiste el resultado.
     * Usa hash SHA-256 para evitar duplicados.
     *
     * @param dna Array de Strings con la secuencia de ADN
     * @return true si es mutante, false si es humano
     */
    @Transactional
    public boolean analyzeDna(String[] dna) {
        // Calcular hash del ADN
        String dnaHash = calculateDnaHash(dna);

        // Verificar si ya fue analizado (deduplicación)
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        // Analizar con el algoritmo optimizado
        boolean isMutant = mutantDetector.isMutant(dna);

        // Persistir resultado
        DnaRecord record = new DnaRecord();
        record.setDnaHash(dnaHash);
        record.setMutant(isMutant);
        dnaRecordRepository.save(record);

        return isMutant;
    }

    /**
     * Calcula hash SHA-256 de una secuencia de ADN.
     * Estrategia de deduplicación O(1).
     *
     * @param dna Array de Strings con la secuencia de ADN
     * @return Hash SHA-256 en formato hexadecimal
     */
    private String calculateDnaHash(String[] dna) {
        try {
            // Normalizar entrada concatenando las secuencias
            String dnaString = String.join("", Arrays.asList(dna));

            // Calcular SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculando hash SHA-256", e);
        }
    }
}
