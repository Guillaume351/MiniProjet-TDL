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
- [x] Printer
- [x] Repetition
- [x] Return
- [ ] declaration/FunctionDeclaration
- [x] declaration/TypeDeclaration

### collect
- [ ] Assignement
- [x] Printer
- [x] Repetition
- [x] Return
- [ ] declaration/TypeDeclaration

- [ ] declaration/FunctionDeclaration

### getType
- [ ] Assignement

### checkType
- [ ] Assignement
- [ ] Conditional
- [ ] Iteration
- [x] Printer
- [ ] Repetition
- [ ] Return

- [x] declaration/ConstantDeclaration
- [ ] declaration/FunctionDeclaration
- [x] declaration/VariableDeclaration


# Questions
- Dans FunctionCall.java, est-ce qu'il faut créer une nouvelle symbol table ?
- Si oui, est-ce qu'on doit cloner la symbol table globale ?