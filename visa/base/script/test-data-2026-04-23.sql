-- ============================================================
-- DONNÉES DE TEST POUR LE SYSTÈME DE DEMANDE DE VISA
-- ============================================================
-- Date : 2026-04-23
-- Ce script insère des données de test complètes pour tester
-- le formulaire et la gestion des demandes de visa

-- NETTOYAGE DES DONNÉES EXISTANTES (optionnel)
-- DELETE FROM demandeur_documents_communs;
-- DELETE FROM demandeur_documents_types;
-- DELETE FROM type_visa_documents;
-- DELETE FROM visa_transformable;
-- DELETE FROM passeport;
-- DELETE FROM etat_civil;
-- DELETE FROM demandeur;

-- ============================================================
-- 1. INSERTION DES TABLES DE RÉFÉRENCE
-- ============================================================

-- Sexe
INSERT INTO sexe (libelle) VALUES ('Masculin');
INSERT INTO sexe (libelle) VALUES ('Féminin');
INSERT INTO sexe (libelle) VALUES ('Autre');

-- Situation familiale
INSERT INTO situation_familiale (libelle) VALUES ('Célibataire');
INSERT INTO situation_familiale (libelle) VALUES ('Marié(e)');
INSERT INTO situation_familiale (libelle) VALUES ('Divorcé(e)');
INSERT INTO situation_familiale (libelle) VALUES ('Veuf / Veuve');
INSERT INTO situation_familiale (libelle) VALUES ('Pacsé(e)');

-- Type de demande visa
INSERT INTO type_demande_visa (libelle) VALUES ('Nouvelle demande');
INSERT INTO type_demande_visa (libelle) VALUES ('Renouvellement');
INSERT INTO type_demande_visa (libelle) VALUES ('Transformation');
INSERT INTO type_demande_visa (libelle) VALUES ('Duplicata');

-- Type de visa
-- INSERT INTO type_visa (libelle) VALUES ('Tourisme');
-- INSERT INTO type_visa (libelle) VALUES ('Affaire');
-- INSERT INTO type_visa (libelle) VALUES ('Étudiant');
INSERT INTO type_visa (libelle) VALUES ('Investisseur');
INSERT INTO type_visa (libelle) VALUES ('Travailleur');
-- INSERT INTO type_visa (libelle) VALUES ('Famille');

-- Pays
INSERT INTO pays (libelle) VALUES ('France');
INSERT INTO pays (libelle) VALUES ('États-Unis');
INSERT INTO pays (libelle) VALUES ('Allemagne');
INSERT INTO pays (libelle) VALUES ('Royaume-Uni');
INSERT INTO pays (libelle) VALUES ('Chine');
INSERT INTO pays (libelle) VALUES ('Inde');
INSERT INTO pays (libelle) VALUES ('Japon');
INSERT INTO pays (libelle) VALUES ('Madagascar');
INSERT INTO pays (libelle) VALUES ('Canada');
INSERT INTO pays (libelle) VALUES ('Belgique');

-- Nationalité
INSERT INTO nationalite (libelle) VALUES ('Française');
INSERT INTO nationalite (libelle) VALUES ('Américaine');
INSERT INTO nationalite (libelle) VALUES ('Allemande');
INSERT INTO nationalite (libelle) VALUES ('Britannique');
INSERT INTO nationalite (libelle) VALUES ('Chinoise');
INSERT INTO nationalite (libelle) VALUES ('Indienne');
INSERT INTO nationalite (libelle) VALUES ('Japonaise');
INSERT INTO nationalite (libelle) VALUES ('Malgache');
INSERT INTO nationalite (libelle) VALUES ('Canadienne');
INSERT INTO nationalite (libelle) VALUES ('Belge');

-- Statut de demande
INSERT INTO statut_demande (libelle) VALUES ('En attente');
INSERT INTO statut_demande (libelle) VALUES ('En cours d''examen');
INSERT INTO statut_demande (libelle) VALUES ('Approuvée');
INSERT INTO statut_demande (libelle) VALUES ('Rejetée');
INSERT INTO statut_demande (libelle) VALUES ('En révision');

-- Documents communs
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Formulaire de demande rempli et signé', true);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Copie du passeport (pages d''identité)', true);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Photos d''identité récentes (4 exemplaires)', true);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Acte de naissance (traduction officielle)', true);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Extrait de casier judiciaire vierge', true);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Justificatif de domicile à Madagascar', false);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Certificat médical d''aptitude', false);
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Preuve de paiement des frais de dossier', true);

-- Documents spécifiques aux types
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Plan d''affaires détaillé', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Preuve de capacité financière (relevés bancaires 3 mois)', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Attestation d''investissement ou lettre d''intention', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Statuts de la société (si déjà constituée)', false);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Autorisation de l''EDBM (Economic Development Board)', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Rapport d''audit financier (2 dernières années)', false);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Contrat de travail visé par le Ministère du Travail', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Autorisation de travail délivrée par les autorités', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Diplômes et qualifications professionnelles (traduction)', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Lettre de l''employeur sur papier en-tête officiel', true);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Fiche de poste et description des missions', false);
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Preuve d''enregistrement de l''employeur à Madagascar', true);

-- ============================================================
-- 2. INSERTION DES DONNÉES DE TEST
-- ============================================================

-- DEMANDEUR 1 : Investisseur français
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000001', 4, 1);  -- Type visa: Investisseur, Type demande: Nouvelle demande

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'DUPONT', 'Jean', NULL, '1980-05-15', 'Paris, France',
    'jean.dupont@example.com', '+33 6 12 34 56 78',
    2, 1, 1, 1,  -- Marié, Masculin, Français, France
    1
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('12AB34567', '2018-03-20', '2028-03-20', 1);

INSERT INTO visa_transformable (reference, date_entree_mada, lieu, date_expiration, id_etat_civil)
VALUES ('VT-2024-000123', '2024-06-15', 'Antananarivo - Ivato', '2025-06-15', 1);

-- DEMANDEUR 2 : Travailleur allemand
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000002', 5, 1);  -- Type visa: Travailleur, Type demande: Nouvelle demande

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'MUELLER', 'Anna', 'SCHMIDT', '1992-08-22', 'Berlin, Allemagne',
    'anna.mueller@example.de', '+49 30 123 45 67',
    1, 2, 3, 3,  -- Célibataire, Féminin, Allemande, Allemagne
    2
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('DE45678901', '2020-11-10', '2030-11-10', 2);

-- DEMANDEUR 3 : Touriste britannique
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000003', 1, 1);  -- Type visa: Tourisme, Type demande: Nouvelle demande

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'SMITH', 'Robert', NULL, '1975-12-03', 'Londres, Royaume-Uni',
    'robert.smith@example.co.uk', '+44 20 1234 5678',
    2, 1, 4, 4,  -- Marié, Masculin, Britannique, Royaume-Uni
    3
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('GB123456789', '2019-07-05', '2029-07-05', 3);

-- DEMANDEUR 4 : Investisseur américain
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000004', 4, 3);  -- Type visa: Investisseur, Type demande: Transformation

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'JOHNSON', 'Michael', NULL, '1978-01-18', 'New York, États-Unis',
    'michael.johnson@example.com', '+1 212 555 0100',
    1, 1, 2, 2,  -- Célibataire, Masculin, Américain, États-Unis
    4
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('US9876543210', '2017-09-14', '2027-09-14', 4);

INSERT INTO visa_transformable (reference, date_entree_mada, lieu, date_expiration, id_etat_civil)
VALUES ('VT-2022-000456', '2022-04-20', 'Toliara', '2025-04-20', 4);

-- DEMANDEUR 5 : Travailleur indien
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000005', 5, 1);  -- Type visa: Travailleur, Type demande: Nouvelle demande

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'KUMAR', 'Rajesh', NULL, '1985-03-10', 'Mumbai, Inde',
    'rajesh.kumar@example.in', '+91 22 1234 5678',
    2, 1, 6, 6,  -- Marié, Masculin, Indien, Inde
    5
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('IN123456789', '2019-02-15', '2029-02-15', 5);

-- DEMANDEUR 6 : Étudiante japonaise
INSERT INTO demandeur (code, id_type_visa, id_type_demande_visa) 
VALUES ('VLS-2026-000006', 3, 1);  -- Type visa: Étudiant, Type demande: Nouvelle demande

INSERT INTO etat_civil (
    nom, prenom, nom_jeune_fille, date_naissance, lieu_naissance, 
    email, contact, 
    id_situation_familiale, id_sexe, id_nationalite, id_pays, 
    id_demandeur
) VALUES (
    'TANAKA', 'Yuki', NULL, '2002-07-08', 'Tokyo, Japon',
    'yuki.tanaka@example.jp', '+81 3 1234 5678',
    1, 2, 7, 7,  -- Célibataire, Féminin, Japonaise, Japon
    6
);

INSERT INTO passeport (numero, date_delivrance, date_expiration, id_etat_civil)
VALUES ('JP123456789', '2021-04-22', '2031-04-22', 6);

-- ============================================================
-- 2.5. INSERTION DES DEMANDES
-- ============================================================

-- Demande 1 : Investisseur français (En cours d'examen)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-15', 1, 2);  -- En cours d'examen

-- Demande 2 : Travailleur allemand (En attente)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-22', 2, 1);  -- En attente

-- Demande 3 : Touriste britannique (Approuvée)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-10', 3, 3);  -- Approuvée

-- Demande 4 : Investisseur américain (En cours d'examen)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-05', 4, 2);  -- En cours d'examen

-- Demande 5 : Travailleur indien (En attente)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-20', 5, 1);  -- En attente

-- Demande 6 : Étudiante japonaise (En révision)
INSERT INTO demande (date_demande, id_demandeur, id_statut_demande) 
VALUES ('2026-04-18', 6, 5);  -- En révision

-- ============================================================
-- 3. INSERTION DES ASSOCIATIONS DOCUMENTS
-- ============================================================

-- Documents communs pour le demandeur 1
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 1, 1), (true, 1, 2), (true, 1, 3), (true, 1, 4), (true, 1, 5), (false, 1, 6), (false, 1, 7), (true, 1, 8);

-- Documents spécifiques type visa pour le demandeur 1 (Investisseur)
INSERT INTO demandeur_documents_types (is_ok, id_documents_types, id_demandeur) 
VALUES (true, 1, 1), (true, 2, 1), (true, 3, 1), (false, 4, 1), (true, 5, 1), (false, 6, 1);

-- Documents communs pour le demandeur 2
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 2, 1), (true, 2, 2), (true, 2, 3), (true, 2, 4), (true, 2, 5), (true, 2, 6), (false, 2, 7), (true, 2, 8);

-- Documents spécifiques type visa pour le demandeur 2 (Travailleur)
INSERT INTO demandeur_documents_types (is_ok, id_documents_types, id_demandeur) 
VALUES (true, 7, 2), (true, 8, 2), (true, 9, 2), (true, 10, 2), (false, 11, 2), (true, 12, 2);

-- Documents communs pour le demandeur 3
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 3, 1), (true, 3, 2), (true, 3, 3), (true, 3, 4), (true, 3, 5), (false, 3, 6), (false, 3, 7), (true, 3, 8);

-- Documents communs pour le demandeur 4
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 4, 1), (true, 4, 2), (true, 4, 3), (true, 4, 4), (true, 4, 5), (true, 4, 6), (true, 4, 7), (true, 4, 8);

-- Documents spécifiques type visa pour le demandeur 4 (Investisseur)
INSERT INTO demandeur_documents_types (is_ok, id_documents_types, id_demandeur) 
VALUES (true, 1, 4), (true, 2, 4), (true, 3, 4), (true, 4, 4), (true, 5, 4), (true, 6, 4);

-- Documents communs pour le demandeur 5
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 5, 1), (true, 5, 2), (true, 5, 3), (true, 5, 4), (true, 5, 5), (false, 5, 6), (false, 5, 7), (true, 5, 8);

-- Documents spécifiques type visa pour le demandeur 5 (Travailleur)
INSERT INTO demandeur_documents_types (is_ok, id_documents_types, id_demandeur) 
VALUES (true, 7, 5), (true, 8, 5), (true, 9, 5), (true, 10, 5), (false, 11, 5), (true, 12, 5);

-- Documents communs pour le demandeur 6
INSERT INTO demandeur_documents_communs (is_ok, id_demandeur, id_documents_commune) 
VALUES (true, 6, 1), (true, 6, 2), (true, 6, 3), (true, 6, 4), (true, 6, 5), (false, 6, 6), (false, 6, 7), (true, 6, 8);

-- ============================================================
-- 4. ASSOCIATIONS TYPE_VISA_DOCUMENTS
-- ============================================================

-- Documents pour les Investisseurs (Type visa 4)
INSERT INTO type_visa_documents (id_type_visa, id_documents_types) 
VALUES (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6);

-- Documents pour les Travailleurs (Type visa 5)
INSERT INTO type_visa_documents (id_type_visa, id_documents_types) 
VALUES (5, 7), (5, 8), (5, 9), (5, 10), (5, 11), (5, 12);

-- ============================================================
-- 5. REQUÊTES DE VÉRIFICATION
-- ============================================================

-- Vérifier les données insérées
SELECT COUNT(*) as total_demandeurs FROM demandeur;
SELECT * FROM demandeur;
SELECT * FROM etat_civil;
SELECT * FROM passeport;
SELECT * FROM visa_transformable;

-- Joindre les informations complètes
SELECT 
    d.code,
    ec.nom,
    ec.prenom,
    sf.libelle as situation_familiale,
    tv.libelle as type_visa,
    tdv.libelle as type_demande,
    n.libelle as nationalite,
    p.libelle as pays
FROM demandeur d
JOIN etat_civil ec ON d.id_demandeur = ec.id_demandeur
JOIN situation_familiale sf ON ec.id_situation_familiale = sf.id_situation_familiale
JOIN type_visa tv ON d.id_type_visa = tv.id_type_visa
JOIN type_demande_visa tdv ON d.id_type_demande_visa = tdv.id_type_demande_visa
JOIN nationalite n ON ec.id_nationalite = n.id_nationalite
JOIN pays p ON ec.id_pays = p.id_pays;
