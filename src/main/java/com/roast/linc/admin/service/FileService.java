package com.roast.linc.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {
    // Görsellerin kaydedileceği klasör (application.yml'den çekilebilir)
    private final String uploadDir = "uploads/evidences/";

    public String saveFile(MultipartFile file) throws IOException {
        // Dosya adını benzersiz yapalım (UUID)
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path copyLocation = Paths.get(uploadDir + fileName);

        // Klasör yoksa oluştur
        Files.createDirectories(Paths.get(uploadDir));

        // Dosyayı kopyala
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName; // DB'ye kaydedilecek dosya adı
    }
}
