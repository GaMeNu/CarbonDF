# CarbonDF
### A tool for writing DiamondFire code using a custom language

## Why?
DiamondFire code is annoying to write. Checking parameters and variables is slow, and you have to move around constantly.
CDF lets you write actual code (with syntax similar to Java) and have it compile back to DiamondFire code.

## How?
Parsing is taken care of by ANTLR (see src/main/parser_grammar).
Since DF templates are basically GZipped JSONs, the idea is to parse the language, compile it into a JSON file, and export it as a template.
In the future I may add the ability to import files, a more concise syntax, and maybe a way to somewhat run a file or test it

### Syntax examples:
```CarbonDF
fun multiply(param, other_param: num){
    temp[l] = param * other_param;
}
```

```CarbonDF
hidden proc lightningLoop() {
    launchPower = 20;
    repeat forever() {
        
        playerPos[li] = default.location;
        game.summonLightning(playerPos);
        game.createExplosion(playerPos);
        default.launchUp(launchPower);
        default.sendMessage(st"<#FFFFFF>Thou hast been <#FFFF00>smitten<#FFFFFF>!");
        wait(60);
    }
}
```

