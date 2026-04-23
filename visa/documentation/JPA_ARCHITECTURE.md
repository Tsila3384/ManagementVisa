# Guide d'Architecture JPA pour le Système de Gestion des Visas

## Vue d'ensemble

Ce document décrit l'architecture JPA et la structure de base de données pour l'application de gestion des visas. Il couvre toutes les entités, les repositories, les dépendances Maven et la configuration PostgreSQL.

---

## Table des matières

1. [Architecture générale](#architecture-générale)
2. [Dépendances Maven](#dépendances-maven)
3. [Configuration PostgreSQL](#configuration-postgresql)
4. [Description des entités JPA](#description-des-entités-jpa)
5. [Repositories](#repositories)
6. [Utilisation des entités](#utilisation-des-entités)
7. [Modèle relationnel](#modèle-relationnel)

---

## Architecture générale

### Structure des packages

```
com.itu.visa
├── entity/          # Contient toutes les entités JPA
├── repository/      # Contient tous les repositories Spring Data JPA
├── service/         # (À implémenter) Couche métier
├── controller/      # (À implémenter) Contrôleurs REST
└── VisaApplication.java  # Classe principale
```

### Approches utilisées

- **Lombok** : Réduit le boilerplate code (getters, setters, constructeurs)
- **Spring Data JPA** : Implémente le pattern Repository avec les interfaces CRUD
- **PostgreSQL** : Base de données relationnelle
- **Fetch Type LAZY** : Chargement lazy par défaut pour optimiser les requêtes

---

## Dépendances Maven

Le fichier `pom.xml` inclut les dépendances suivantes :

### Dépendances de base Spring Boot
```xml
<!-- Spring Boot Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Boot Web (MVC/REST) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### Dépendances de base de données
```xml
<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Dépendances de développement
```xml
<!-- Lombok - Réduit le boilerplate code -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## Configuration PostgreSQL

### Fichier : `application.properties`

```properties
# Configuration de la base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/visa_db
spring.datasource.username=postgres
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuration JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.org.hibernate.SQL=DEBUG
```

### Paramètres clés

| Paramètre | Description | Valeur |
|-----------|-------------|--------|
| `url` | Chaîne de connexion PostgreSQL | `jdbc:postgresql://localhost:5432/visa_db` |
| `username` | Utilisateur PostgreSQL | `postgres` |
| `password` | Mot de passe PostgreSQL | À configurer |
| `ddl-auto` | Validation du schéma | `validate` (ne crée rien automatiquement) |
| `dialect` | Dialecte Hibernate | `PostgreSQLDialect` |

### Avant de démarrer l'application

1. Créez la base de données PostgreSQL :
```sql
CREATE DATABASE visa_db;
```

2. Exécutez le script SQL fourni :
```sql
psql -U postgres -d visa_db -f base/script/script-23-04-2026.sql
```

3. Mettez à jour le mot de passe dans `application.properties`

---

## Description des entités JPA

### Entités de référence (Lookup Tables)

#### 1. **Pays**
```java
@Entity
@Table(name = "pays")
```
- **Description** : Liste des pays
- **Champs principaux**
  - `idPays` (PK) : Identifiant unique
  - `libelle` : Nom du pays

#### 2. **Nationalite**
```java
@Entity
@Table(name = "nationalite")
```
- **Description** : Nationalités possibles
- **Champs principaux**
  - `idNationalite` (PK) : Identifiant unique
  - `libelle` : Libellé de la nationalité

#### 3. **Sexe**
```java
@Entity
@Table(name = "sexe")
```
- **Description** : Sexe (Masculin, Féminin, Autre)
- **Champs principaux**
  - `idSexe` (PK) : Identifiant unique
  - `libelle` : Libellé du sexe

#### 4. **SituationFamiliale**
```java
@Entity
@Table(name = "situation_familiale")
```
- **Description** : Situation familiale (Célibataire, Marié, Divorcé, etc.)
- **Champs principaux**
  - `idSituationFamiliale` (PK) : Identifiant unique
  - `libelle` : Libellé de la situation

#### 5. **TypeDemandeVisa**
```java
@Entity
@Table(name = "type_demande_visa")
```
- **Description** : Type de demande (Nouvelle, Renouvellement, Transformation)
- **Champs principaux**
  - `idTypeDemandeVisa` (PK) : Identifiant unique
  - `libelle` : Type de demande

#### 6. **TypeVisa**
```java
@Entity
@Table(name = "type_visa")
```
- **Description** : Type de visa (Tourisme, Affaire, Étudiant, etc.)
- **Champs principaux**
  - `idTypeVisa` (PK) : Identifiant unique
  - `libelle` : Type de visa

#### 7. **DocumentsCommun**
```java
@Entity
@Table(name = "documents_communs")
```
- **Description** : Documents requis pour tous les types de visa
- **Champs principaux**
  - `idDocumentsCommune` (PK) : Identifiant unique
  - `libelle` : Nom du document
  - `isObligatoire` : Indique si le document est obligatoire

#### 8. **DocumentsType**
```java
@Entity
@Table(name = "documents_types")
```
- **Description** : Documents spécifiques à un type de visa
- **Champs principaux**
  - `idDocumentsTypes` (PK) : Identifiant unique
  - `libelle` : Nom du document
  - `isObligatoire` : Indique si le document est obligatoire

---

### Entités principales

#### 9. **Demandeur**
```java
@Entity
@Table(name = "demandeur")
```
- **Description** : Personne demandant un visa
- **Champs principaux**
  - `idDemandeur` (PK) : Identifiant unique
  - `code` : Code unique du demandeur
  - `typeVisa` (FK) : Référence à TypeVisa
  - `typeDemandeVisa` (FK) : Référence à TypeDemandeVisa
- **Relations**
  - OneToOne → EtatCivil
  - OneToMany → DemandeDocumentsType
  - OneToMany → DemandeDocumentsCommun

#### 10. **EtatCivil**
```java
@Entity
@Table(name = "etat_civil")
```
- **Description** : Informations civiles d'un demandeur
- **Champs principaux**
  - `idEtatCivil` (PK) : Identifiant unique
  - `nom`, `prenom` : Noms et prénoms
  - `nomJeuneFille` : Nom de jeune fille
  - `dateNaissance`, `lieuNaissance` : Données de naissance
  - `email`, `contact` : Informations de contact
  - Références FK vers Nationalite, Sexe, SituationFamiliale, Pays
  - `demandeur` (OneToOne, unique) : Référence au Demandeur
- **Relations**
  - OneToOne ← Demandeur
  - OneToOne → Passeport
  - OneToOne → VisaTransformable

#### 11. **Passeport**
```java
@Entity
@Table(name = "passeport")
```
- **Description** : Informations de passeport d'un demandeur
- **Champs principaux**
  - `idPasseport` (PK) : Identifiant unique
  - `numero` : Numéro de passeport
  - `dateDelivrance`, `dateExpiration` : Dates clés
  - `etatCivil` (OneToOne, unique) : Référence à EtatCivil

#### 12. **VisaTransformable**
```java
@Entity
@Table(name = "visa_transformable")
```
- **Description** : Information sur un visa pouvant être transformé
- **Champs principaux**
  - `idVisaTransformable` (PK) : Identifiant unique
  - `reference` : Référence du visa
  - `dateEntreeMada`, `dateExpiration` : Dates clés
  - `lieu` : Lieu d'entrée à Madagascar
  - `etatCivil` (OneToOne, unique) : Référence à EtatCivil

---

### Entités de jonction (Mapping Tables)

#### 13. **DemandeDocumentsType**
```java
@Entity
@Table(name = "demandeur_documents_types")
```
- **Description** : Relation N:M entre Demandeur et DocumentsType
- **Champs principaux**
  - `idDemandeDocuments` (PK) : Identifiant unique
  - `isOk` : Statut de complétude du document
  - `demandeur` (FK) : Référence à Demandeur
  - `documentsType` (FK) : Référence à DocumentsType

#### 14. **DemandeDocumentsCommun**
```java
@Entity
@Table(name = "demandeur_documents_communs")
```
- **Description** : Relation N:M entre Demandeur et DocumentsCommun
- **Champs principaux**
  - `idDemandeDocumentsCommuns` (PK) : Identifiant unique
  - `isOk` : Statut de complétude du document
  - `demandeur` (FK) : Référence à Demandeur
  - `documentsCommun` (FK) : Référence à DocumentsCommun

#### 15. **TypeVisaDocuments** (avec clé composite)
```java
@Entity
@Table(name = "type_visa_documents")
@IdClass(TypeVisaDocumentsId.class)
```
- **Description** : Relation N:M entre TypeVisa et DocumentsType
- **Champs principaux**
  - `typeVisa` (PK, FK) : Référence à TypeVisa
  - `documentsType` (PK, FK) : Référence à DocumentsType
- **Classe d'aide**
  - `TypeVisaDocumentsId` : Classe représentant la clé composite

---

## Repositories

Chaque entité possède un repository correspondant qui hérite de `JpaRepository<Entité, IdType>`.

### Liste des repositories

| Repository | Entité | ID Type |
|------------|--------|---------|
| `PaysRepository` | Pays | Long |
| `NationaliteRepository` | Nationalite | Long |
| `SexeRepository` | Sexe | Long |
| `SituationFamilialeRepository` | SituationFamiliale | Long |
| `TypeDemandeVisaRepository` | TypeDemandeVisa | Long |
| `TypeVisaRepository` | TypeVisa | Long |
| `DocumentsCommunRepository` | DocumentsCommun | Long |
| `DocumentsTypeRepository` | DocumentsType | Long |
| `DemandeurRepository` | Demandeur | Long |
| `EtatCivilRepository` | EtatCivil | Long |
| `PasseportRepository` | Passeport | Long |
| `VisaTransformableRepository` | VisaTransformable | Long |
| `DemandeDocumentsTypeRepository` | DemandeDocumentsType | Long |
| `DemandeDocumentsCommunRepository` | DemandeDocumentsCommun | Long |
| `TypeVisaDocumentsRepository` | TypeVisaDocuments | TypeVisaDocumentsId |

### Utilisation des repositories

#### Injection dans les services
```java
@Service
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    @Autowired
    private EtatCivilRepository etatCivilRepository;
}
```

#### Opérations CRUD de base
```java
// Créer
Demandeur demandeur = Demandeur.builder()
    .code("DEM001")
    .typeVisa(typeVisa)
    .typeDemandeVisa(typeDemandeVisa)
    .build();
demandeurRepository.save(demandeur);

// Lire
Optional<Demandeur> demandeur = demandeurRepository.findById(1L);

// Mettre à jour
demandeur.setCode("DEM002");
demandeurRepository.save(demandeur);

// Supprimer
demandeurRepository.deleteById(1L);

// Récupérer tous
List<Demandeur> demandeurs = demandeurRepository.findAll();
```

---

## Utilisation des entités

### Créer un demandeur complet

```java
@Service
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    @Autowired
    private EtatCivilRepository etatCivilRepository;
    @Autowired
    private TypeVisaRepository typeVisaRepository;
    @Autowired
    private TypeDemandeVisaRepository typeDemandeVisaRepository;
    
    public void creerDemandeurComplet() {
        // 1. Créer les références
        TypeVisa typeVisa = typeVisaRepository.findById(1L).orElse(null);
        TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(1L).orElse(null);
        
        // 2. Créer le demandeur
        Demandeur demandeur = Demandeur.builder()
            .code("DEM001")
            .typeVisa(typeVisa)
            .typeDemandeVisa(typeDemandeVisa)
            .build();
        Demandeur saved = demandeurRepository.save(demandeur);
        
        // 3. Créer l'état civil
        EtatCivil etatCivil = EtatCivil.builder()
            .nom("Dupont")
            .prenom("Jean")
            .dateNaissance(LocalDate.of(1990, 1, 15))
            .email("jean@example.com")
            .demandeur(saved)
            .build();
        etatCivilRepository.save(etatCivil);
    }
}
```

### Annotations Lombok utilisées

| Annotation | Description |
|-----------|-------------|
| `@Data` | Génère getters, setters, equals, hashCode, toString |
| `@NoArgsConstructor` | Génère un constructeur sans arguments |
| `@AllArgsConstructor` | Génère un constructeur avec tous les champs |
| `@Builder` | Génère le pattern Builder |

---

## Modèle relationnel

### Diagramme des relations

```
Pays
  ↑
  └─── EtatCivil ──────────→ Passeport
       ↑ ↑ ↑ ↑
       │ │ │ └──→ VisaTransformable
       │ │ │
       │ │ └──→ Sexe
       │ │
       │ └──→ Nationalite
       │
       └──→ SituationFamiliale

Demandeur
  ↑
  ├─→ TypeVisa ──────────→ DocumentsType (via TypeVisaDocuments)
  │
  ├─→ TypeDemandeVisa
  │
  ├─→ EtatCivil
  │
  ├─→ DemandeDocumentsType ──────→ DocumentsType
  │
  └─→ DemandeDocumentsCommun ──────→ DocumentsCommun
```

### Cardinalités principales

| Relation | Cardinalité | Description |
|----------|-------------|-------------|
| Demandeur → EtatCivil | 1:1 | Un demandeur a exactement un état civil |
| EtatCivil → Passeport | 1:1 | Un état civil a au maximum un passeport |
| EtatCivil → VisaTransformable | 1:1 | Un état civil a au maximum un visa transformable |
| Demandeur → DocumentsType | N:M | Un demandeur peut avoir plusieurs documents types |
| Demandeur → DocumentsCommun | N:M | Un demandeur peut avoir plusieurs documents communs |
| TypeVisa → DocumentsType | N:M | Un type de visa requiert plusieurs documents types |

---

## Stratégies de chargement (Fetch Strategies)

### Lazy Loading (Défaut)
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_type_visa", nullable = false)
private TypeVisa typeVisa;
```
- Charge les données uniquement quand accédées
- Réduit le trafic de base de données
- Attention à la LazyInitializationException en dehors du contexte de session

### Eager Loading (Cas particuliers)
```java
@OneToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_etat_civil", nullable = false, unique = true)
private EtatCivil etatCivil;
```
- Charge les données immédiatement
- Peut causer des problèmes N+1

---

## Bonnes pratiques

1. **Toujours utiliser les IDs** pour les relations plutôt que les objets complets
2. **Lazy loading par défaut** sauf besoin spécifique
3. **Utiliser les builders** pour créer les entités
4. **Valider les données** avec `@NotNull`, `@NotBlank`, etc.
5. **Gérer les transactions** au niveau du service
6. **Utiliser les DTOs** pour les retours API (à implémenter)

---

## Prochaines étapes

1. **Créer les services métier** pour chaque entité
2. **Implémenter les contrôleurs REST** pour les opérations CRUD
3. **Ajouter les validations** avec Jakarta Validation
4. **Implémenter les requêtes personnalisées** dans les repositories
5. **Ajouter la pagination et le tri**
6. **Créer les DTOs** pour l'API REST

---

## Dépannage

### Erreur : Cannot resolve symbol 'jakarta'
- **Solution** : Utilisez Spring Boot 3.0+ qui utilise jakarta.* au lieu de javax.*

### Erreur : LazyInitializationException
- **Solution** : Utiliser `@Transactional` sur les méthodes du service ou changer en `FetchType.EAGER`

### Erreur : Driver not found for PostgreSQL
- **Solution** : Assurez-vous que la dépendance PostgreSQL est dans pom.xml et que la version de PostgreSQL est installée

### Erreur : Database connection refused
- **Solution** : 
  - Vérifiez que PostgreSQL est en cours d'exécution
  - Vérifiez l'URL, l'utilisateur et le mot de passe dans application.properties
  - Vérifiez que la base de données existe

---

**Dernière mise à jour** : 23/04/2026
**Auteur** : Équipe de développement
**Version** : 1.0
