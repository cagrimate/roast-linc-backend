package com.roast.linc.admin.dto;

import java.util.List;
import java.util.Map;

public class CaseDetailResponse {
    private Long id;
    private String title;
    private String description;
    private String categoryName;
    private String statusName;
    private List<String> evidenceUrls; // SS linkleri
    private long totalVotes;
    private Map<String, Long> voteDistribution; // Hangi Enum'dan kaç tane?
    private Map<String, Double> votePercentages; // Yüzdesel dağılım

    // Constructor, Getter ve Setterlar (Lombok kullanmadığımız için manuel)
    public CaseDetailResponse() {}

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Map<String, Long> getVoteDistribution() {
        return voteDistribution;
    }

    public void setVoteDistribution(Map<String, Long> voteDistribution) {
        this.voteDistribution = voteDistribution;
    }

    public Map<String, Double> getVotePercentages() {
        return votePercentages;
    }

    public void setVotePercentages(Map<String, Double> votePercentages) {
        this.votePercentages = votePercentages;
    }
}
