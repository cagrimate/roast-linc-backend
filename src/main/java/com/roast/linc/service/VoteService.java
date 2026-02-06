package com.roast.linc.service;

import com.roast.linc.entity.LegalCase;
import com.roast.linc.entity.Vote;
import com.roast.linc.enums.CaseStatus;
import com.roast.linc.enums.VoteType;
import com.roast.linc.repository.LegalCaseRepository;
import com.roast.linc.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final LegalCaseRepository caseRepository;

    public VoteService(VoteRepository voteRepository, LegalCaseRepository caseRepository) {
        this.voteRepository = voteRepository;
        this.caseRepository = caseRepository;
    }

    public void castVote(Long caseId, String deviceId, VoteType voteType) {
        // 1. Aynı cihaz daha önce oy vermiş mi kontrolü
        Optional<Vote> existingVote = voteRepository.findByLegalCaseIdAndDeviceId(caseId, deviceId);
        Optional<LegalCase> caseAvailable = caseRepository.findByIdAndStatus(caseId, CaseStatus.ON_TRIAL);
        if(caseAvailable.isEmpty()) {
            throw new IllegalStateException("Bu davaya oy kullanamazsınız !");
        }

        if (existingVote.isPresent()) {
            throw new IllegalStateException("Bu davaya daha önce oy verdiniz!");
        }

        // 2. Dava var mı kontrolü
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dava bulunamadı."));

        // --- YENİ EKLENEN KONTROL ---
        // 2.1. Oylama süresi dolmuş mu veya dava pasif mi?
        if (!legalCase.isActive() || (legalCase.getExpiryDate() != null && legalCase.getExpiryDate().isBefore(LocalDateTime.now()))) {
            throw new IllegalStateException("Bu davanın oylama süresi dolmuştur!");
        }
        // ----------------------------

        // 3. Oyu kaydet
        Vote vote = new Vote();
        vote.setLegalCase(legalCase);
        vote.setDeviceId(deviceId);
        vote.setVoteType(voteType);

        voteRepository.save(vote);
    }

    // Enum değerlerini kullanarak istatistikleri ekrana yazma/dönme mantığı
    public void printCaseResults(Long caseId) {
        System.out.println("--- DAVA SONUÇLARI (ID: " + caseId + ") ---");

        for (VoteType type : VoteType.values()) {
            // Her bir Enum tipi için (GUILTY, CRINGE vb.) DB'deki sayıyı alıyoruz
            long count = voteRepository.countByLegalCaseIdAndVoteType(caseId, type.getId());

            // Enum içindeki 'name' alanını (String açıklamayı) ekrana basıyoruz
            System.out.println(type.getName() + " : " + count + " Oy");
        }
    }
}
