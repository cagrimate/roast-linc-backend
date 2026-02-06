package com.roast.linc.dto;

import java.util.Map;

public class VoteResultDTO {
    private Long caseId;
    private String title;
    private Map<String, Long> votes; // "Cringe": 450, "Suçlu": 120 gibi

    public VoteResultDTO(Long caseId, String title, Map<String, Long> votes) {
        this.caseId = caseId;
        this.title = title;
        this.votes = votes;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Long> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, Long> votes) {
        this.votes = votes;
    }
}
