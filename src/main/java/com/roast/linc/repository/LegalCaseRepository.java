package com.roast.linc.repository;

import com.roast.linc.entity.LegalCase;
import com.roast.linc.enums.CaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LegalCaseRepository extends JpaRepository<LegalCase, Long> {
    // Sadece aktif olan (oylamaya açık) davaları listelemek için
    List<LegalCase> findByStatus(CaseStatus status);

    // En yeni davaları en üstte getirmek için
    Page<LegalCase> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<LegalCase> findByExpiryDateBeforeAndActiveTrue(LocalDateTime dateTime);

    Optional<LegalCase> findByIdAndStatus(Long id, CaseStatus status);
}
