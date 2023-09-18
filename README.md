# CarbonDF
### A tool for writing DiamondFire code using a custom language

## Why?
DiamondFire code is annoying to write. Checking parameters and variables is slow, and you have to move around constantly.
CDF lets you write actual code (with syntax somewhere between Python and Kotlin) and have it compile back to DiamondFire code.

## How?
Parsing is taken care of by ANTLR (see src/main/parser_grammar).
Since DF templates are basically GZipped JSONs, the idea is to parse the language, compile it into a JSON file, and export it as a template.
In the future I may add the ability to import files, and maybe a way to somewhat run a file or test it

