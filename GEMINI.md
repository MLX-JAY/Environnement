# GEMINI.md - Projet Environnement

Ce document décrit le projet "Environnement", une simulation en Java d'un écosystème dynamique sur une carte.

## 1. Vue d'ensemble du projet

Le projet "Environnement" est une application de simulation basée sur une grille qui modélise l'interaction entre divers biomes et des événements environnementaux. La simulation progresse par "rounds" où les événements se déplacent et affectent les propriétés des biomes, pouvant entraîner des transformations de biomes.

## 2. Architecture

L'architecture du projet est structurée autour de plusieurs composants clés :

### 2.1. Moteur de Simulation (`moteur/`)

C'est le cœur logique de la simulation.

- **`Carte` (`moteur/donne/carte/Carte.java`):** Représente la grille de simulation. Elle est composée de `Bloc` (`moteur/donne/carte/Bloc.java`) qui sont les unités de base de la carte.
- **`Biome` (`moteur/donne/biome/Biome.java` et ses sous-classes):** Classes abstraites et concrètes (ex: `Foret`, `Desert`, `Mer`, `Village`) qui définissent les types de terrains. Chaque biome possède des propriétés comme la température, l'humidité, la pollution et la purification.
- **`Evenement` (`moteur/donne/evenement/Evenement.java` et ses sous-classes):** Classes abstraites et concrètes (ex: `Pluie`, `VentChaud`) qui représentent des phénomènes affectant les biomes. Ils ont une durée, une position et un impact sur les propriétés des biomes.
- **`Manageur` (`moteur/processus/Manageur.java` et `moteur/processus/ManageurBasique.java`):** L'interface `Manageur` définit les opérations pour gérer la simulation. `ManageurBasique` est son implémentation, responsable de l'initialisation de la carte (`CarteHasard`), de la gestion des événements (`ajouterEvenement`, `bougerEvenementMobile`) et de l'avancement de la simulation par rounds (`nextRound`, `transformation`).

### 2.2. Interface Utilisateur Graphique (GUI) (`gui/`)

Ce package contient les classes responsables de l'affichage de la simulation.

- **`MainGUI` (`gui/MainGUI.java`):** La fenêtre principale de l'application.
- **`MainDisplayer` (`gui/MainDisplayer.java`):** Le panneau principal où la carte et les biomes sont dessinés.
- **`PanelStatistique`, `PanelTemps`:** Affichent des informations ou des statistiques de la simulation.
- **`StrategiePeinture`:** Probablement une interface ou classe abstraite pour gérer différentes manières de dessiner.

### 2.3. Configuration (`config/`)

Contient les classes de configuration du jeu.

- **`GameConfiguration` (`config/GameConfiguration.java`):** Définit les constantes globales du jeu telles que la taille de la fenêtre, la taille des blocs et la vitesse de la simulation.
- **`ConfigurationBiome` (`config/ConfigurationBiome.java`):** Contient les valeurs par défaut ou les seuils pour les propriétés des différents biomes.
- **`ConfigurationEvenement` (`config/ConfigurationEvenement.java`):** Contient les caractéristiques des événements (ex: durée, impact).

## 3. Concepts Clés

- **Biomes:** Chaque `Bloc` sur la `Carte` est associé à un `Biome`. Les biomes interagissent avec les `Evenement` et peuvent se transformer si leurs propriétés atteignent certains seuils (ex: un biome avec 100% d'humidité peut devenir une `Mer`).
- **Événements:** Ils apparaissent sur la carte, se déplacent (s'ils sont mobiles) et modifient les propriétés des biomes avec lesquels ils partagent une position. Ils ont une durée de vie limitée.
- **Rounds de Simulation:** La simulation avance étape par étape. À chaque round, de nouveaux événements peuvent être ajoutés, les événements existants se déplacent, et leurs impacts sont appliqués aux biomes.

## 4. Comment exécuter le projet

Ce projet est une application Java standard.

1. **Compilation:** Assurez-vous d'avoir un JDK (Java Development Kit) installé.  
   Utilisez votre IDE (IntelliJ IDEA, Eclipse) pour compiler le projet, ou compilez-le depuis la ligne de commande :
