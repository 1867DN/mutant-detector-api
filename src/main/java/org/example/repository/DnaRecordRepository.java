package org.example.repository;

import java.util.Optional;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para acceso a datos de ADN.
 */
@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    /**
     * Busca un registro de ADN por su hash SHA-256.
     * Permite evitar duplicados y búsqueda O(1).
     *
     * @param dnaHash Hash SHA-256 del ADN
     * @return Optional con el registro si existe
     */
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    /**
     * Cuenta la cantidad de registros de mutantes o humanos.
     *
     * @param isMutant true para contar mutantes, false para humanos
     * @return cantidad de registros
     */
    long countByIsMutant(boolean isMutant);
}
