package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Mutant Detector.
 * API REST para detectar mutantes mediante análisis de ADN.
 */
@SpringBootApplication
public class MutantDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);
    }
}
