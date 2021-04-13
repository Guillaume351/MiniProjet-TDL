# TODO


/!\ Il se peut que des getType que l'on a fait au debut ne soit pas assez complet.
En effet, certaines erreurs de types incompatibles ont peut être été oubliées

/!\ Pourquoi quand on affecte une variable pas encore déclarée bah ça plante pas
genre ça : int b = c; int c = 3;

/!\ La différence entre Repetition et Iteration ???




## Type à faire

- [ ] EnumerationType


## Ce dont on va parler à la présentation

- Présenter la manière dont on a géré le lien entre FunctionDeclaration et Return pour le checkType() + ajout du $parameterslength$ pour le getCode() du Return

- Pour le FunctionCall : pourquoi le CALL SB fonctionne dans un appel récursif ?
  
- Parler du TODO qu'il nous reste, cad la longueur d'un PointerType

- Présenter nos différents exemples pour les différentes fonctionnalités :
    - la fonction PGCD          --> Corentin   (parler du fait qu'on a ajouté un JUMP pour sauter les déclarations de méthodes pour que TAM fonctionne)
    - la fonction factorielle   --> Guillaumus (Cf probleme de registre ?)
    - les Segments et Points    --> Guillaumus (Mécanisme de remontée des fields)
    - les pointeurs et tableaux --> Corentin   (parler des fichiers PointerAccess et PointerAssignment) + dire que c'est idem pour les tableaux
    
    --> Il faut qu'on précise qu'on a rajouté un getDeclaration dans IdentifierAccess pour obtenir les register + offset des déclarations dans FieldAccess, PointerAccess,...
  
- Parler du fait que le LOADA LOADI nous a un peu poser problème ; on ne savait où placer le LOADI  --> dans Assignment ? puis finalement dans VariableAccess mais cela a posé le
  problème que dans les autres Access par exemple PointerAccess, on ne pouvait faire this.pointer.getCode()
  
- Parler des Enumerations ; idées d'implémentation/montrer l'implémentation si on l'a déjà fait

