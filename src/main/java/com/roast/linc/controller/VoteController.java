package com.roast.linc.controller;

import com.roast.linc.dto.ApiResponse;
import com.roast.linc.dto.VoteBodyRequest;

import com.roast.linc.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/votes") // "public" eklemek güvenlik ayarlarında kolaylık sağlar
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // Örnek istek: POST /api/v1/public/votes/5?deviceId=unique-uuid-123&typeId=1
    @PostMapping("/{caseId}")
    public ResponseEntity<ApiResponse<String>> castVote(
            @PathVariable Long caseId,
            @RequestBody VoteBodyRequest request) { // Veriyi Body'den alıyoruz

        // request.getVoteType() dediğin an senin Deserializer çoktan ID'yi Enum'a çevirmiş olacak.
        voteService.castVote(caseId, request.getDeviceId(), request.getVoteType());

        return ResponseEntity.ok(ApiResponse.success(null, "Oyunuz başarıyla kaydedildi."));
    }
}
