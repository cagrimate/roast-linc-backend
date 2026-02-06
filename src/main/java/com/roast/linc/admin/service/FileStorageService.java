package com.roast.linc.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    // Resimlerin kaydedileceği ana dizin
    private final Path rootLocation = Paths.get("uploads");

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) throw new RuntimeException("Boş dosya yüklenemez.");

            // Klasör yoksa oluştur
            if (!Files.exists(rootLocation)) Files.createDirectories(rootLocation);

            // Dosya adını benzersiz yap (Örn: 550e8400-e29b..._ekrangoruntusu.png)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

            return fileName; // DB'ye bu adı kaydedeceğiz
        } catch (IOException e) {
            throw new RuntimeException("Dosya kaydedilirken hata oluştu: " + e.getMessage());
        }
    }
}
