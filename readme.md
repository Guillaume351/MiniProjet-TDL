# TODO
- Faire les resolve
- Faire les collect
- Faire les checkType

- Traiter les TODOs en commentaire

/!\ Il se peut que des getType que l'on a fait au debut ne soit pas assez complet.
En effet, certaines erreurs de types incompatibles ont peut être été oubliées

/!\ allocateMemory

## Génération de code
### getCode

### autre
- [ ] Block

### expression
- [ ] accessible/AddressAccess.java
- [ ] accessible/ArrayAccess.java
- [ ] accessible/ConstantAccess.java
- [ ] accessible/FieldAccess.java
- [ ] accessible/IdentifierAccess.java
- [ ] accessible/ParameterAccess.java
- [ ] accessible/PointerAccess.java
- [ ] accessible/VariableAccess.java
  
- [ ] allocation/ArrayAllocation.java
- [ ] allocation/PointerAllocation.java

- [ ] ConditionalExpression.java
- [ ] Couple.java
- [ ] First.java
- [ ] FunctionCall.java
- [ ] Second.java
- [ ] Sequence.java


### instructions
- [ ] Assignment
- [ ] Conditional
- [ ] Iteration
- [ ] Printer
- [ ] Repetition
- [ ] Return

- [ ] declaration/ConstantDeclaration
- [ ] declaration/FunctionDeclaration
- [ ] declaration/VariableDeclaration



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
- [ ] AbstractConversion

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

- [ ] declaration/FunctionDeclaration

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


# Questions
- Dans FunctionCall.java, est-ce qu'il faut créer une nouvelle symbol table ?
- Si oui, est-ce qu'on doit cloner la symbol table globale ?
- Pour les expressions, est-ce qu'il faut checktype dans le getType ? (cf la ConditionalExpression)