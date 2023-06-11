Fonctionnalités livrées :
- Lancement d'une partie simple, avec un nombre de manches pouvant être modifié, trois bots qui jouent la partie, et un affichage des scores (relativement simple) à chaque fin de manche.
- Plateau de jeu avec des tuiles ne possédant pour le moment qu'une simple et unique couleur. Les tuiles sont piochées dans des fabriques par les bots, puis placées dans des lignes motifs leur appartenant. Un arbitre s'occupe ensuite de les poser sur le mur.
- Score relativement simple entre les bots, sans gestion des malus.
- Affichage du bot qui a gagné la partie, avec son score final.

Issues faites :
- Ensemble des issues liées à la première itération :
    - Création des bots (par Lucas)
    - Création d'un plateau (par Nicolas)
    - Création des fabriques (par Jonathan)
    - Piocher les tuiles dans les fabriques (par Lucas et Jonathan)
    - Déposer les tuiles sur une ligne motif (par Lucas et Jonathan)
    - Vérifier si une ligne motif est complète (par Jonathan)
    - Déplacer la tuile de la ligne correspondante sur le mur (par Jérémy)
    - Compter les points (par Nicolas)
    - Arrêter le jeu à la fin d'un round (par Nicolas)
    - Affichage des score (par Nicolas)
    - Création du mur (par Jérémy)
    - Création des lignes motifs (par Jonathan)
    - Création de la piste de score (par Jérémy)
    - Lancement d'une partie / d'une manche (par Nicolas)
    - Création d'un arbitre (par Jérémy)

En termes de tests, quasiment la totalité des méthodes (exceptées celles de la classe Main) possèdent des tests unitaires fonctionnant sous JUnit 5. Tous les tests réalisés sont validés.