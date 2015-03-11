PRIDE est une application RESTfull pour proposer des idées de projets

Repository (ou repo) : c'est le Github
Quand eclipse demande de s'identifier (quand vous faites un commit ou quoi que ce soit d'autre, voir plus bas) dans Usernam il faut mettre le pseudo de Github et dans mot de passe... le mot de passe de Github.


Il faut utiliser Eclipse Luna (parce que j'ai fait les tests pour Github sur Luna)
Dans eclipse il faut installer des plugins. Pour ça, il faut aller dans "Help" -> Eclipse Marketplace"
Dans la petite bar de recherche en haut à gauche, il faut chercher le plugin suivant : 

  - Subclipse 1.10.9 (il suffit de tapper "Subclipse", pour synchroniser avec Github)

Une fois que vous avez installé tous ça (si c'est pas déjà fait, il faut redémarrer Eclipse), on va récupèrer le projet qui est ici même sur Github. Dans Eclipse, il faut aller dans "Window" -> "Open Perspective" dans la fenêtre de dialogue, il faut sélectionner "SVN Repository Exploring". Normalement, il ouvre la perspective de SVN, et à gauche (là où il y a les projets d'habitudes) vous êtes sur un onglet "SVN Repositories". Il faut faire clique droit dans le vide dans l'onglet, "New" -> "Repository location". Une fenêtre s'ouvre, dans "Url", il faut mettre l'url du repo Github, qui est : "https://github.com/Mougoule/pride.git", on clique sur "Finish". Dans l'onglet "SVN Repositories", il y a un repo qui apparaît (icône comme pour les BDD et il s'appelle "https://github.com/Mougoule/pride.git"). On clique sur la petite flêche pour ouvrir l'arboressence, ensuite de même sur le dossier "trunk" (c'est pas moi qui est voulu faire une référence à DBZ, ça s'appelle comme ça dans SVN... Peut-être que eux aussi étaient fan de DBZ quand ils étaient des pitits n'enfants...) et là, LA, j'ai bien dit LAAAAA motherfucker!! Qu'est ce qu'on voit ? On voit, "pride" DAT MOTHAFUCKA YA NEGEUZZZZZZZ!!!! Clique droit dessus, "Checkout", on clique sur "Finish" direct (parce qu'on est des Thug). Une fois que c'est fait, il faut attendre un peu le temps qu'Eclipse le convertisse en projet Maven, pour savoir si c'est bon, il y a un "M" bleu qui appraît sur l'icône du projet (pour qu'est ce que c'est Maven, voir paragraphe suivant entre **. S'il le fait pas tout seul (genre au bout de 10 minutes) faut essayer en faisant clique droit -> "Configure" -> "Convert to Maven project". Si ça ça marche pas, vous êtes viré et je récupèrer les royalities du projet... Et ouais ma gueule.
Quand c'est fait, dans le projet il y a un fichier "pom.xml" qui est à la source, il faut faire clique droit -> "Run as" -> "Maven install". Il se passe des trucs dans la console. On croise les droit pour un "SUCCESS".

*
Maven c'est pour gérer les "dependencies", c-à-d les différents frameworks que l'on va utiliser (genre Hibernate, CXF) ou bien des bibliothèques utilent (genre celles d'apache qui permettent de parser des inputstream en fichier et inversement sur facilement)
*

A ce stade, on a récupéré le projet tel qu'il est ici sur Github, c'est déjà pas mal maintenant on peut taffer dessus. Quand vous avez bien taffer dessus (un petit carré noir avec une étoile blanche apparaît, c'est que vous avez modifié un fichier) et que c'est bon vous voulez "commiter" votre taf (le synchro avec Github quoi), clique droit sur le projet -> "Team" -> "Synchronize with Repository" (Eclipse dit qu'il faut ouvrir une perspective spéciale, on clique sur "ok"). Comme précédemment, y a un onglet qui s'est ouvert à gauche à la place des projets. On voit le projet que l'on veut synchroniser. 
La flêche bleue qui va vers la gauche signifie qu'il y a des changements qui proviennent du repo.
La flêche grise qui va vers la droite signifie qu'il y a des changements sur notre version qui ne sont pas synchronisée avec le repo.
(Quand il y a un plus dans la flêche, c'est qu'il s'agit d'un nouveau fichier, quand il y a un moins c'est que le fichier a été supprimé)
Quand y a les deux flêches en même temps, c'est que les deux cas sont présents en même temps (izi, appelez moi Captain Obvious!)
Quand il y a une double flêche rouge, c'est qu'il y a des conflits. Et ça c'est déliquat car on peut faire disparaître le taf des autres. On peut double cliquer sur un fichier (genre un classe) et ça ouvre à droite une fenêtre de comparaison entre notre fichier et celui sur le repo pour voir les différences ("Remote File" : c'est le fichier sur Github, "Local File" c'est le notre en local). Il faut intégrer en priorité les changements qui viennent de Github avant d'envoyer nos propres modifs. Sur l'écran de comparaison, il encadre en rouge les conflits, en bleu les mises à jour qui viennent de Github et en gris les votre. Dès fois il est paumé il raconte de la merde. 
Quand les fichiers ont uniquement une flêche bleue, on les sélectionne, on fait clique droit -> "Update".
Quand les fichiers ont uniquement une flêche grise, on les sélectionne, on fait clique droit -> "Commit".
Quand les fichiers ont un double flêche rouge, on a deux possibilités :
  - soit on s'enfiche de notre fichier local (genre notre modif c'est juste un retour à la ligne" et du coup on fait clique droit -> "Override and Update" et on perd notre fichier local.
  - soit la flêche rouge reste alors qu'il n'y a pas de conflits (en général elle reste même une fois que vous avez récupéré les modifs de Github) et la il faut faire clique droit -> "Mark as Merged" (en gros on dit que notre version est la plus à jour). La flêche rouge disparait et la grise apprait, on commit.

Depuis l'onglet des projets à gauche :
Si on fait de la merde, on a plusieurs options, notamment : "Compare With" et "Replace With" (clique droit sur un fichier)
On choisit l'une des deux et on clique sur "Local History..." et là on a l'historique des versions qui est enregistré en local.

/!\ /!\ /!\ Très important /!\ /!\ /!\ : sur Github, il faut éditer votre profil et renseigner un nom en plus de votre pseudo. C'est ce nom qui s'affiche à droite des fichiers. Ca permet de savoir qui a fait le dernier commit afin de savoir qui c'est qui a tout pété.

Ne pas commiter les fichiers de confs, du style : 
  - "Pom.xml" (le fichier de Maven). Dans ce fichier, au début il y a des balises <profile>, dedans il y a une balise <logs.folder>, elle indique l'endroit où les logs seront copiés. Il faut créer le dossier au chemin indiqué (vous pouvez changez le chemin mais il faut pas le commiter).
  - "persistence.xml" (c'est pour la BDD)
  - "context.xml" (c'est ce qui fait le lien avec la BDD). Dans ce fichier il y a des modifs à faire, mais elles sont propres à chacun, il faut pas commiter. Les modfis sont :
      -  username="root" password="hardis38", il faut remplacer par le username et le password que vous utilisez pour vous connecter à la base en local sur votre machine. Je parlerai de la base plus tard.
  - "web.xml" (fichier de conf de CXF)
  - "beans.xml", celui-là faut apporter des modifs, mais il faut faire attention de pas tout casser. On ajoute les web services qu'on a créé dedans. Il y en a déjà, c'est des exemples. C'est très simple, quand on créer un service, on rajoute les lignes à la suite de celle déjà existentes (en faisant attention de pas niquer les balises) : 
      <bean
				class="fr.pride.project.services.rs.SecurityRestService">
			</bean>
en remplaçant "SecurityRestService" par le nom que l'on a donné à la classe.
  - Et globalement, tout fichier que vous n'avez pas touché car Maven créer des fichiers, et ceux là c'est propre à chacun, donc globalement vous commitez que les ".java"
