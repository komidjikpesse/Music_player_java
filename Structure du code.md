## Structure du code

Le code est organisé de manière modulaire pour faciliter sa compréhension et sa maintenance. Voici une description détaillée de chaque composant :

### 1. Classe principale `MusicPlayer`

Cette classe contient le point d'entrée du programme et est responsable de la création de l'interface graphique principale. Elle instancie également les boutons de contrôle et associe des écouteurs d'événements à chacun d'eux pour gérer les interactions de l'utilisateur.

### 2. Gestion des événements

Les actions de l'utilisateur sont gérées par des écouteurs d'événements associés aux différents boutons de l'interface graphique. Chaque bouton est associé à une action spécifique, comme la lecture, la pause, le redémarrage, la reprise, le passage à la piste suivante ou le retour à la piste précédente.

### 3. Lecture audio

La lecture des fichiers audio est gérée par la classe `Clip` de la bibliothèque Java Sound. Lorsque l'utilisateur appuie sur le bouton "Play", le fichier audio sélectionné est chargé et lu par la classe `Clip`.

### 4. Enregistrement audio

L'enregistrement audio à partir du microphone est géré par la classe `TargetDataLine` de la bibliothèque Java Sound. Lorsque l'utilisateur appuie sur le bouton "Capture", le programme commence à enregistrer des données audio à partir du microphone et les sauvegarde dans un fichier WAV une fois l'enregistrement terminé.

### 5. Manipulation de fichiers

Les opérations de lecture et d'écriture de fichiers audio sont effectuées à l'aide des classes `File`, `FileReader`, `FileWriter`, `BufferedReader` et `BufferedWriter`. Ces classes permettent de charger des fichiers audio à partir du système de fichiers et de sauvegarder les enregistrements audio dans des fichiers WAV.

### 6. Interface utilisateur

L'interface utilisateur est construite à l'aide de composants Swing tels que des boutons, des menus déroulants et des onglets. Les différents composants sont agencés de manière à fournir une expérience utilisateur intuitive et conviviale.

### 7. Gestion de la playlist

L'application permet à l'utilisateur de créer une playlist de ses morceaux audio préférés. Les fichiers audio à lire sont spécifiés dans un fichier texte, et l'application lit séquentiellement les fichiers de la playlist lorsque l'utilisateur appuie sur les boutons de contrôle.

### 8. Fonctions utilitaires

Des fonctions utilitaires sont fournies pour simplifier certaines tâches récurrentes, telles que la récupération du nombre de lignes dans un fichier texte et la récupération d'une ligne spécifique à partir d'un fichier texte.

### 9. Point d'entrée `main()`

Le point d'entrée du programme se trouve dans la méthode `main()`, où une instance de la classe `MusicPlayer` est créée et affichée à l'écran pour démarrer l'application.

Cette structure modulaire permet une gestion claire et efficace du code, facilitant ainsi sa compréhension, sa maintenance et son évolutivité.
