# TODO
- Faire les resolve
- Faire les collect
- Faire les checkType

- Traiter les TODOs en commentaire

/!\ Il se peut que des getType que l'on a fait au debut ne soit pas assez complet.
En effet, certaines erreurs de types incompatibles ont peut être été oubliées

/!\ Pourquoi quand on affecte une variable pas encore déclarée bah ça plante pas
genre ça : int b = c; int c = 3;

/!\ Dans la functiondeclaration, quand on checktype comment qu'on fait pour avoir les instructions return

/!\ Utilité de addPrefix ??

/!\ La différence entre Repetition et Iteration ???

/!\ Poser la question de getCode() pour Printer et Return

/!\ Revoir le getCode pour First et Second (notamment LOAD de la variable et POP)

/!\ Pourquoi pas de append du left dans le getCode de BinaryExpression ?

## Génération de code
### getCode

### autre
- [x] Block

### expression
- [x] accessible/AddressAccess.java
- [ ] accessible/ArrayAccess.java
- [x] accessible/ConstantAccess.java
- [x] accessible/FieldAccess.java
- [x] accessible/IdentifierAccess.java
- [x] accessible/ParameterAccess.java
- [x] accessible/PointerAccess.java
- [x] accessible/VariableAccess.java
  
- [x] allocation/ArrayAllocation.java
- [x] allocation/PointerAllocation.java

- [x] ConditionalExpression.java
- [x] Couple.java
- [x] First.java
- [x] FunctionCall.java
- [x] Second.java
- [x] Sequence.java


### instructions
- [x] Assignment
- [x] Conditional
- [x] Iteration
- [ ] Printer
- [x] Repetition
- [ ] Return

- [ ] declaration/ConstantDeclaration   /!\ À VOIR
- [ ] declaration/FunctionDeclaration
- [x] declaration/VariableDeclaration

### allocateMemory

### autre

- [x] Block

### instructions

- [x] Assignment
- [x] Conditional
- [x] Iteration
- [x] Printer
- [x] Repetition
- [x] Return

- [x] declaration/ConstantDeclaration   /!\ À VOIR
- [ ] declaration/FunctionDeclaration
- [x] declaration/VariableDeclaration

## Expressions à faire

### resolve

- [x] Second.java
- [x] FunctionCall.java
- [x] AbstractPointer.java
- [x] AbstractField.java
- [x] allocation/ArrayAllocation.java
- [x] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java
- [x] AbstractArray.java
- [ ] AbstractConversion.java

### collect
- [x] Second.java 
- [x] FunctionCall.java
- [x] ConditionalExpression
- [x] AbstractPointer.java
- [x] AbstractField.java
- [x] allocation/ArrayAllocation.java
- [x] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java
- [x] AbstractArray.java
- [ ] AbstractConversion.java

### getType
- [x] First.java
- [x] Second.java
- [x] FunctionCall.java
- [x] ConditionalExpression.java
- [x] AbstractPointer.java
- [x] AbstractField.java
- [x] allocation/ArrayAllocation.java
- [x] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java
- [x] AbstractArray.java
- [ ] AbstractConversion.java
- [x] assignable/VariableAssignment.java

## Instructions à faire


### resolve
- [x] Assignment -> pourquoi Logger.error ?
- [x] Printer
- [x] Repetition
- [x] Return
- [ ] declaration/FunctionDeclaration -> a reflechir
- [x] declaration/TypeDeclaration

### collect
- [x] Assignment
- [x] Printer
- [x] Repetition
- [x] Return
- [x] declaration/TypeDeclaration

- [x] declaration/FunctionDeclaration

### getType
- [x] Assignment -> pas sûr pour ErrorType

### checkType
- [x] Assignment
- [x] Conditional
- [x] Iteration
- [x] Printer
- [x] Repetition
- [x] Return

- [x] declaration/ConstantDeclaration
- [ ] declaration/FunctionDeclaration
- [x] declaration/VariableDeclaration
