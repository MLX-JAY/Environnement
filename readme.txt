================================================================================
  PROJET ENVIRONNEMENT — Simulateur d'Écosystème Dynamique en Java
  CY Cergy Paris Université — Licence 2 Informatique
  Génie Logiciel et Conception de Logiciels — Année 2025-2026
================================================================================

  Auteurs : Feraoun Mohamed Amine
            Anurajan Thenuxshan
            Ouardani Nidhal

  Encadrant : Tianxiao LIU

  Dépôt GitHub : https://github.com/MLX-JAY/Environnement

================================================================================
  PRÉREQUIS
================================================================================

Avant de lancer le projet, vérifiez que les éléments suivants sont installés
sur votre machine :

  - Java Development Kit (JDK) 8
  - Eclipse IDE (recommandé : Eclipse IDE for Java Developers, version 2021-09
    ou ultérieure)
  - Résolution d'écran : 1920 x 1080 pixels minimum recommandée

  Bibliothèques JAR requises (à placer dans un dossier lib/ ou équivalent) :

    - jcommon.jar        : utilitaires communs JFreeChart
    - jfreechart.jar     : génération de graphiques 2D
    - jfreesvg.jar       : export des graphiques au format SVG
    - swtgraphics2d.jar  : pont entre SWT et Graphics2D
    - log4j.jar          : journalisation applicative
    - junit.jar          : framework de tests unitaires
    - hamcrest-core.jar  : assertions pour les tests unitaires

  Systèmes d'exploitation compatibles :
    - Windows 10 ou ultérieur
    - GNU/Linux (distributions Debian et dérivées)
    - macOS 12 ou ultérieur

================================================================================
  ÉTAPE 1 — RÉCUPÉRER LE CODE SOURCE
================================================================================

  Option A — Via GitHub Desktop (recommandée) :
  -----------------------------------------------
  1. Ouvrez GitHub Desktop.
  2. Allez dans : File > Clone repository
  3. Sélectionnez l'onglet "URL".
  4. Collez l'URL du dépôt : https://github.com/MLX-JAY/Environnement
  5. Choisissez le dossier de destination, puis cliquez sur "Clone".

  Option B — Téléchargement direct (sans Git) :
  -----------------------------------------------
  1. Rendez-vous sur : https://github.com/MLX-JAY/Environnement
  2. Cliquez sur "Code" > "Download ZIP".
  3. Extrayez l'archive ZIP dans le dossier de votre choix.

================================================================================
  ÉTAPE 2 — IMPORTER LE PROJET DANS ECLIPSE
================================================================================

  1. Lancez Eclipse et choisissez un workspace.
  2. Dans la barre de menu, allez dans :
         File > Import...
  3. Sélectionnez : General > Existing Projects into Workspace
     Puis cliquez sur "Next".
  4. Cliquez sur "Browse..." et sélectionnez le dossier racine du projet
     (celui contenant le dossier "src").
  5. Le projet "Environnement" apparaît dans la liste. Cochez-le.
  6. Cliquez sur "Finish".

================================================================================
  ÉTAPE 3 — AJOUTER LES BIBLIOTHÈQUES AU BUILD PATH
================================================================================

  1. Dans l'explorateur de projets (Package Explorer), faites un clic droit
     sur le projet "Environnement".
  2. Sélectionnez : Properties
  3. Dans le panneau de gauche, cliquez sur : Java Build Path
  4. Allez dans l'onglet : Libraries
  5. Cliquez sur "Add External JARs..."
  6. Naviguez jusqu'au dossier contenant les fichiers JAR listés dans la
     section "PRÉREQUIS" ci-dessus, sélectionnez-les tous, puis cliquez
     sur "Open".
  7. Cliquez sur "Apply and Close".

  Vérification : aucune erreur de compilation ne doit apparaître dans le
  projet. Si des erreurs persistent, vérifiez que tous les JAR ont bien
  été ajoutés.

================================================================================
  ÉTAPE 4 — COMPILER LE PROJET
================================================================================

  La compilation est automatique dans Eclipse si l'option "Build Automatically"
  est activée (menu Project > Build Automatically).

  Si ce n'est pas le cas, compilez manuellement via :
         Project > Build Project

================================================================================
  ÉTAPE 5 — LANCER L'APPLICATION
================================================================================

  1. Dans le Package Explorer, localisez le fichier :
         src/ ... /Environnement.java
     (il s'agit du fichier contenant la méthode main)
  2. Faites un clic droit sur ce fichier.
  3. Sélectionnez : Run As > Java Application
  4. L'interface graphique s'ouvre automatiquement après quelques instants.

  Alternative — Lancement depuis un terminal (si un JAR a été exporté) :
         java -jar /chemin/vers/Environnement.jar

================================================================================
  UTILISATION RAPIDE
================================================================================

  Au lancement, deux fenêtres se succèdent :

  1. Fenêtre d'ÉDITION :
     - Configurez les probabilités d'apparition des événements (menu gauche).
     - Générez une carte aléatoire via le bouton "Générer Carte", ou
       cliquez sur chaque cellule pour choisir manuellement les biomes.
     - Cliquez sur "Lancer la simulation" pour démarrer.

  2. Fenêtre de SIMULATION :
     - Play    : démarre ou reprend la simulation.
     - Pause   : suspend l'exécution à la fin du round en cours.
     - Vitesse x2 : double la cadence d'enchaînement des rounds.
     - Statistiques : ouvre la fenêtre d'analyse graphique en temps réel.
     - Tutoriel : affiche les règles de fonctionnement du simulateur.
     - Bilan de fin : termine la simulation et affiche le récapitulatif.

================================================================================
  JOURNAUX D'EXÉCUTION
================================================================================

  Les journaux de la session sont écrits dans :
         src/logs/simulation.log

  Chaque entrée comporte un horodatage, un niveau de sévérité (INFO ou DEBUG)
  et un message décrivant l'action enregistrée (démarrage de round,
  transformation de biome, déplacement d'événement, anomalie détectée).

================================================================================
  STRUCTURE DU PROJET
================================================================================

  Environnement/
  ├── src/
  │   ├── moteur/      <- Logique métier : carte, biomes, événements, règles
  │   ├── gui/         <- Interface graphique (Swing)
  │   ├── config/      <- Paramètres de simulation (probabilités, seuils)
  │   ├── util/        <- Utilitaires transversaux (Log4j, LoggerUtility)
  │   └── logs/
  │       └── simulation.log
  ├── lib/             <- Fichiers JAR des bibliothèques
  └── readme.txt       <- Ce fichier

================================================================================
