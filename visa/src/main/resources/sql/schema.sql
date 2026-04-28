-- Création des tables de référence (Lookup Tables)

CREATE TABLE IF NOT EXISTS pays (
    id_pays SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_pays)
);

CREATE TABLE IF NOT EXISTS nationalite (
    id_nationalite SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_nationalite)
);

CREATE TABLE IF NOT EXISTS sexe (
    id_sexe SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_sexe)
);

CREATE TABLE IF NOT EXISTS situation_familiale (
    id_situation_familiale SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_situation_familiale)
);

CREATE TABLE IF NOT EXISTS type_demande_visa (
    id_type_demande_visa SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_type_demande_visa)
);

CREATE TABLE IF NOT EXISTS type_visa (
    id_type_visa SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(id_type_visa)
);

CREATE TABLE IF NOT EXISTS documents_communs (
    id_documents_commune SERIAL,
    libelle VARCHAR(50),
    is_obligatoire BOOLEAN,
    PRIMARY KEY(id_documents_commune)
);

CREATE TABLE IF NOT EXISTS documents_types (
    id_documents_types SERIAL,
    libelle VARCHAR(50),
    is_obligatoire BOOLEAN,
    PRIMARY KEY(id_documents_types)
);

-- Création des tables principales

CREATE TABLE IF NOT EXISTS demandeur (
    id_demandeur SERIAL,
    code VARCHAR(50),
    id_type_visa INTEGER NOT NULL,
    id_type_demande_visa INTEGER NOT NULL,
    PRIMARY KEY(id_demandeur),
    FOREIGN KEY(id_type_visa) REFERENCES type_visa(id_type_visa),
    FOREIGN KEY(id_type_demande_visa) REFERENCES type_demande_visa(id_type_demande_visa)
);

CREATE TABLE IF NOT EXISTS etat_civil (
    id_etat_civil SERIAL,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    nom_jeune_fille VARCHAR(50),
    date_naissance DATE,
    lieu_naissance VARCHAR(50),
    email VARCHAR(50),
    contact VARCHAR(50),
    id_situation_familiale INTEGER NOT NULL,
    id_sexe INTEGER NOT NULL,
    id_nationalite INTEGER NOT NULL,
    id_pays INTEGER NOT NULL,
    id_demandeur INTEGER NOT NULL,
    PRIMARY KEY(id_etat_civil),
    UNIQUE(id_demandeur),
    FOREIGN KEY(id_situation_familiale) REFERENCES situation_familiale(id_situation_familiale),
    FOREIGN KEY(id_sexe) REFERENCES sexe(id_sexe),
    FOREIGN KEY(id_nationalite) REFERENCES nationalite(id_nationalite),
    FOREIGN KEY(id_pays) REFERENCES pays(id_pays),
    FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE IF NOT EXISTS passeport (
    id_passeport SERIAL,
    numero VARCHAR(50),
    date_delivrance DATE,
    date_expiration DATE,
    id_etat_civil INTEGER NOT NULL,
    PRIMARY KEY(id_passeport),
    UNIQUE(id_etat_civil),
    FOREIGN KEY(id_etat_civil) REFERENCES etat_civil(id_etat_civil)
);

CREATE TABLE IF NOT EXISTS visa_transformable (
    id_visa_transformable SERIAL,
    reference VARCHAR(50),
    date_entree_mada DATE,
    lieu VARCHAR(50),
    date_expiration DATE,
    id_etat_civil INTEGER NOT NULL,
    PRIMARY KEY(id_visa_transformable),
    UNIQUE(id_etat_civil),
    FOREIGN KEY(id_etat_civil) REFERENCES etat_civil(id_etat_civil)
);

-- Création des tables de jonction

CREATE TABLE IF NOT EXISTS demandeur_documents_types (
    id_demandeur_documents SERIAL,
    is_ok BOOLEAN,
    id_documents_types INTEGER NOT NULL,
    id_demandeur INTEGER NOT NULL,
    PRIMARY KEY(id_demandeur_documents),
    FOREIGN KEY(id_documents_types) REFERENCES documents_types(id_documents_types),
    FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE IF NOT EXISTS demandeur_documents_communs (
    id_demandeur_documents_communs SERIAL,
    is_ok BOOLEAN,
    id_demandeur INTEGER NOT NULL,
    id_documents_commune INTEGER NOT NULL,
    PRIMARY KEY(id_demandeur_documents_communs),
    FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur),
    FOREIGN KEY(id_documents_commune) REFERENCES documents_communs(id_documents_commune)
);

CREATE TABLE IF NOT EXISTS type_visa_documents (
    id_type_visa INTEGER,
    id_documents_types INTEGER,
    PRIMARY KEY(id_type_visa, id_documents_types),
    FOREIGN KEY(id_type_visa) REFERENCES type_visa(id_type_visa),
    FOREIGN KEY(id_documents_types) REFERENCES documents_types(id_documents_types)
);

CREATE TABLE IF NOT EXISTS historique_document (
    id SERIAL,
    demandeur_id INTEGER NOT NULL,
    document_id INTEGER NOT NULL,
    date_remise TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(demandeur_id) REFERENCES demandeur(id_demandeur),
    FOREIGN KEY(document_id) REFERENCES documents_types(id_documents_types)
);

CREATE TABLE IF NOT EXISTS duplicata (
    id_duplicata SERIAL,
    id_demande_original INTEGER,
    id_demande_duplicata INTEGER NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remarques VARCHAR(500),
    PRIMARY KEY(id_duplicata),
    FOREIGN KEY(id_demande_original) REFERENCES demande(id_demande),
    FOREIGN KEY(id_demande_duplicata) REFERENCES demande(id_demande)
);

-- Insertion des données initiales de référence (si vides)

INSERT INTO sexe (libelle) VALUES ('Masculin') ON CONFLICT DO NOTHING;
INSERT INTO sexe (libelle) VALUES ('Féminin') ON CONFLICT DO NOTHING;
INSERT INTO sexe (libelle) VALUES ('Autre') ON CONFLICT DO NOTHING;

INSERT INTO situation_familiale (libelle) VALUES ('Célibataire') ON CONFLICT DO NOTHING;
INSERT INTO situation_familiale (libelle) VALUES ('Marié(e)') ON CONFLICT DO NOTHING;
INSERT INTO situation_familiale (libelle) VALUES ('Divorcé(e)') ON CONFLICT DO NOTHING;
INSERT INTO situation_familiale (libelle) VALUES ('Veuf / Veuve') ON CONFLICT DO NOTHING;
INSERT INTO situation_familiale (libelle) VALUES ('Pacsé(e)') ON CONFLICT DO NOTHING;

INSERT INTO type_demande_visa (libelle) VALUES ('Nouvelle demande') ON CONFLICT DO NOTHING;
INSERT INTO type_demande_visa (libelle) VALUES ('Renouvellement') ON CONFLICT DO NOTHING;
INSERT INTO type_demande_visa (libelle) VALUES ('Transformation') ON CONFLICT DO NOTHING;
INSERT INTO type_demande_visa (libelle) VALUES ('Duplicata') ON CONFLICT DO NOTHING;

INSERT INTO type_visa (libelle) VALUES ('Tourisme') ON CONFLICT DO NOTHING;
INSERT INTO type_visa (libelle) VALUES ('Affaire') ON CONFLICT DO NOTHING;
INSERT INTO type_visa (libelle) VALUES ('Étudiant') ON CONFLICT DO NOTHING;
INSERT INTO type_visa (libelle) VALUES ('Investisseur') ON CONFLICT DO NOTHING;
INSERT INTO type_visa (libelle) VALUES ('Travailleur') ON CONFLICT DO NOTHING;
INSERT INTO type_visa (libelle) VALUES ('Famille') ON CONFLICT DO NOTHING;

INSERT INTO pays (libelle) VALUES ('France') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('États-Unis') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Allemagne') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Royaume-Uni') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Chine') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Inde') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Japon') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Madagascar') ON CONFLICT DO NOTHING;
INSERT INTO pays (libelle) VALUES ('Autre') ON CONFLICT DO NOTHING;

INSERT INTO nationalite (libelle) VALUES ('Française') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Américaine') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Allemande') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Britannique') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Chinoise') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Indienne') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Japonaise') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Malgache') ON CONFLICT DO NOTHING;
INSERT INTO nationalite (libelle) VALUES ('Autre') ON CONFLICT DO NOTHING;

INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Formulaire de demande rempli et signé', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Copie du passeport', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Photos d''identité', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Acte de naissance', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Extrait de casier judiciaire', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Justificatif de domicile', false) ON CONFLICT DO NOTHING;
INSERT INTO documents_communs (libelle, is_obligatoire) VALUES ('Certificat médical', false) ON CONFLICT DO NOTHING;

INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Plan d''affaires', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Preuve de capacité financière', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Contrat de travail', true) ON CONFLICT DO NOTHING;
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Diplômes et qualifications', false) ON CONFLICT DO NOTHING;
INSERT INTO documents_types (libelle, is_obligatoire) VALUES ('Lettre de l''employeur', true) ON CONFLICT DO NOTHING;
