package com.roast.linc.service;

import com.roast.linc.admin.dto.CaseDetailResponse;
import com.roast.linc.entity.Evidence;
import com.roast.linc.entity.LegalCase;
import com.roast.linc.enums.CaseCategory;
import com.roast.linc.enums.CaseStatus;
import com.roast.linc.enums.VoteType;
import com.roast.linc.repository.LegalCaseRepository;
import com.roast.linc.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaseService {
    private final LegalCaseRepository caseRepository;
    private final EvidenceService evidenceService;
    private final VoteRepository voteRepository;

    public CaseService(LegalCaseRepository caseRepository, EvidenceService evidenceService, VoteRepository voteRepository) {
        this.caseRepository = caseRepository;
        this.evidenceService = evidenceService;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public LegalCase createCase(String title, String description, List<String> ssUrls, CaseCategory category) {
        // 1. Dava Nesnesini Oluştur
        LegalCase newCase = new LegalCase();
        newCase.setTitle(title);
        newCase.setDescription(description);
        newCase.setCategory(category);
        newCase.setStatus(CaseStatus.ON_TRIAL); // Başlar başlamaz oylamaya açılsın

        newCase.setCreatedAt(LocalDateTime.now());
        newCase.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24 saat sınırı burada konuyor
        newCase.setActive(true);

        // 2. Kaydet
        LegalCase savedCase = caseRepository.save(newCase);

        // 3. Kanıtları (SS) Kaydet
        if (ssUrls != null && !ssUrls.isEmpty()) {
            evidenceService.saveEvidences(savedCase, ssUrls);
        }

        return savedCase;
    }

    public CaseDetailResponse getCaseDetail(Long caseId) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dava bulunamadı"));

        CaseDetailResponse response = new CaseDetailResponse();
        response.setId(legalCase.getId());
        response.setTitle(legalCase.getTitle());
        response.setDescription(legalCase.getDescription());
        response.setEvidenceUrls(legalCase.getEvidences().stream()
                .map(Evidence::getFileUrl)
                .collect(Collectors.toList()));

        // İstatistikleri Hesapla
        Map<String, Long> distribution = new HashMap<>();
        Map<String, Double> percentages = new HashMap<>();
        long totalVotes = voteRepository.countByLegalCaseId(caseId);
        response.setTotalVotes(totalVotes);

        for (VoteType type : VoteType.values()) {
            long count = voteRepository.countByLegalCaseIdAndVoteType(caseId, type.getId());
            distribution.put(type.getName(), count);

            double percent = (totalVotes == 0) ? 0 : (double) count / totalVotes * 100;
            percentages.put(type.getName(), Math.round(percent * 100.0) / 100.0); // Virgülden sonra 2 basamak
        }

        response.setVoteDistribution(distribution);
        response.setVotePercentages(percentages);

        return response;
    }

    // Tüm davaları listelemek için (Entity döner)
    public Page<LegalCase> getAllCasesPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return caseRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // Tek bir davayı Entity olarak dönmek için (Controller'daki getById için)
    public LegalCase getCaseById(Long id) {
        return caseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dava bulunamadı: " + id));
    }
}
