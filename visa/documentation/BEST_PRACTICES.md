# Guide des Bonnes Pratiques et Patterns 📚

## Patterns d'architecture utilisés

### 1. Repository Pattern ✅

**Déjà implémenté** dans le projet.

```java
// Utilisation
@Service
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    public List<Demandeur> getAllDemandeurs() {
        return demandeurRepository.findAll();
    }
}
```

**Avantages** :
- Abstraction de la base de données
- Facilite les tests (mock le repository)
- Une seule source de vérité pour les requêtes

---

### 2. Service Layer Pattern (À implémenter)

```java
@Service
@Transactional
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    @Autowired
    private EtatCivilRepository etatCivilRepository;
    
    // Logique métier
    public Demandeur creerDemandeurComplet(
            Demandeur demandeur, 
            EtatCivil etatCivil) {
        
        // Validation métier
        if (!isValidCode(demandeur.getCode())) {
            throw new IllegalArgumentException("Code invalide");
        }
        
        // Sauvegarde atomique
        Demandeur savedDemandeur = demandeurRepository.save(demandeur);
        etatCivil.setDemandeur(savedDemandeur);
        etatCivilRepository.save(etatCivil);
        
        return savedDemandeur;
    }
    
    private boolean isValidCode(String code) {
        return code != null && code.matches("^DEM\\d{3,}$");
    }
}
```

**Caractéristiques** :
- `@Service` : Marque comme service métier
- `@Transactional` : Gère les transactions automatiquement
- Logique métier centralisée
- Réutilisable par les contrôleurs et autres services

---

### 3. DTO Pattern (À implémenter)

```java
// DTO (Data Transfer Object)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeurDTO {
    private Long idDemandeur;
    private String code;
    private Long idTypeVisa;
    private Long idTypeDemandeVisa;
}

// Mapper
@Component
public class DemandeurMapper {
    public DemandeurDTO toDTO(Demandeur demandeur) {
        return new DemandeurDTO(
            demandeur.getIdDemandeur(),
            demandeur.getCode(),
            demandeur.getTypeVisa().getIdTypeVisa(),
            demandeur.getTypeDemandeVisa().getIdTypeDemandeVisa()
        );
    }
    
    public Demandeur toEntity(DemandeurDTO dto, 
                              TypeVisaRepository typeVisaRepo,
                              TypeDemandeVisaRepository typeDemandeVisaRepo) {
        return Demandeur.builder()
            .idDemandeur(dto.getIdDemandeur())
            .code(dto.getCode())
            .typeVisa(typeVisaRepo.findById(dto.getIdTypeVisa()).orElse(null))
            .typeDemandeVisa(typeDemandeVisaRepo.findById(dto.getIdTypeDemandeVisa()).orElse(null))
            .build();
    }
}
```

**Avantages** :
- Sépare API interne du modèle de données
- Contrôle ce qui est exposé
- Permet de filtrer les données sensibles
- Facilite la versioning de l'API

---

### 4. Controller Pattern (À implémenter)

```java
@RestController
@RequestMapping("/api/demandeurs")
@Slf4j
public class DemandeurController {
    
    @Autowired
    private DemandeurService demandeurService;
    
    @Autowired
    private DemandeurMapper demandeurMapper;
    
    // GET tous les demandeurs
    @GetMapping
    public ResponseEntity<List<DemandeurDTO>> getAllDemandeurs() {
        List<Demandeur> demandeurs = demandeurService.getAllDemandeurs();
        List<DemandeurDTO> dtos = demandeurs.stream()
            .map(demandeurMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    // GET un demandeur par ID
    @GetMapping("/{id}")
    public ResponseEntity<DemandeurDTO> getDemandeurById(@PathVariable Long id) {
        return demandeurService.getDemandeurById(id)
            .map(d -> ResponseEntity.ok(demandeurMapper.toDTO(d)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST créer un demandeur
    @PostMapping
    public ResponseEntity<DemandeurDTO> createDemandeur(
            @Valid @RequestBody DemandeurDTO dto) {
        Demandeur demandeur = demandeurMapper.toEntity(dto, ...);
        Demandeur created = demandeurService.saveDemandeur(demandeur);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(demandeurMapper.toDTO(created));
    }
    
    // PUT mettre à jour
    @PutMapping("/{id}")
    public ResponseEntity<DemandeurDTO> updateDemandeur(
            @PathVariable Long id,
            @Valid @RequestBody DemandeurDTO dto) {
        Demandeur updated = demandeurService.updateDemandeur(id, dto);
        return ResponseEntity.ok(demandeurMapper.toDTO(updated));
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeur(@PathVariable Long id) {
        demandeurService.deleteDemandeur(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Utilisation des annotations

### JPA Annotations

```java
// Entité
@Entity
@Table(name = "demandeur")
public class Demandeur {
    
    // Clé primaire avec auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur")
    private Long idDemandeur;
    
    // Colonne simple
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;
    
    // Relation Many-to-One
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;
    
    // Relation One-to-One
    @OneToOne(mappedBy = "demandeur", cascade = CascadeType.ALL)
    private EtatCivil etatCivil;
}
```

### Validation Annotations

```java
@Entity
@Table(name = "demandeur")
public class Demandeur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemandeur;
    
    // Validation
    @NotBlank(message = "Le code ne peut pas être vide")
    @Pattern(regexp = "^DEM\\d{3,}$", 
             message = "Le code doit être au format DEMXXX")
    @Column(name = "code", length = 50)
    private String code;
    
    @NotNull(message = "Le type de visa est obligatoire")
    @ManyToOne
    @JoinColumn(name = "id_type_visa")
    private TypeVisa typeVisa;
}
```

### Spring Annotations

```java
// Service avec transaction
@Service
@Transactional
public class DemandeurService {
    
    // Injection de dépendance
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    // Méthode transactionnelle
    @Transactional
    public void creerDemandeur(Demandeur demandeur) {
        // Modifications à la base de données
        demandeurRepository.save(demandeur);
    }
}
```

---

## Gestion des erreurs

### Exception personnalisée

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
```

### Contrôleur d'exception global

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            message,
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

// Modèle d'erreur
@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
```

---

## Bonnes pratiques JPA

### 1. Utiliser les relations correctement

```java
// ❌ MAUVAIS - Lazy Loading hors contexte de session
Demandeur demandeur = demandeurRepository.findById(1L).orElse(null);
// LazyInitializationException ici !
String typeVisaLibelle = demandeur.getTypeVisa().getLibelle();

// ✅ BON - Eager Loading ou Transactionnel
@Transactional(readOnly = true)
public Demandeur getDemandeurWithVisa(Long id) {
    return demandeurRepository.findById(id)
        .map(d -> {
            // Le TypeVisa est chargé dans le contexte de session
            d.getTypeVisa().getLibelle(); // OK
            return d;
        })
        .orElse(null);
}

// ✅ BON - Utiliser les requêtes personnalisées
@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
    @Query("SELECT d FROM Demandeur d LEFT JOIN FETCH d.typeVisa WHERE d.id = ?1")
    Optional<Demandeur> findByIdWithVisa(Long id);
}
```

### 2. Utiliser les requêtes personnalisées

```java
@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
    
    // Requête JPQL
    @Query("SELECT d FROM Demandeur d WHERE d.code = ?1")
    Optional<Demandeur> findByCode(String code);
    
    // Requête SQL native
    @Query(value = "SELECT * FROM demandeur WHERE id_type_visa = ?1", 
           nativeQuery = true)
    List<Demandeur> findByTypeVisa(Long typeVisaId);
    
    // Derived queries (Spring génère la requête)
    List<Demandeur> findByCodeContaining(String code);
    Optional<Demandeur> findByCodeAndTypeVisa(String code, TypeVisa typeVisa);
}
```

### 3. Pagination et tri

```java
@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
    Page<Demandeur> findAll(Pageable pageable);
}

// Utilisation
@Service
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    public Page<Demandeur> getAllDemandeurs(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
            page, 
            size, 
            Sort.by("code").ascending()
        );
        return demandeurRepository.findAll(pageRequest);
    }
}

// Dans le contrôleur
@GetMapping
public ResponseEntity<Page<DemandeurDTO>> getAllDemandeurs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(
        demandeurService.getAllDemandeurs(page, size)
            .map(demandeurMapper::toDTO)
    );
}
```

### 4. Gestion des transactions

```java
// Au niveau du service
@Service
@Transactional
public class DemandeurService {
    
    // Hérite de @Transactional du service
    public Demandeur saveDemandeur(Demandeur demandeur) {
        return demandeurRepository.save(demandeur);
    }
    
    // Requête en lecture seule
    @Transactional(readOnly = true)
    public Demandeur getDemandeur(Long id) {
        return demandeurRepository.findById(id).orElse(null);
    }
    
    // Pas de transaction (lecture seule)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void logAccess(Long demandeurId) {
        // Logging sans transaction
    }
}
```

---

## Logging

```java
@Service
@Slf4j  // Lomboks crée automatiquement le logger
public class DemandeurService {
    
    public Demandeur saveDemandeur(Demandeur demandeur) {
        log.info("Création d'un nouveau demandeur avec code: {}", demandeur.getCode());
        try {
            Demandeur saved = demandeurRepository.save(demandeur);
            log.info("Demandeur créé avec succès: {}", saved.getIdDemandeur());
            return saved;
        } catch (Exception e) {
            log.error("Erreur lors de la création du demandeur", e);
            throw new IllegalArgumentException("Impossible de créer le demandeur", e);
        }
    }
}
```

---

## Structure recommandée du projet

```
src/main/java/com/itu/visa/
├── config/              # Configuration Spring
│   └── ApplicationConfig.java
├── controller/          # Contrôleurs REST
│   ├── DemandeurController.java
│   └── EtatCivilController.java
├── entity/             # Entités JPA ✅ DÉJÀ CRÉÉ
│   ├── Demandeur.java
│   └── ...
├── repository/         # Repositories ✅ DÉJÀ CRÉÉ
│   ├── DemandeurRepository.java
│   └── ...
├── service/           # Logique métier
│   ├── DemandeurService.java
│   └── EtatCivilService.java
├── dto/              # Data Transfer Objects
│   ├── DemandeurDTO.java
│   └── EtatCivilDTO.java
├── mapper/           # Mappage Entity ↔ DTO
│   ├── DemandeurMapper.java
│   └── EtatCivilMapper.java
├── exception/        # Exceptions personnalisées
│   ├── ResourceNotFoundException.java
│   └── InvalidDataException.java
├── handler/         # Exception handlers
│   └── GlobalExceptionHandler.java
└── VisaApplication.java  # Classe principale
```

---

## Checklist d'une implémentation complète

Pour chaque entité principale (Demandeur, EtatCivil, Passeport, etc.) :

- [ ] Repository créé ✅ (déjà fait)
- [ ] Service créé avec logique métier
- [ ] Service testé unitairement
- [ ] DTO créé
- [ ] Mapper créé
- [ ] Contrôleur créé
  - [ ] GET /api/xxx (tous)
  - [ ] GET /api/xxx/{id} (un)
  - [ ] POST /api/xxx (créer)
  - [ ] PUT /api/xxx/{id} (mettre à jour)
  - [ ] DELETE /api/xxx/{id} (supprimer)
- [ ] Endpoints testés avec Postman/Insomnia
- [ ] Validation ajoutée
- [ ] Documentation Swagger générée
- [ ] Tests d'intégration

---

## Ressources supplémentaires

### Documentation
- [Spring Framework Best Practices](https://spring.io/guides/gs/accessing-data-jpa/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)

### Outils
- Postman : https://www.postman.com/
- Swagger Editor : https://editor.swagger.io/
- Lombok : https://projectlombok.org/

---

**Dernière mise à jour** : 23/04/2026
