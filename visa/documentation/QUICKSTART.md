# Guide de démarrage rapide 🚀

## Pour ceux qui reprennent le projet

### Étape 1 : Cloner et configurer (5 min)

```bash
# 1. Ouvrir le projet dans VS Code/IntelliJ
# 2. Les entités et repositories sont déjà créés ✅

# 3. Installer les dépendances Maven
mvn clean install
```

### Étape 2 : Configurer PostgreSQL (10 min)

```bash
# 1. Installer PostgreSQL si pas déjà fait
# https://www.postgresql.org/download/

# 2. Créer la base de données
psql -U postgres -c "CREATE DATABASE visa_db;"

# 3. Exécuter le script SQL
psql -U postgres -d visa_db -f base/script/script-23-04-2026.sql

# 4. Vérifier les tables
psql -U postgres -d visa_db -c "\dt"
```

### Étape 3 : Configurer l'application (2 min)

**Fichier** : `src/main/resources/application.properties`

```properties
# Remplacer cette ligne :
spring.datasource.password=your_password_here

# Par votre mot de passe PostgreSQL :
spring.datasource.password=votre_mot_de_passe
```

### Étape 4 : Démarrer l'application (5 min)

```bash
# Option 1 : Via Maven
mvn spring-boot:run

# Option 2 : Via l'IDE
# Clic droit sur VisaApplication.java → Run

# Option 3 : Build et JAR
mvn clean package
java -jar target/visa-0.0.1-SNAPSHOT.jar
```

**✅ L'application devrait démarrer sans erreurs**

Vous verrez dans les logs :
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
```

---

## Structure du code existant

### 📁 Entités JPA (`src/main/java/com/itu/visa/entity/`)

Toutes les entités sont créées et annotées avec JPA :

- **Entités de lookup** : Pays, Nationalite, Sexe, SituationFamiliale, TypeVisa, TypeDemandeVisa, DocumentsCommun, DocumentsType
- **Entités principales** : Demandeur, EtatCivil, Passeport, VisaTransformable
- **Entités de jonction** : DemandeDocumentsType, DemandeDocumentsCommun, TypeVisaDocuments

### 📁 Repositories (`src/main/java/com/itu/visa/repository/`)

Chaque entité a un repository prêt à utiliser :

```java
@Autowired
private DemandeurRepository demandeurRepository;

// Utilisation
List<Demandeur> all = demandeurRepository.findAll();
Optional<Demandeur> one = demandeurRepository.findById(1L);
Demandeur saved = demandeurRepository.save(demandeur);
```

---

## Ce qu'il faut faire maintenant

### Phase 1️⃣ : Créer les Services (1-2 jours)

```java
src/main/java/com/itu/visa/service/
├── DemandeurService.java
├── EtatCivilService.java
├── PasseportService.java
└── ...
```

**Exemple** :
```java
@Service
public class DemandeurService {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    public List<Demandeur> getAllDemandeurs() {
        return demandeurRepository.findAll();
    }
    
    public Demandeur saveDemandeur(Demandeur demandeur) {
        return demandeurRepository.save(demandeur);
    }
}
```

### Phase 2️⃣ : Créer les Contrôleurs REST (1-2 jours)

```java
src/main/java/com/itu/visa/controller/
├── DemandeurController.java
├── EtatCivilController.java
└── ...
```

**Exemple** :
```java
@RestController
@RequestMapping("/api/demandeurs")
public class DemandeurController {
    @Autowired
    private DemandeurService demandeurService;
    
    @GetMapping
    public List<Demandeur> getAllDemandeurs() {
        return demandeurService.getAllDemandeurs();
    }
    
    @PostMapping
    public Demandeur createDemandeur(@RequestBody Demandeur demandeur) {
        return demandeurService.saveDemandeur(demandeur);
    }
}
```

### Phase 3️⃣ : Créer les DTOs (Optionnel mais recommandé) (1 jour)

```java
src/main/java/com/itu/visa/dto/
├── DemandeurDTO.java
├── EtatCivilDTO.java
└── ...
```

**Avantages** :
- Sépare l'API interne du modèle de base de données
- Contrôle ce qui est exposé à l'extérieur
- Permet de filtrer les données sensibles

---

## Checklist du développement

- [ ] PostgreSQL installé et configuré
- [ ] Base de données créée et remplie
- [ ] `application.properties` mis à jour
- [ ] Application démarre sans erreurs
- [ ] Services créés pour chaque entité principale
- [ ] Contrôleurs REST implémentés
- [ ] DTOs créés (optionnel)
- [ ] Tests unitaires écrits
- [ ] Tests d'intégration passant
- [ ] Documentation API (Swagger/OpenAPI)

---

## Tests rapides

### Test 1 : Vérifier la connexion à la base

```bash
# Avec psql
psql -U postgres -d visa_db

# Vérifier les données
SELECT COUNT(*) FROM type_visa;
SELECT * FROM type_visa;

\q  # Quitter
```

### Test 2 : Vérifier le démarrage de l'application

```bash
mvn clean compile
mvn spring-boot:run
```

Cherchez dans les logs : "Tomcat started on port 8080"

### Test 3 : Tester les repositories (une fois créés)

Créer une classe de test :

```java
@SpringBootTest
public class DemandeurRepositoryTest {
    @Autowired
    private DemandeurRepository demandeurRepository;
    
    @Test
    public void testFindAll() {
        List<Demandeur> demandeurs = demandeurRepository.findAll();
        assertNotNull(demandeurs);
    }
}
```

---

## Erreurs courantes et solutions

### Erreur : "FATAL: role \"postgres\" does not exist"
→ PostgreSQL non démarré ou mal installé

**Solution** : Redémarrer PostgreSQL
```bash
brew services stop postgresql && brew services start postgresql
```

### Erreur : "database \"visa_db\" does not exist"
→ Base de données non créée

**Solution** :
```bash
psql -U postgres -c "CREATE DATABASE visa_db;"
psql -U postgres -d visa_db -f base/script/script-23-04-2026.sql
```

### Erreur : "Cannot resolve symbol 'jakarta'"
→ Version Spring Boot < 3.0

**Solution** : Vérifier `pom.xml` pour Spring Boot 3.0+
Le projet utilise la version 4.0.5 ✅

### Erreur : "Hibernate HHH000187: HikariPool-1 - Exception during pool initialization"
→ Mot de passe PostgreSQL incorrect

**Solution** : Vérifier `application.properties`

### Erreur : "Port 8080 already in use"
→ Une autre application utilise le port 8080

**Solution** : Changer le port dans `application.properties`
```properties
server.port=8081
```

---

## Documentation complète

📖 Consulter les fichiers de documentation :

1. **JPA_ARCHITECTURE.md** (35 pages)
   - Explication complète de toutes les entités
   - Diagrammes des relations
   - Exemples d'utilisation
   - Bonnes pratiques

2. **POSTGRESQL_SETUP.md** (5 pages)
   - Installation PostgreSQL
   - Configuration étape par étape
   - Troubleshooting
   - Commandes utiles

3. **README_MODIFICATIONS.md** (cette page)
   - Résumé des changements
   - Structure du projet
   - Prochaines étapes

---

## Ressources utiles

### Documentation officielle
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring REST Docs](https://spring.io/projects/spring-restdocs)
- [PostgreSQL Manual](https://www.postgresql.org/docs/)

### Outils recommandés
- **IntelliJ IDEA** : IDE professionnel pour Spring (recommandé)
- **VS Code** : Éditeur léger avec extensions
- **pgAdmin** : Interface graphique pour PostgreSQL
- **Postman/Insomnia** : Tester les APIs REST

### Libs utiles à ajouter (selon les besoins)
```xml
<!-- Swagger/OpenAPI pour documenter les APIs -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.2</version>
</dependency>

<!-- MapStruct pour mapper les DTOs -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
```

---

## Support et questions

Si vous rencontrez des problèmes :

1. ✅ Vérifiez la configuration dans `application.properties`
2. ✅ Vérifiez que PostgreSQL est en cours d'exécution
3. ✅ Vérifiez les logs de l'application
4. ✅ Consultez la documentation (JPA_ARCHITECTURE.md)
5. ✅ Google/Stack Overflow avec le message d'erreur exact

---

## Bon développement ! 🎉

L'architecture est mise en place, les entités et repositories sont créés.
Vous êtes prêt pour implémenter la logique métier.

**Durée estimée avant un MVP** : 3-5 jours de développement
- Jour 1-2 : Services métier
- Jour 2-3 : Contrôleurs REST  
- Jour 3-4 : DTOs et validations
- Jour 4-5 : Tests et ajustements

Bonne chance ! 💪

---

**Dernière mise à jour** : 23/04/2026
