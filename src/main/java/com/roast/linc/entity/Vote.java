package com.roast.linc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roast.linc.enums.VoteType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    @JsonBackReference // Swagger ve JSON dönüşümü bu alanı takip etmesin
    private LegalCase legalCase;

    private String deviceId; // Kim oy verdi? (Anonim)

    private VoteType voteType; // Enum Tipinde (Sayısal saklanır)

    private LocalDateTime votedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LegalCase getLegalCase() {
        return legalCase;
    }

    public void setLegalCase(LegalCase legalCase) {
        this.legalCase = legalCase;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }
}
