package com.roast.linc.service;

import com.roast.linc.entity.Evidence;
import com.roast.linc.entity.LegalCase;
import com.roast.linc.repository.EvidenceRepository;
import com.roast.linc.repository.LegalCaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final LegalCaseRepository legalCaseRepository;

    public EvidenceService(EvidenceRepository evidenceRepository, LegalCaseRepository legalCaseRepository) {
        this.evidenceRepository = evidenceRepository;
        this.legalCaseRepository = legalCaseRepository;
    }

    /**
     * Toplu kanıt yüklemeleri için kullanılır (Cloudinary'den gelen URL listesini kaydeder)
     */
    @Transactional
    public void saveEvidences(LegalCase legalCase, List<String> fileUrls) {
        for (String url : fileUrls) {
            Evidence evidence = new Evidence();
            evidence.setFileUrl(url); // Cloudinary'den gelen tam URL buraya yazılır
            evidence.setLegalCase(legalCase);
            evidenceRepository.save(evidence);
        }
    }

    /**
     * Tekil fotoğraf yükleme ucu için kullanılır
     */
    @Transactional
    public void addEvidenceToCase(Long caseId, String cloudUrl) {
        // 1. Veritabanında davanın varlığını kontrol ediyoruz
        LegalCase legalCase = legalCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dava bulunamadı"));

        // 2. Yeni Evidence nesnesi oluşturuyoruz
        Evidence evidence = new Evidence();

        // ESKİ HALİ: evidence.setFileUrl("/images/" + fileName);
        // YENİ HALİ: Cloudinary'den gelen "https://res.cloudinary.com/..." linkini direkt kaydediyoruz
        evidence.setFileUrl(cloudUrl);

        evidence.setLegalCase(legalCase);

        // 3. Veritabanına kaydediyoruz
        evidenceRepository.save(evidence);
    }
}
