package com.roast.linc.repository;

import com.roast.linc.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Bir cihazın belirli bir davaya daha önce oy verip vermediğini kontrol etmek için
    Optional<Vote> findByLegalCaseIdAndDeviceId(Long caseId, String deviceId);

    // Belirli bir davanın toplam oy sayısını getirmek için
    long countByLegalCaseId(Long caseId);

    // Belirli bir davadaki belirli bir oy tipinin (örn: kaç kişi "Cringe" dedi) sayısını getirmek için
    long countByLegalCaseIdAndVoteType(Long caseId, int voteTypeId);
}
