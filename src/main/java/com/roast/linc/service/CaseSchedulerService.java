package com.roast.linc.service;

import com.roast.linc.entity.LegalCase;
import com.roast.linc.repository.LegalCaseRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseSchedulerService {

    private final LegalCaseRepository caseRepository;

    public CaseSchedulerService(LegalCaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    // Her dakika çalışır
    @Scheduled(fixedRate = 60000)
    public void closeExpiredCases() {
        LocalDateTime now = LocalDateTime.now();
        List<LegalCase> expiredCases = caseRepository.findByExpiryDateBeforeAndActiveTrue(now);

        for (LegalCase c : expiredCases) {
            c.setActive(false);
            // Burada istersen "Dava Sonuçlandı" gibi bir log atabilirsin
        }
        caseRepository.saveAll(expiredCases);
    }
}
