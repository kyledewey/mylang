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
