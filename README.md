# CarbonDF
### A tool for writing DiamondFire code using a custom language

> [!WARNING]
> This repository is now **DEPRECATED!** CarbonDF is now being remade on https://github.com/GaMeNu/CarbonRebuilt

## Why?
DiamondFire code is annoying to write. Checking parameters and variables is slow, and you have to move around constantly.
In addition, DiamondFire has lacks some features some people may want to use, such as strong types, and returning values.

## What?
CarbonDF is a tool that allows you to write and code in a text-based language, and then transpiles your code into DiamondFire templates.
CarbonDF uses an easy-to-read syntax inspired by Java, Kotlin and Python.
In addition, CarbonDF brings its own features to the table, such as type checking, function returns, and nested list/dict creation.


## How?
Parsing is taken care of by ANTLR (see [src/main/parser_grammar](./src/main/resources/parser_grammar)).
Since DF templates are basically GZipped JSONs, the idea is to parse the language, recreate it in an intermediary format (Java), and then transpile it into a JSON format, and export it as a template.
In the future I may add the ability to import files, a more concise syntax, some syntax sugar, and maybe a way to semi-emulate a file and add some more checks that DF does not include.

### Syntax examples:
```CarbonDF
fun multiply(param, other_param: num) -> (result: num){
    local temp = mult(param, other_param);
    return(temp);
}
```

```CarbonDF
// Set process visibility to hidden
hidden proc lightningLoop() {

    // Declare new weakly typed variable with the value of 20 (num)
    line launchPower = 20;
    
    repeat forever() {
        
        // New strongly typed variable (loc) with the value of the default player's location
        line playerPos: loc = default.location;
        
        // Summon a lightning at their location, create an exploasion, and launch them up in the air.
        GameAction::summonLightning(playerPos);
        GameAction::createExplosion(playerPos);
        PlayerAction::default.launchUp(launchPower);
        
        // Send a centered message
        default.sendMessage('<#FFFFFF>Thou hast been <#FFFF00>smitten<#FFFFFF>!'){"AlignmentMode": "Centered"};
        
        // Wait 60 ticks
        wait(60);
    }
}
```

