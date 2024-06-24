# CarbonDF
### A tool for writing DiamondFire code using a custom language

## Why?
DiamondFire code is annoying to write. Checking parameters and variables is slow, and you have to move around constantly.
CDF lets you write actual code and have it transpile back to DiamondFire code.

In addition, CDF adds extra features, such as strong-typing and returning values.

## How?
Parsing is taken care of by ANTLR (see [src/main/parser_grammar](./src/main/resources/parser_grammar)).
Since DF templates are basically GZipped JSONs, the idea is to parse the language, transpile it into a JSON format, and export it as a template.
In the future I may add the ability to import files, a more concise syntax, some syntax sugar, and maybe a way to semi-emulate a file and add some more checks that DF does not include.

### Syntax examples:
```CarbonDF
fun multiply(param, other_param: num) -> (result: num){
    local temp = mult(param, other_param);
    return(temp);
}
```

```CarbonDF
hidden proc lightningLoop() {
    line launchPower = 20;
    repeat (forever()) {
        
        line playerPos: loc = default.location;
        GameAction::summonLightning(playerPos);
        GameAction::createExplosion(playerPos);
        PlayerAction::default.launchUp(launchPower);
        sendMessage('<#FFFFFF>Thou hast been <#FFFF00>smitten<#FFFFFF>!');
        wait(60);
    }
}
```

