# Partie Refactoring – Projet AutoLocation

## Objectif
Analyser et améliorer la qualité du projet avec SonarQube for IDE, puis effectuer les corrections principales du code.

## Étapes réalisées
1. Installation du plugin SonarQube for IDE dans JetBrains IntelliJ IDEA / JetBrains IU.
2. Activation de l'analyse automatique sur le dossier `src/main/java`.
3. Vérification des résultats avant refactoring : retours `orElse(null)`, méthodes mal nommées, gestion des erreurs limitée.
4. Refactoring des services : remplacement de tous les `orElse(null)` par `EntityNotFoundException`.
5. Ajout d'un `GlobalExceptionHandler` pour retourner des réponses HTTP structurées (`404`, `409`).
6. Conservation des associations bidirectionnelles lors des affectations.
7. Ajout du scanner Sonar Maven dans `pom.xml`.

## Problèmes corrigés
- Absence d'erreur explicite lorsqu'une entité n'existe pas.
- Réponses API non structurées en cas d'erreur.
- Risque de relation JPA incohérente entre les collections.
- Absence de configuration Sonar Maven dans le projet.

## Commandes
```powershell
.\mvnw.cmd -DskipTests compile
.\mvnw.cmd -DskipTests package
.\mvnw.cmd sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=<TOKEN>
```

## Résultat attendu dans Sonar
- Réduction des anomalies liées aux valeurs `null`.
- Réduction des problèmes de gestion des erreurs.
- Contrôle continu des services et contrôleurs.

## Captures à insérer dans le PDF
1. Liste des anomalies avant refactoring.
2. Analyse du service avec les anomalies signalées.
3. Même fichier après correction.
4. Résultat de la compilation Maven.
5. Résultat de l'analyse SonarQube ou du sonar-maven-plugin.

