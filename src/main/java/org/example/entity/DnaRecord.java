package org.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un registro de ADN analizado.
 * Usa hash SHA-256 para evitar duplicados.
 */
@Entity
@Table(name = "dna_records")
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Hash SHA-256 del ADN para evitar duplicados.
     * Marcado como único para garantizar integridad.
     */
    @Column(name = "dna_hash", nullable = false, unique = true, length = 64)
    private String dnaHash;

    /**
     * Indica si el ADN corresponde a un mutante.
     */
    @Column(name = "is_mutant", nullable = false)
    private boolean isMutant;

    /**
     * Fecha y hora de creación del registro.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructores
    public DnaRecord() {
    }

    public DnaRecord(Long id, String dnaHash, boolean isMutant, LocalDateTime createdAt) {
        this.id = id;
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDnaHash() {
        return dnaHash;
    }

    public void setDnaHash(String dnaHash) {
        this.dnaHash = dnaHash;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
