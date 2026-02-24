# Environnement

Le projet **Environnement** est une application de simulation développée en Java. Il permet de modéliser un écosystème dynamique sur une carte (grille), mettant en scène l'interaction entre divers types de terrains (biomes) et des événements climatiques ou géologiques.

## 🌟 Fonctionnalités Principales

* **Carte Basée sur une Grille :** Une modélisation spatiale où chaque bloc de la grille représente un type de biome spécifique.
* **Diversité des Biomes :** Gestion de multiples environnements tels que la Forêt, le Désert, la Mer, la Montagne, la Banquise, ainsi que des zones habitées comme les Villes et les Villages.
* **Événements Dynamiques :** Apparition d'événements environnementaux variés (Pluie, Vent Chaud/Froid, Météores, Séismes, Pollution, Purification). Certains sont statiques, d'autres se déplacent sur la carte au fil du temps.
* **Évolution par "Rounds" :** La simulation avance étape par étape. À chaque tour, les événements appliquent leurs effets (modification de la température, de l'humidité, etc.) sur les biomes touchés.
* **Transformations Écologiques :** En fonction des conditions (atteinte de seuils de température ou d'humidité), les biomes peuvent muter et se transformer en un autre type (ex: une zone trop humide devenant une mer).

## 🏗️ Architecture du Projet

Le code source est organisé autour de trois modules principaux :

* **`moteur/` (Moteur de Simulation) :** Le cœur du programme. Contient la logique de la `Carte`, la définition des `Biome` et des `Evenement`, ainsi que le `Manageur` qui orchestre le déroulement des rounds et l'application des règles.
* **`gui/` (Interface Graphique) :** Gère l'affichage visuel de la simulation pour l'utilisateur. Comprend la fenêtre principale (`MainGUI`), la zone de dessin de la carte (`MainDisplayer`), ainsi que des panneaux pour les statistiques et le temps.
* **`config/` (Configuration) :** Regroupe les paramètres globaux (dimensions, vitesse de simulation), ainsi que les seuils et caractéristiques par défaut des biomes et événements (`ConfigurationBiome`, `ConfigurationEvenement`).

## ⚙️ Prérequis

* **Java Development Kit (JDK) :** Version 8 ou supérieure recommandée.
* **Environnement de développement :** Un IDE comme IntelliJ IDEA, Eclipse, ou simplement des outils en ligne de commande.

## 🚀 Comment lancer la simulation

1. **Cloner ou télécharger** le code source du projet sur votre machine.
2. **Ouvrir** le projet dans votre IDE Java préféré (Eclipse, IntelliJ, etc.).
3. **Compiler** le projet pour générer les fichiers `.class` dans le répertoire `bin/`.
4. **Exécuter** la classe principale de lancement. Généralement, l'interface graphique peut être lancée via le fichier de test associé, par exemple : `test.TestGUI.java`.

---
*Ce projet a été développé dans le cadre d'un cours d'ingénierie logicielle et de conception orientée objet.*