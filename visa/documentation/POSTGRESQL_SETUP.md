# Guide de Configuration PostgreSQL

## Installation et Configuration initiale

### 1. Installer PostgreSQL

#### Sur Windows
- Téléchargez l'installateur depuis [postgresql.org](https://www.postgresql.org/download/windows/)
- Lancez l'installateur et suivez les instructions
- **Important** : Notez le mot de passe défini pour l'utilisateur `postgres`

#### Sur macOS
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Sur Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
```

### 2. Vérifier l'installation

```bash
psql --version
```

---

## Configuration de la base de données pour le projet

### 1. Se connecter à PostgreSQL

```bash
psql -U postgres
```

Vous serez invité à entrer le mot de passe `postgres`.

### 2. Créer la base de données

```sql
CREATE DATABASE visa_db;
```

Pour vérifier :
```sql
\l
```

### 3. Exécuter le script SQL

Quittez psql avec `\q`, puis exécutez :

```bash
psql -U postgres -d visa_db -f base/script/script-23-04-2026.sql
```

Ou depuis psql :
```sql
\c visa_db
\i base/script/script-23-04-2026.sql
```

### 4. Vérifier les tables créées

```bash
psql -U postgres -d visa_db -c "\dt"
```

---

## Configuration de l'application

### 1. Mettre à jour application.properties

Fichier : `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/visa_db
spring.datasource.username=postgres
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

⚠️ **Sécurité** : Ne commitez pas les vraies identifiants en production. Utilisez les variables d'environnement.

### 2. Configuration avancée (optionnel)

Pour une sécurité accrue en production :

```properties
# Utiliser des variables d'environnement
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/visa_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

# Pool de connexions
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.connection-timeout=20000
```

---

## Vérification du fonctionnement

### 1. Démarrer l'application

```bash
mvn spring-boot:run
```

### 2. Logs de vérification

Cherchez dans les logs :
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed
```

Cela indique que la connexion à la base de données est établie.

### 3. Tester les endpoints (une fois implémentés)

```bash
curl http://localhost:8080/api/demandeurs
```

---

## Commandes PostgreSQL utiles

### Accès à la base de données

```bash
# Connexion
psql -U postgres -d visa_db

# Se connecter à une base
\c visa_db

# Lister les tables
\dt

# Décrire une table
\d demandeur

# Lister toutes les bases
\l

# Quitter
\q
```

### Requêtes courantes

```sql
-- Afficher toutes les données
SELECT * FROM demandeur;

-- Compter les demandeurs
SELECT COUNT(*) FROM demandeur;

-- Ajouter des données de test
INSERT INTO type_visa (libelle) VALUES ('Tourisme');
INSERT INTO type_demande_visa (libelle) VALUES ('Nouvelle demande');

-- Supprimer les données (attention !)
DELETE FROM demandeur WHERE id_demandeur = 1;

-- Afficher la structure d'une table
\d etat_civil
```

---

## Troubleshooting

### Erreur : FATAL: role "postgres" does not exist

**Cause** : PostgreSQL n'est pas correctement installé ou démarré

**Solution** :
```bash
# Windows
pg_ctl -D "C:\Program Files\PostgreSQL\15\data" start

# macOS
brew services start postgresql@15

# Linux
sudo systemctl start postgresql
```

### Erreur : FATAL: database "visa_db" does not exist

**Solution** :
```bash
psql -U postgres -c "CREATE DATABASE visa_db;"
```

### Erreur : Connection refused

**Cause** : PostgreSQL n'est pas en cours d'exécution ou sur un port différent

**Solution** :
```bash
# Vérifier l'état
pg_isready

# Ou vérifier le port
netstat -an | grep 5432

# Modifier application.properties si sur un port différent
spring.datasource.url=jdbc:postgresql://localhost:5433/visa_db
```

### Erreur : password authentication failed

**Cause** : Mot de passe incorrect

**Solution** :
1. Réinitialiser le mot de passe PostgreSQL
2. Ou utiliser `trust` en développement (attention à la sécurité !)

Pour changer le mot de passe :
```bash
psql -U postgres -c "ALTER USER postgres WITH PASSWORD 'nouveau_mot_de_passe';"
```

---

## Dump et restauration de la base de données

### Créer une sauvegarde

```bash
pg_dump -U postgres visa_db > backup_visa_db.sql
```

### Restaurer une sauvegarde

```bash
psql -U postgres -d visa_db < backup_visa_db.sql
```

---

## Configuration pour développement local vs production

### Développement (application-dev.properties)
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE
```

### Production (application-prod.properties)
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.root=WARN
```

Pour activer un profil :
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## Utiliser pgAdmin pour gérer la base de données (Optionnel)

### Installation

```bash
# Docker (recommandé)
docker run -p 80:80 -e 'PGADMIN_DEFAULT_EMAIL=admin@example.com' -e 'PGADMIN_DEFAULT_PASSWORD=admin' dpage/pgadmin4
```

### Accès

- URL : http://localhost
- Email : admin@example.com
- Mot de passe : admin

### Ajouter une connexion

1. Clic droit sur "Servers"
2. Ajouter un nouveau serveur
3. Nom : "Local"
4. Onglet "Connection"
   - Hôte : localhost
   - Port : 5432
   - Username : postgres
   - Password : [votre mot de passe]

---

**Document créé le** : 23/04/2026
