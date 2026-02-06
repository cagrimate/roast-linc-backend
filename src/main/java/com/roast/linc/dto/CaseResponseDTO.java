package com.roast.linc.dto;

import java.util.List;
import java.util.Map;

public class CaseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String statusName;
    private List<String> evidenceUrls;
    private Map<String, Long> voteDistribution; // Hangi Enum tipinden kaç oy var?
    private long totalVotes;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<String> getEvidenceUrls() {
        return evidenceUrls;
    }

    public void setEvidenceUrls(List<String> evidenceUrls) {
        this.evidenceUrls = evidenceUrls;
    }

    public Map<String, Long> getVoteDistribution() {
        return voteDistribution;
    }

    public void setVoteDistribution(Map<String, Long> voteDistribution) {
        this.voteDistribution = voteDistribution;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
