package com.example.project.service.implement;

import com.example.project.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String UPLOAD_DIR = "uploads/products";

    @Override
    public String saveImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("File must be an image");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            String originalName = file.getOriginalFilename();
            String cleanName = originalName == null ? "image"
                    : originalName.replaceAll("\\s+", "_")
                    .replaceAll("[^a-zA-Z0-9._-]", "");

            String fileName = UUID.randomUUID() + "_" + cleanName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/products/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }

    @Override
    public String updateImage(String existingImageUrl, MultipartFile newFile) {

        if (newFile == null || newFile.isEmpty()) {
            return existingImageUrl;
        }

        String contentType = newFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("File must be an image");
        }

        // 🔥 XÓA ẢNH CŨ
        deleteImage(existingImageUrl);

        // 🔥 LƯU ẢNH MỚI
        return saveImage(newFile);
    }

    @Override
    public void deleteImage(String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) return;

        try {
            // imageUrl = /uploads/products/abc.jpg
            String fileName = Paths.get(imageUrl).getFileName().toString();
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Không thể xóa file ảnh", e);
        }
    }
}

