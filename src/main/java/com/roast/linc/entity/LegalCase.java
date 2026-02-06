package com.roast.linc.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roast.linc.enums.CaseCategory;
import com.roast.linc.enums.CaseStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cases")
public class LegalCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private CaseStatus status; // Enum Tipinde

    private CaseCategory category; // Enum Tipinde

    // Bir davanın birden fazla Kanıtı (SS) olabilir
    @OneToMany(mappedBy = "legalCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evidence> evidences;

    // Bir davanın birden fazla Oyu olabilir
    @OneToMany(mappedBy = "legalCase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vote> votes;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiryDate; // Bitiş zamanı
    private boolean active = true;    // Oylama açık mı?


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public CaseCategory getCategory() {
        return category;
    }

    public void setCategory(CaseCategory category) {
        this.category = category;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
