package com.roast.linc.repository;

import com.roast.linc.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    List<Evidence> findByLegalCaseId(Long caseId);

}
