# Versions (🇫🇷)
You can find this file in [english 🇬🇧](RELEASES_EN.md)

## 1.1 (Android 5.1.1 Lollipop et ultérieur)

### 1.1.1

Améliorations, Optimisations et Résolutions de problèmes:

* La lecture de la musique suivante lorsque il s'agit de la dernière musique (après activation du
  mode aléatoire) ne fait plus crasher l'application
* Le chargement des playlists est plus rapide au démarrage de l'application

### 1.1.0

Améliorations, Optimisations et Résolutions de problèmes:

* La taille de l'icône repeat one dans les paramètre de lecture par défaut s'affiche correctement
  sur les différentes tailles d'écran
* Mise à jour des librairies

### 1.1.0-beta-1

Nouvelles Fonctionnalités:

* Ajout d'un nouveau paramètre permettant d'utiliser l'Audio Offload (envoie de la lecture dans un
  processus dédié). Cette option permet d'optimiser la batterie
* Possiblité d'ajouter la musique en cours de lecture dans des playlists via les actions rapides
* Navigation vers les albums, genres, artistes, etc. depuis le menu d'un appui long sur une musique

Améliorations, Optimisations et Résolutions de problèmes:

* Problème d'affichage lors du lancement de la première musique dans Android Auto réglé
* La séléction du mode aléatoire par défaut est représenté par des boutons avec icônes et plus à
  l'aide d'un switch
* Affichage du nom des boutons de la barre de navigation

## 1.0 (Android 5.1.1 Lollipop et ultérieur)

### 1.0.3

Améliorations, Optimisations et Résolutions de problèmes:

* L'album n'est plus affiché dans le playback si la taille de la hauteur de l'écran est trop petite
  en mode paysage
* Résolution du crash lors du clique sur le bouton paramètre lorsque la permission audio n'est pas
  accordée
* La langue par défaut si la langue du système n'est pas prise en charge est maintenant l'anglais et
  plus le français
* Les titres des albums ne dépasseront plus la largeur de l'album
* Corrections de la taille du texte de la section "Musique" dans les vues artiste et genre
* L'ajout d'une musique dans plusieurs playlist est maintenant résolu
* Résolution du problème lors de l'ajout de 2 playlists de même nom avec des majuscules et minuscule
  sur des lettres différentes
* Lors de l'ajout d'une musique à une playlist et qu'aucune playlist n'a été créée, l'application
  lance la création d'une nouvelle playlist

### 1.0.2

Améliorations, Optimisations et Résolutions de problèmes:

* La navigation dans les paramètres fonctionne de manière uniforme pour tous les menus.
* Amélioration du design

### 1.0.1

Améliorations, Optimisations et Résolutions de problèmes:

* Les icones dans la vue de la musique en cours de lecture n'est plus totalement blanc ni totalement
  noir lorsque désactivé.
* Harmonisation des icones dans les paramètre en les rendant rond.
* Ajout d'une marge horizontale dans la vue du paramètre Android Auto
* Problème résolu pour le téléchargement d'une mise à jour stable

### 1.0.0

Satunes est maintenant stable!

Améliorations, Optimisations et Résolutions de problèmes:

* Le problème lié à la navigation dans les genres ou albums avec un nom contenant le caractère '/'
  peut maintenant fonctionner normalement
* Le design a été amélioré:
  * Les boutons shuffle et repeat dans la vue playback (uniquement dans l'application sur téléphone)
    sont complètement rond
  * La prise en charge des tailles d'écran à été améliorée
* Ajout d'un menu dans les paramètres expliquant comment activer Android Auto

### 1.0.0-preview-2

Nouvelles Fonctionnalités:

* Possibilité de changer le nom d'une playlist

Améliorations, Optimisations et Résolutions de problèmes:

* Les problèmes lié au playback dans Android Auto sont réglés:
  * Pas d'affichage au lancement
  * Barre de progression pendant le mode répéter un seul morceau qui va au delà de la limite de
    temps
* Ajout d'un texte expliquant pourquoi l'exportation et exportation des playlists est en bêta
* Tentative de correction du crash lors de l'ouverture de l'app après un long moment après une pause

### 1.0.0-preview-1

Cette version est prévue d'être déployée comme version stable si aucun bug n'est détecté pendant
cette phase.

Nouvelles Fonctionnalités:

* Ajout d'un bouton shuffle dans toutes les vues Android Auto
* Suppression de l'onglet "Musics" car trop d'éléments dans Android Auto.
* Nouveaux paramètres:
  * Choix du mode répétition
  * Choix du mode shuffle
  * Possibilité d'empêcher les autres application de mettre le playback en pause/play

Améliorations, Optimisations et Résolutions de problèmes:

* Les accents sont correctement triés
* La barre de progression ne bug plus lors de l'activation/désactivation du mode shuffle
* La lecture en arrière plan fonctionne correctement en fonction du paramètre choisi
* Les mises à jour prendront en compte les alpha, beta et preview 1,2,3, etc. par exemple "
  1.0.0-preview-1"
* Le multilangage est maintenant prit en charge dans Android Auto
* Le bouton shuffle dans Android Auto a un icone
* Modification du lien pour github
* Amélioration du design et prise en charge de différentes taille d'écran.

## 0.10 (Android 5.1.1 Lollipop et ultérieur)

### 0.10.1-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Les morceaux sont triés par ordre alphabétique dans les dossiers, cela évite le mauvais ordre de
  lecture.
* Optimization des performances et de la batterie lors de la lecture. Le rafraichissement de la
  barre de progression se fait uniquement lorsque la vue concernée est ouverte.

### 0.10.0-beta

L'application est maintenant disponible pour les appareils Android Lollipop (5.1.1) et plus récents.
Environ 99.2% d'entre vous peuvent l'installer 😜.

Nouvelles Fonctionnalités:

* Il est possible de changer la vitesse de rafraichissement de la barre de progression
* Il est possible d'exporter et d'importer les playlists, au format json (des améliorations future
  seront mises au point.)
* Il est possible d'exclure les sonneries

Améliorations, Optimisations et Résolutions de problèmes:

* La façon dont les permissions sont gérée aa été amélioré et l'application ne plante plus lors du
  premier démarrage
* Le design a été amélioré:
  * Afin d'éviter les blocage dans les petits écran, toutes les éléments sont scrollable en attente
    de trouver une solution plus adéquate
  * L'image de l'album est affichée à l'écran dans la page de l'album
  * Les différentes vues sont dans leur entierté déroulable
  * La vue des genres affiches, en plus des musiques, les albums comme dans la page d'un artiste
  * Le nom de l'artiste est affiché dans la vue de l'album et permet une redirection vers celui-ci
  * Les zones de textes ont été harmonisées et les long textes seront coupés si nécéssaire
* Quelques optimisations légères ont été apportées

## 0.9 (Android 9 Pie et ultérieur)

### 0.9.2-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Amélioration du tri
* Les albums contiennent plus qu'une musique
* Prise en compte du cas où aucune donnée n'a été chargée.

### 0.9.1-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Changement du nom de domaine vers "io.github.antoinepirlot.satunes".
* Le design a été retouché pour apporter des pochette d'album par défaut partout dans l'application.
* Les pochettes d'albums s'affichent dans Android Auto.
* Les migration de la base de données pourront être faites.
* Réglage de quelques problème lors de l'initialisation de Android Auto dans certains cas.
* La recomposition de l'écran lors de l'ajout de données comme des playlist est maintenant
  opérationnel.

### 0.9.0-beta

MP3 Player porte enfin son propre nom "Satunes". Cette version devient compatible Android 9 Pie et plus récent.

Nouvelles Fonctionnalités:

* Un click sur la notification ouvre l'application là où l'utilisateur l'a laissé.
* Compatibilité avec Android 9 Pie et plus récent
* Possibilité d'ajouter plusieurs musiques dans une playlist
* Possibilité de lancer les modes aléatoire et répéter un morceau dans Android Auto

Améliorations, Optimisations et Résolutions de problèmes:

* Le design a été amélioré.
* Les paramètres ont été déplacés dans des sous sections pour une meilleure séparations.
* Les mises à jours pourront se faire peu importe le nom de l'application
* Les toasts ne seront plus affichés 2 fois lors de la mise à jour.
* L'image des albums s'affiche dans les listes des albums et musiques afin de rendre les listes plus sympatique.
* Optimisation de l'utilisation de la batterie
* Le lancement d'une musique depuis les playlists est maintenant réglé.
* La librairie qui permet la navigation a été mise à jour et apporte de nouvelles animations.

## 0.8 (Android 11 Red Velvet Cake et ultérieur)

### 0.8.3-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Tous les albums qui ont le même noms seront affiché à l'écran.
* Les artistes, albums et genres sans noms auront un nom associé.

### 0.8.2-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Les albums contiennent toutes leurs musiques. Un album est considéré comme identique si son titre
  et son artist sont les mêmes.
* Maintenant il est possible de télécharger et d'installer la mise à jour depuis l'application.

### 0.8.1-beta

Améliorations, Optimisations et Résolutions de problèmes:

* Lorsque l'utilisateur a ajouté des musique aux playlists mais qu'il décide de les supprimer de
  l'appareil. L'application supprime les musiques manquante des playlists. Cela empêche
  l'application de crasher à chaque lancement.
* Le système de mise à jour à été repensé. Maintenant, l'utilisateur doit cliquer sur un bouton dans
  les paramètres. Ce n'est plus automatique afin d'éviter des consomations de données inutiles.
* La vérification des mises à jour a été corrigée et prend maintenant en compte 4 types de mises à
  jour:
  * Alpha: Il s'agit des versions les plus expérimentales et les plus buguées. Les fonctionnalités
    ne sont pas marquée dans le marbre et peuvent disparaître avec une mise à jour ultérieure.
  * Beta: Il s'agit des versions de tests permettant de régler les problèmes des nouvelles
    fonctionnalités. Celle-ci seront ajouté définitivement à l'application (sauf cas de force
    majeur).
  * Preview: Il s'agit d'une version candidate qui, si tout ce passe bien, sera déployée en version
    stable.
  * Stable: Il s'agit d'une version prête à l'emploi. C'est la version recomandée pour une
    utilisation normale. Elle peut cependant encore présenter quelques bugs mais ceux-ci se feront
    rare et n'empêcheront pas le bon fonctionnement des fonctionalités.
* L'ajout de musiques à plusieurs playlists est en principe réglé.
* Les crash survenant lors d'une recomposition de l'UI suite à des changements comme "Rotation de
  l'écran" ou encore "Activation du mode sombre" ne feront plus crasher l'application.
* La navigation a été corigée, maintenant l'utilisateur peut revenir à d'autre écrans principaux
  depuis les paramètre suite à une recompositions de l'écran comme indiqué au point précédent.
* Lors du lancement de l'application, elle ne devrait plus crasher suite à des problèmes liés à
  l'initialisation du playback.
* La structure des données a été révisée afin de régler certains problèmes comme l'ouverture d'un
  album qui n'était pas le bon ou qui faisait crasher l'application lorsque 2 albums ont le même
  nom.
* Optimisation du chargement des données ainsi que du changement du mode aléatoire (grâce aux
  coroutines de Kotlin)
* La barre de progression a été réduite sur les côtés pour éviter un conflit avec la navigation par
  geste.
* Le playback dans Android Auto a été réglé. Cela se traduira par une possible augmentation de
  l'utilisation de la batterie mais ceci n'a pas encore pu être vérifié.

### 0.8.0-beta

Nouvelles Fonctionnalités:

* Il est possible de vérifier si une mise à jour est disponible via les paramètres.
* Ajout de nouvelles sections dans la page paramètres.
* Ajout de titre indiquant où l'utilisateur se trouve.
* Ajout d'une liste horizontale affichant les albums d'un artiste.
* Possibilité de cliquer sur l'album (dans la page d'un artiste ou depuis la musique en cours de lecture) pour afficher la page de l'album.

Améliorations, Optimisations et Résolutions de problèmes:

* Le paramètre qui permet de masquer l'onglet fichier est réparé, le paramètre est enregistré correctement.
* L'affichage de la page paramètre à été repensé et amélioré.

## 0.7 (Android 11 Red Velvet Cake et ultérieur)

### 0.7.3-beta

Améliorations, Optimisation et Résolution de problèmes:

* Le chargement des données a été optimisé et est beaucoup plus rapide.
* Tous les textes affichés dans Android Auto sont maintenant déchappés pour voir les bons
  caractères.

### 0.7.2-beta

Améliorations, Optimisation et Résolution de problèmes:

* La navigation entre les pages est plus intuitive
* Correctif apporté au playback
* Les caractères spéciaux sont échappés, cela règles des problèmes d'ouverture de dossier/media avec
  un nom contenant un caractère spécial (par exemple: Hip-Hop/Rap)

### 0.7.1-beta

Maintenant, l'application possède son icône 🤗 (notez que je ne suis pas un artiste 🤭).

Résolution des problèmes:

* Le bouton pour créer une playlist est maintenant situé en bas à droite de l'écran.
* Le playback a été remanié et les changements de pistes ne devraient faire de problème avec
  l'affichage de la musique en cours de lecture.
* Un cercle de chargement apparaît au lancement lorsqu'il y a beaucoup de musiques à charger afin d'
  éviter un lancement plus lent de l'application.
* La musique ne se met plus en pause automatiquement dans Android Auto lors d'un changement de
  musique depuis la liste de lecture.

### 0.7.0-beta

Fonctionnalités:

* Ajout des playlists

## 0.6 (Android 11 Red Velvet Cake et ultérieur)

### 0.6.0-beta

Cette version rend l'application compatible avec Android 11 Red Velvet Cake et ultérieur.

## 0.5 (Android 14 Upside Down Cake et ultérieur)

### 0.5.1-beta

Résolutions de problèmes:

* Optimisation de Android Auto
* La lecture de la musique est correctement synchronisée.

### 0.5.0-beta

Fonctionnalités:

* Support pour Android Auto
  * Pour activer Android Auto clique [ici](README.md) pour savoir comment faire.

Résolutions de problèmes:

* Optimisation
* Les infrmations de la musique sont affichée dans la notification.

## 0.4 (Android 14 Upside Down Cake et ultérieur)

### 0.4.2-beta

Résolutions de problèmes:

* La musique est mise en pause lorsqu'une autre application mets de l'audio.

### 0.4.1-beta

Résolutions de problèmes:

* Musiques, Albums, etc. sont triées par rapport à leurs noms.

### 0.4.0-beta

Fonctionnalités:

* Affichage de la lite des genres et possiblité de lire la musique depuis ceux-ci.
* Paramètres
    * Authorise l'utilisateur à choisir d'arrêter la lecture si l'application est fermée depuis le multi-âche.
    * Authorise l'utilisateur à choisir d'arrêter la lecture si le casque/écouteurs sont déconnectés.

Résolutions de problèmes:

* La musique provenant d'un stockage externe peut être lue.
* Affiche les différents stockage ("Cet appareil", "Carte SD", etc.)

## 0.3 (Android 14 Upside Down Cake et ultérieur)

### 0.3.2-beta

Résolutions de problèmes:

* Désactive la mise en aléatoire depuis l'extérieur de l'application.

### 0.3.1-beta

Résolutions de problèmes:

* Le chargement des données sur les appareils Galaxy ne font plus crasher l'application si aucune image d'album n'est trouvée.
* La déconnexion des écouteurs/casques met la lecture en pause.
* Le bouton play/pause est correctement synchronisé avec l'état de la musique.

### 0.3.0-beta

De nouvelles fonctionnalités sont arrivées:

* Affichage des albums et la possibilité de lire la musique depuis ceux-ci.
* Page des paramètres
    * Possiblité de modifier la bar en bas de l'écran en enlevant des catégories.

## 0.2 (Android 14 Upside Down Cake et ultérieur)

### 0.2.1-beta

Dans cette version, le problème suivant à été resolu:

* Lors du chargement des images d'albums, l'application ne crash plus.

### 0.2.0-beta

Dans cette version, l'image de l'album est montré dans la vue lecture en cours et la bar de progression à été déplacée en bas de l'écran.

## 0.1 (Android 14 Upside Down Cake et ultérieur)

### 0.1.0-beta

Cette version est la toute première version de l'application (en bêta).
Cela inclus les fonctionnalités de base d'un lecteur MP3.
