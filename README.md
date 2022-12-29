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
print ::= `(` `print` expression `)`
progn ::= `(` `progn` statement* `)`
statement ::= vardec | loop | assign | print | progn
op ::= `+` | `-` | `&&` | `||` | `<`
program ::= statement
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
- PrintToken: 14
- PrognToken: 15

## AST Definition ##

interface Type
  - class IntType: 0
  - class BoolType: 1

interface Stmt
  - class VardecStmt
  - class WhileStmt
  - class AssignStmt
  - class PrintStmt
  - class PrognStmt

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

## Types ##

- Vardec puts a variable in scope with a type
  - Need to remember the variable and the type
  - Need to ensure the expression is of the type
- num should be an int
- true and false should be bools
- var is whatever the type of the variable is
- while's expression is a boolean
- assign:
  - var should be in scope
  - var's type should match expression's type
- exp1 + exp2 => int, exp1: int, exp2: int
- exp1 - exp2 => int, exp1: int, exp2: int
- exp1 && exp2 => bool, exp1: bool, exp2: bool
- exp1 || exp2 => bool, exp1: bool, exp2: bool
- exp1 < exp => bool, exp1: int, exp2: int
- program: all statements are well-typed

## Running the Code ##

```console
mvn exec:java -Dexec.mainClass="mylang.MyLang" -Dexec.args="input_program.mylang output_program.js"
```
