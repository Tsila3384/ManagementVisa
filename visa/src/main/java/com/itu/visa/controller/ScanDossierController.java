package com.itu.visa.controller;

import com.itu.visa.entity.Demande;
import com.itu.visa.entity.DemandeDocumentsCommun;
import com.itu.visa.entity.DemandeDocumentsType;
import com.itu.visa.entity.EtatCivil;
import com.itu.visa.repository.DemandeDocumentsCommunRepository;
import com.itu.visa.repository.DemandeDocumentsTypeRepository;
import com.itu.visa.repository.DemandeRepository;
import com.itu.visa.repository.EtatCivilRepository;
import com.itu.visa.repository.HistoriqueDocumentCommunRepository;
import com.itu.visa.repository.HistoriqueDocumentRepository;
import com.itu.visa.repository.StatutDemandeRepository;
import com.itu.visa.service.ScanDossierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/demandes/{demandeId}/scan")
public class ScanDossierController {

    private final DemandeRepository demandeRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;
    private final DemandeDocumentsTypeRepository demandeDocumentsTypeRepository;
    private final StatutDemandeRepository statutDemandeRepository;
    private final HistoriqueDocumentRepository historiqueDocumentRepository;
    private final HistoriqueDocumentCommunRepository historiqueDocumentCommunRepository;
    private final ScanDossierService scanDossierService;

    public ScanDossierController(
            DemandeRepository demandeRepository,
            EtatCivilRepository etatCivilRepository,
            DemandeDocumentsCommunRepository demandeDocumentsCommunRepository,
            DemandeDocumentsTypeRepository demandeDocumentsTypeRepository,
            StatutDemandeRepository statutDemandeRepository,
            HistoriqueDocumentRepository historiqueDocumentRepository,
                HistoriqueDocumentCommunRepository historiqueDocumentCommunRepository,
            ScanDossierService scanDossierService
    ) {
        this.demandeRepository = demandeRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.demandeDocumentsCommunRepository = demandeDocumentsCommunRepository;
        this.demandeDocumentsTypeRepository = demandeDocumentsTypeRepository;
        this.statutDemandeRepository = statutDemandeRepository;
        this.historiqueDocumentRepository = historiqueDocumentRepository;
        this.historiqueDocumentCommunRepository = historiqueDocumentCommunRepository;
        this.scanDossierService = scanDossierService;
    }

    public record ScanDocItem(String kind, Long joinId, String libelle, boolean uploaded) {}

    @PostMapping("/import")
    public String importFolders(@PathVariable Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            return "redirect:/demandes";
        }

        var demandeur = demande.getDemandeur();
        EtatCivil etatCivil = etatCivilRepository.findByDemandeur(demandeur).orElse(null);
        String nom = etatCivil != null ? etatCivil.getNom() : demandeur.getCode();
        String prenom = etatCivil != null ? etatCivil.getPrenom() : "";

        Path demandeFolder = scanDossierService.getDemandeFolder(demandeId, nom, prenom);
        scanDossierService.ensureFolderExists(demandeFolder);

        // Créer un dossier par document (communs + typés)
        var docsCommuns = demandeDocumentsCommunRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsCommun doc : docsCommuns) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsCommun().getLibelle();
            scanDossierService.ensureFolderExists(scanDossierService.getDocumentFolder(demandeFolder, libelle));
        }

        var docsTypes = demandeDocumentsTypeRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsType doc : docsTypes) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsType().getLibelle();
            scanDossierService.ensureFolderExists(scanDossierService.getDocumentFolder(demandeFolder, libelle));
        }

        return "redirect:/demandes/" + demandeId + "/scan";
    }

    @GetMapping
    public String scanPage(
            @PathVariable Long demandeId,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "success", required = false) String success,
            Model model
    ) {
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            return "redirect:/demandes";
        }

        var demandeur = demande.getDemandeur();
        EtatCivil etatCivil = etatCivilRepository.findByDemandeur(demandeur).orElse(null);
        String nom = etatCivil != null ? etatCivil.getNom() : demandeur.getCode();
        String prenom = etatCivil != null ? etatCivil.getPrenom() : "";

        Path demandeFolder = scanDossierService.getDemandeFolder(demandeId, nom, prenom);

        List<ScanDocItem> documents = new ArrayList<>();

        var docsCommuns = demandeDocumentsCommunRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsCommun doc : docsCommuns) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsCommun().getLibelle();
            Path docFolder = scanDossierService.getDocumentFolder(demandeFolder, libelle);
            Path expectedPdf = scanDossierService.getExpectedPdfPath(docFolder, libelle);
            boolean uploaded = scanDossierService.fileExists(expectedPdf);
            documents.add(new ScanDocItem("COMMUN", doc.getIdDemandeDocumentsCommuns(), libelle, uploaded));
        }

        var docsTypes = demandeDocumentsTypeRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsType doc : docsTypes) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsType().getLibelle();
            Path docFolder = scanDossierService.getDocumentFolder(demandeFolder, libelle);
            Path expectedPdf = scanDossierService.getExpectedPdfPath(docFolder, libelle);
            boolean uploaded = scanDossierService.fileExists(expectedPdf);
            documents.add(new ScanDocItem("TYPE", doc.getIdDemandeDocuments(), libelle, uploaded));
        }

        boolean allUploaded = !documents.isEmpty() && documents.stream().allMatch(ScanDocItem::uploaded);

        var historiqueDocuments = historiqueDocumentRepository
            .findByDemandeurIdOrderByDateRemiseDesc(demandeur.getIdDemandeur());
        var historiqueDocumentsCommuns = historiqueDocumentCommunRepository
            .findByDemandeurIdOrderByDateRemiseDesc(demandeur.getIdDemandeur());

        model.addAttribute("demande", demande);
        model.addAttribute("demandeur", demandeur);
        model.addAttribute("etatCivil", etatCivil);
        model.addAttribute("documents", documents);
        model.addAttribute("allUploaded", allUploaded);
        model.addAttribute("historiqueDocuments", historiqueDocuments);
        model.addAttribute("historiqueDocumentsCommuns", historiqueDocumentsCommuns);
        model.addAttribute("error", error);
        model.addAttribute("success", success);

        return "scan-dossiers";
    }

    @PostMapping("/upload")
    public String upload(
            @PathVariable Long demandeId,
            @RequestParam("kind") String kind,
            @RequestParam("joinId") Long joinId,
            @RequestParam("file") MultipartFile file
    ) {
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            return "redirect:/demandes";
        }

        var demandeur = demande.getDemandeur();
        EtatCivil etatCivil = etatCivilRepository.findByDemandeur(demandeur).orElse(null);
        String nom = etatCivil != null ? etatCivil.getNom() : demandeur.getCode();
        String prenom = etatCivil != null ? etatCivil.getPrenom() : "";

        Path demandeFolder = scanDossierService.getDemandeFolder(demandeId, nom, prenom);

        String libelle;
        if ("COMMUN".equalsIgnoreCase(kind)) {
            DemandeDocumentsCommun doc = demandeDocumentsCommunRepository.findById(joinId).orElse(null);
            if (doc == null || doc.getDemandeur() == null || !doc.getDemandeur().getIdDemandeur().equals(demandeur.getIdDemandeur())) {
                return "redirect:/demandes/" + demandeId + "/scan?error=Document+introuvable";
            }
            libelle = doc.getDocumentsCommun().getLibelle();
        } else if ("TYPE".equalsIgnoreCase(kind)) {
            DemandeDocumentsType doc = demandeDocumentsTypeRepository.findById(joinId).orElse(null);
            if (doc == null || doc.getDemandeur() == null || !doc.getDemandeur().getIdDemandeur().equals(demandeur.getIdDemandeur())) {
                return "redirect:/demandes/" + demandeId + "/scan?error=Document+introuvable";
            }
            libelle = doc.getDocumentsType().getLibelle();
        } else {
            return "redirect:/demandes/" + demandeId + "/scan?error=Type+de+document+invalide";
        }

        if (!scanDossierService.isPdf(file)) {
            return "redirect:/demandes/" + demandeId + "/scan?error=Veuillez+uploader+un+fichier+PDF";
        }

        Path docFolder = scanDossierService.getDocumentFolder(demandeFolder, libelle);
        Path expectedPdf = scanDossierService.getExpectedPdfPath(docFolder, libelle);

        try {
            scanDossierService.storePdf(file, expectedPdf);
            return "redirect:/demandes/" + demandeId + "/scan?success=Fichier+uploade";
        } catch (Exception e) {
            return "redirect:/demandes/" + demandeId + "/scan?error=Erreur+lors+de+l%27upload";
        }
    }

    @PostMapping("/terminer")
    public String terminer(@PathVariable Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            return "redirect:/demandes";
        }

        var demandeur = demande.getDemandeur();
        EtatCivil etatCivil = etatCivilRepository.findByDemandeur(demandeur).orElse(null);
        String nom = etatCivil != null ? etatCivil.getNom() : demandeur.getCode();
        String prenom = etatCivil != null ? etatCivil.getPrenom() : "";

        Path demandeFolder = scanDossierService.getDemandeFolder(demandeId, nom, prenom);

        // Recalculer si tous les documents sont uploadés
        List<Boolean> uploadedFlags = new ArrayList<>();

        var docsCommuns = demandeDocumentsCommunRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsCommun doc : docsCommuns) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsCommun().getLibelle();
            Path docFolder = scanDossierService.getDocumentFolder(demandeFolder, libelle);
            Path expectedPdf = scanDossierService.getExpectedPdfPath(docFolder, libelle);
            uploadedFlags.add(scanDossierService.fileExists(expectedPdf));
        }

        var docsTypes = demandeDocumentsTypeRepository.findByDemandeur(demandeur);
        for (DemandeDocumentsType doc : docsTypes) {
            if (!Boolean.TRUE.equals(doc.getIsOk())) {
                continue;
            }
            String libelle = doc.getDocumentsType().getLibelle();
            Path docFolder = scanDossierService.getDocumentFolder(demandeFolder, libelle);
            Path expectedPdf = scanDossierService.getExpectedPdfPath(docFolder, libelle);
            uploadedFlags.add(scanDossierService.fileExists(expectedPdf));
        }

        boolean allUploaded = !uploadedFlags.isEmpty() && uploadedFlags.stream().allMatch(Boolean::booleanValue);
        if (!allUploaded) {
            return "redirect:/demandes/" + demandeId + "/scan?error=Tous+les+dossiers+ne+sont+pas+encore+uploade";
        }

        var statut2 = statutDemandeRepository.findById(2L).orElse(null);
        if (statut2 == null) {
            return "redirect:/demandes/" + demandeId + "/scan?error=Statut+2+introuvable";
        }

        demande.setStatutDemande(statut2);
        demandeRepository.save(demande);

        return "redirect:/demandes/" + demandeId;
    }
}
