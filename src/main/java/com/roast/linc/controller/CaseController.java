package com.roast.linc.controller;

import com.roast.linc.admin.dto.CaseDetailResponse;
import com.roast.linc.admin.service.AdminService;
import com.roast.linc.dto.ApiResponse;
import com.roast.linc.dto.CaseCreateRequest;
import com.roast.linc.entity.LegalCase;
import com.roast.linc.service.CaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {
    private final CaseService caseService;
    private final AdminService adminService;

    public CaseController(CaseService caseService, AdminService adminService) {
        this.caseService = caseService;
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LegalCase>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<LegalCase> cases = caseService.getAllCasesPaged(page, size);
        return ResponseEntity.ok(ApiResponse.success(cases, "Davalar başarıyla getirildi."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegalCase> getById(@PathVariable Long id) {
        return ResponseEntity.ok(caseService.getCaseById(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<CaseDetailResponse>> getDetails(@PathVariable Long id) {
        CaseDetailResponse detail = caseService.getCaseDetail(id);
        return ResponseEntity.ok(ApiResponse.success(detail, "Dava detayları başarıyla getirildi."));
    }

    /*@PostMapping("/cases")
    public ResponseEntity<ApiResponse<Object>> createCase(@RequestBody CaseCreateRequest request) {
        // Burada adminService içindeki metodunu çağıracağız
        // Eğer adminService içinde böyle bir metod yoksa aşağıya basit halini ekliyorum
        Object newCase = adminService.saveNewCase(request);

        return ResponseEntity.ok(ApiResponse.success(newCase, "Yeni dava başarıyla oluşturuldu."));
    }

     */
}
