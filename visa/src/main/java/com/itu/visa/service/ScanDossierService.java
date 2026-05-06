package com.itu.visa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ScanDossierService {

    private final Path rootPath;

    public ScanDossierService(@Value("${visa.scan.root:scan-dossiers}") String rootDir) {
        this.rootPath = Paths.get(rootDir).toAbsolutePath().normalize();
    }

    public Path getRootPath() {
        return rootPath;
    }

    public Path getDemandeFolder(Long demandeId, String nom, String prenom) {
        String safeNom = sanitizePathSegment(nom);
        String safePrenom = sanitizePathSegment(prenom);
        String folderName = demandeId + "_" + safeNom + "_" + safePrenom;
        return rootPath.resolve(folderName).normalize();
    }

    public Path getDocumentFolder(Path demandeFolder, String documentLibelle) {
        String safeDoc = sanitizePathSegment(documentLibelle);
        return demandeFolder.resolve(safeDoc).normalize();
    }

    public Path getExpectedPdfPath(Path documentFolder, String documentLibelle) {
        String safeDoc = sanitizePathSegment(documentLibelle);
        return documentFolder.resolve(safeDoc + ".pdf").normalize();
    }

    public void ensureFolderExists(Path folder) {
        try {
            Files.createDirectories(folder);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le dossier: " + folder, e);
        }
    }

    public boolean isPdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.toLowerCase().endsWith(".pdf")) {
            return true;
        }

        String contentType = file.getContentType();
        return contentType != null && contentType.toLowerCase().contains("pdf");
    }

    public void storePdf(MultipartFile file, Path targetFile) {
        Objects.requireNonNull(file, "file");
        Objects.requireNonNull(targetFile, "targetFile");

        if (!isPdf(file)) {
            throw new IllegalArgumentException("Seuls les fichiers PDF sont acceptés");
        }

        ensureFolderExists(targetFile.getParent());

        try (InputStream is = file.getInputStream()) {
            Files.copy(is, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
        }
    }

    public boolean fileExists(Path path) {
        try {
            return Files.exists(path);
        } catch (SecurityException e) {
            return false;
        }
    }

    private String sanitizePathSegment(String input) {
        String value = (input == null) ? "" : input.trim();
        if (value.isBlank()) {
            return "N_A";
        }

        // Windows-forbidden characters: < > : " / \ | ? *
        value = value.replaceAll("[<>:\\\"/\\\\|?*]", "_");
        // Remove control characters
        value = value.replaceAll("[\\p{Cntrl}]", "");
        // Avoid trailing dots/spaces on Windows
        value = value.replaceAll("[. ]+$", "");

        if (value.isBlank()) {
            return "N_A";
        }

        return value;
    }
}
