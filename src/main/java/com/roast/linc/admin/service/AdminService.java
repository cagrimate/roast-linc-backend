package com.roast.linc.admin.service;

import com.roast.linc.dto.CaseCreateRequest;
import com.roast.linc.entity.LegalCase;
import com.roast.linc.enums.CaseStatus;
import com.roast.linc.repository.LegalCaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private final LegalCaseRepository caseRepository;

    public AdminService(LegalCaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Transactional
    public void approveCase(Long caseId) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dava bulunamadı"));
        legalCase.setStatus(CaseStatus.ON_TRIAL); // Oylamaya aç
        caseRepository.save(legalCase);
    }

    @Transactional
    public void deleteCase(Long caseId) {
        caseRepository.deleteById(caseId);
    }

    public LegalCase saveNewCase(CaseCreateRequest request) {
        // 1. Yeni bir Entity (LegalCase) oluşturuyoruz
        LegalCase legalCase = new LegalCase();

        // 2. DTO'dan gelenleri set ediyoruz
        legalCase.setTitle(request.getTitle());
        legalCase.setDescription(request.getDescription());
        legalCase.setCategory(request.getCaseCategory());

        // 3. Bizim tarafımızda olması gerekenleri set ediyoruz
        legalCase.setCreatedAt(LocalDateTime.now());
        legalCase.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24 saat kuralı
        legalCase.setActive(true);
        legalCase.setStatus(CaseStatus.WAITING); // Başlangıç durumu

        // 4. Veritabanına kaydediyoruz
        return caseRepository.save(legalCase);
    }
}
