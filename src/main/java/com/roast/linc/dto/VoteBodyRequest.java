package com.roast.linc.dto;

import com.roast.linc.enums.VoteType;

public class VoteBodyRequest {
    private String deviceId;
    private VoteType voteType; // Senin Enum'ın burada otomatik deserialized olacak

    // Getter ve Setterlar
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public VoteType getVoteType() { return voteType; }
    public void setVoteType(VoteType voteType) { this.voteType = voteType; }



}
