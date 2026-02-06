package com.roast.linc.admin.controller;

import com.roast.linc.admin.config.JwtUtil;
import com.roast.linc.admin.dto.AuthRequest;
import com.roast.linc.admin.dto.AuthResponse;
import com.roast.linc.admin.dto.RegisterRequest;
import com.roast.linc.admin.entity.AdminUser;
import com.roast.linc.admin.repository.AdminRepository;
import com.roast.linc.admin.service.AdminDetailService;
import com.roast.linc.admin.service.AdminService;
import com.roast.linc.admin.service.CloudinaryService;
import com.roast.linc.admin.service.FileStorageService;
import com.roast.linc.dto.ApiResponse;
import com.roast.linc.dto.CaseCreateRequest;
import com.roast.linc.entity.LegalCase;
import com.roast.linc.service.EvidenceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    private final AdminDetailService adminDetailService;
    private final AuthenticationManager authenticationManager; // Bunu ekledik
    private final EvidenceService evidenceService;
    private final CloudinaryService cloudinaryService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(AdminService adminService, JwtUtil jwtUtil, AdminDetailService adminDetailService, AuthenticationManager authenticationManager, EvidenceService evidenceService, CloudinaryService cloudinaryService, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
        this.adminDetailService = adminDetailService;
        this.authenticationManager = authenticationManager;
        this.evidenceService = evidenceService;
        this.cloudinaryService = cloudinaryService;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Burada Spring Security'nin AuthenticationManager'ı ile doğrulama yapıyoruz
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Hatalı kullanıcı adı veya şifre", e);
        }

        final UserDetails userDetails = adminDetailService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        // 1. Kullanıcı adı kontrolü
        if (adminRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Bu kullanıcı adı zaten mevcut!"));
        }

        // 2. Yeni AdminUser oluşturma
        AdminUser newAdmin = new AdminUser();
        newAdmin.setUsername(request.getUsername());

        // 3. Şifreyi BCrypt ile hash'liyoruz (Kritik nokta burası!)
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newAdmin.setPassword(encodedPassword);

        // 4. Veritabanına kaydet
        adminRepository.save(newAdmin);

        return ResponseEntity.ok(ApiResponse.success(null, "Admin başarıyla kaydedildi. Şimdi login olabilirsiniz."));
    }

    // Dava onaylama ucu
    @PostMapping("/cases/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        adminService.approveCase(id);
        return ResponseEntity.ok("Dava başarıyla oylamaya açıldı.");
    }

    // Dava silme ucu
    @DeleteMapping("/cases/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.deleteCase(id);
        return ResponseEntity.ok("Dava ve bağlı tüm veriler silindi.");
    }

    @PostMapping(value = "/cases/{id}/upload-ss", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadEvidence(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        // 1. Buluta fırlat, linki kap
        String cloudUrl = cloudinaryService.uploadFile(file);

        // 2. O linki al, DB'ye hangi dava (id) ise onun altına yaz
        evidenceService.addEvidenceToCase(id, cloudUrl);

        return ResponseEntity.ok("Başarılı! Görsel buluta yüklendi ve DB'ye kaydedildi: " + cloudUrl);
    }

    @PostMapping("/cases")
    public ResponseEntity<ApiResponse<LegalCase>> createCase(@RequestBody CaseCreateRequest request) {
        // 1. Controller içinde set işlemi yapmıyoruz,
        // Tüm mantığı (tarih setleme vb.) servis katmanına devrediyoruz.

        // 2. Servis üzerinden kaydediyoruz
        LegalCase savedCase = adminService.saveNewCase(request);

        return ResponseEntity.ok(ApiResponse.success(savedCase, "Dava başarıyla oluşturuldu."));
    }


}
