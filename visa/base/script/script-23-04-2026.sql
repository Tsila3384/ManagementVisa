CREATE TABLE Pays
(
    Id_Pays SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_Pays)
);

CREATE TABLE nationalite
(
    Id_nationalite SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_nationalite)
);

CREATE TABLE sexe
(
    Id_sexe SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_sexe)
);

CREATE TABLE situation_familiale
(
    Id_situation_familiale SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_situation_familiale)
);

CREATE TABLE type_demande_visa
(
    Id_type_demande_visa SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_type_demande_visa)
);

CREATE TABLE type_visa
(
    Id_type_visa SERIAL,
    libelle VARCHAR(50) ,
    PRIMARY KEY(Id_type_visa)
);

CREATE TABLE documents_communs
(
    Id_documents_commune SERIAL,
    libelle VARCHAR(50),
    is_obligatoire BOOLEAN,
    PRIMARY KEY(Id_documents_commune)
);

CREATE TABLE documents_types
(
    Id_documents_types SERIAL,
    libelle VARCHAR(50),
    is_obligatoire BOOLEAN,
    PRIMARY KEY(Id_documents_types)
);

CREATE TABLE demandeur
(
    Id_demandeur SERIAL,
    code VARCHAR(50) ,
    Id_type_visa INTEGER NOT NULL,
    Id_type_demande_visa INTEGER NOT NULL,
    PRIMARY KEY(Id_demandeur),
    FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id_type_visa),
    FOREIGN KEY(Id_type_demande_visa) REFERENCES type_demande_visa(Id_type_demande_visa)
);

CREATE TABLE statut_demande
(
    Id_statut_demande SERIAL,
    libelle VARCHAR(50),
    PRIMARY KEY(Id_statut_demande)
);

CREATE TABLE demande
(
    Id_demande SERIAL,
    date_demande DATE,
    is_duplicata BOOLEAN DEFAULT FALSE,
    date_duplicata DATE,
    Id_demandeur INTEGER NOT NULL,
    Id_statut_demande INTEGER NOT NULL,
    PRIMARY KEY(Id_demande),
    FOREIGN KEY(Id_statut_demande) REFERENCES statut_demande(Id_statut_demande),
    FOREIGN KEY(Id_demandeur) REFERENCES demandeur(Id_demandeur)
);

CREATE TABLE etat_civil
(
    Id_etat_civil SERIAL,
    nom VARCHAR(50) ,
    prenom VARCHAR(50) ,
    nom_jeune_fille VARCHAR(50) ,
    date_naissance DATE,
    lieu_naissance VARCHAR(50) ,
    email VARCHAR(50) ,
    contact VARCHAR(50) ,
    Id_situation_familiale INTEGER NOT NULL,
    Id_sexe INTEGER NOT NULL,
    Id_nationalite INTEGER NOT NULL,
    Id_Pays INTEGER NOT NULL,
    Id_demandeur INTEGER NOT NULL,
    PRIMARY KEY(Id_etat_civil),
    UNIQUE(Id_demandeur),
    FOREIGN KEY(Id_situation_familiale) REFERENCES situation_familiale(Id_situation_familiale),
    FOREIGN KEY(Id_sexe) REFERENCES sexe(Id_sexe),
    FOREIGN KEY(Id_nationalite) REFERENCES nationalite(Id_nationalite),
    FOREIGN KEY(Id_Pays) REFERENCES Pays(Id_Pays),
    FOREIGN KEY(Id_demandeur) REFERENCES demandeur(Id_demandeur)
);

CREATE TABLE passeport
(
    Id_passeport SERIAL,
    numero VARCHAR(50) ,
    date_delivrance DATE,
    date_expiration DATE,
    Id_etat_civil INTEGER NOT NULL,
    PRIMARY KEY(Id_passeport),
    UNIQUE(Id_etat_civil),
    FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id_etat_civil)
);

CREATE TABLE visa_transformable
(
    Id_visa_transformable SERIAL,
    reference VARCHAR(50) ,
    date_entree_mada DATE,
    lieu VARCHAR(50) ,
    date_expiration DATE,
    Id_etat_civil INTEGER NOT NULL,
    PRIMARY KEY(Id_visa_transformable),
    UNIQUE(Id_etat_civil),
    FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id_etat_civil)
);

CREATE TABLE demandeur_documents_types
(
    Id_demandeur_documents SERIAL,
    is_ok BOOLEAN,
    Id_documents_types INTEGER NOT NULL,
    Id_demandeur INTEGER NOT NULL,
    PRIMARY KEY(Id_demandeur_documents),
    FOREIGN KEY(Id_documents_types) REFERENCES documents_types(Id_documents_types),
    FOREIGN KEY(Id_demandeur) REFERENCES demandeur(Id_demandeur)
);

CREATE TABLE demandeur_documents_communs
(
    Id_demandeur_documents_communs SERIAL,
    is_ok BOOLEAN,
    Id_demandeur INTEGER NOT NULL,
    Id_documents_commune INTEGER NOT NULL,
    PRIMARY KEY(Id_demandeur_documents_communs),
    FOREIGN KEY(Id_demandeur) REFERENCES demandeur(Id_demandeur),
    FOREIGN KEY(Id_documents_commune) REFERENCES documents_communs(Id_documents_commune)
);

CREATE TABLE type_visa_documents
(
    Id_type_visa INTEGER,
    Id_documents_types INTEGER,
    PRIMARY KEY(Id_type_visa, Id_documents_types),
    FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id_type_visa),
    FOREIGN KEY(Id_documents_types) REFERENCES documents_types(Id_documents_types)
);