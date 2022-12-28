# MyLang #

## Grammar Definition ##

```
var is a Variable
num is a Number
type ::= `int` | `bool`
vardec ::= `(` `vardec` type var expression `)`
expression ::= num | `true` | `false` | var |
               `(` op expression expression `)`
loop ::= `(` `while` expression statement `)`
assign ::= `(` `=` var expression `)`
statement ::= vardec | loop | assign
op ::= `+` | `-` | `&&` | `||` | `<`
program ::= statement*
```

Object language (our language): MyLang
Metalanguage (what we are writing the compiler in): Java
Target language (what we are compiling to): JavaScript

## Tokens ##

Possible Tokens:

- IdentifierToken(String)
- NumberToken(int)
- IntToken: 0
- BoolToken: 1
- LeftParenToken: 2
- VardecToken: 3
- RightParenToken: 4
- TrueToken: 5
- FalseToken: 6
- WhileToken: 7
- SingleEqualsToken: 8
- PlusToken: 9
- MinusToken: 10
- LogicalAndToken: 11
- LogicalOrToken: 12
- LessThanToken: 13

## AST Definition ##

interface Type
  - class IntType: 0
  - class BoolType: 1

interface Stmt
  - class VardecStmt
  - class WhileStmt
  - class AssignStmt

interface Exp
  - class NumberLiteralExp
  - class BooleanLiteralExp
  - class VariableExp
  - class BinaryOperatorExp

interface Op
  - class PlusOp: 2
  - class MinusOp: 3
  - class LogicalAndOp: 4
  - class LogicalOrOp: 5
  - class LessThanOp: 6

class Program
