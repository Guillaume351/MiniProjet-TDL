# TODO
- Faire les resolve
- Faire les collect
- Faire les checkType



## Expressions à faire
### resolve
- [x] Second.java
- [x] FunctionCall.java
- [ ] AbstractPointer.java
- [ ] AbstractField.java
- [ ] allocation/ArrayAllocation.java
- [ ] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java

### collect
- [x] Second.java 
- [x] FunctionCall.java
- [ ] ConditionalExpression
- [ ] AbstractPointer.java
- [ ] AbstractField.java
- [ ] allocation/ArrayAllocation.java
- [ ] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java

### getType
- [x] First.java
- [x] Second.java
- [x] FunctionCall.java
- [ ] ConditionalExpression.java
- [ ] AbstractPointer.java
- [ ] AbstractField.java
- [ ] allocation/ArrayAllocation.java
- [ ] allocation/PointerAllocation.java
- [x] accessible/AddressAccess.java

## Instructions à faire
### resolve
- [x] Assignment -> pourquoi Logger.error ?
- [x] Printer
- [x] Repetition
- [x] Return
- [ ] declaration/FunctionDeclaration -> a reflechir
- [x] declaration/TypeDeclaration

### collect
- [ ] Assignment
- [x] Printer
- [x] Repetition
- [x] Return
- [ ] declaration/TypeDeclaration

- [ ] declaration/FunctionDeclaration

### getType
- [x] Assignment -> pas sûr pour ErrorType

### checkType
- [x] Assignment
- [x] Conditional -> ajouter BooleanType ?
- [x] Iteration -> ajouter BooleanType ?
- [x] Printer
- [x] Repetition -> ajouter BooleanType ?
- [x] Return

- [x] declaration/ConstantDeclaration
- [ ] declaration/FunctionDeclaration
- [x] declaration/VariableDeclaration


# Questions
- Dans FunctionCall.java, est-ce qu'il faut créer une nouvelle symbol table ?
- Si oui, est-ce qu'on doit cloner la symbol table globale ?