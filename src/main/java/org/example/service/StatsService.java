package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para obtener estadísticas de verificaciones de ADN.
 */
@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Obtiene las estadísticas de verificaciones de ADN.
     *
     * @return StatsResponse con contadores y ratio
     */
    public StatsResponse getStats() {
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);
        double ratio = countHuman == 0 ? 0.0 : (double) countMutant / countHuman;

        return new StatsResponse(countMutant, countHuman, ratio);
    }
}
