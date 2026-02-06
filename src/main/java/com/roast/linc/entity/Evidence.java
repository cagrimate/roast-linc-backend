package com.roast.linc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "evidences")
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileUrl; // SS'in bulunduğu path veya S3 linki

    private String description; // Fotoğrafın altına eklenecek kısa not

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    @JsonBackReference
    private LegalCase legalCase; // Hangi davaya ait olduğu

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LegalCase getLegalCase() {
        return legalCase;
    }

    public void setLegalCase(LegalCase legalCase) {
        this.legalCase = legalCase;
    }
}
