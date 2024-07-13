package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.parser.CarbonDFParser;

public class Modifiers {
    boolean extern;
    boolean hidden;
    boolean lsCancel;

    public Modifiers() {
        this.extern = false;
        this.hidden = false;
        this.lsCancel = false;
    }


    public static Modifiers generateModifiers(CarbonDFParser.StartdefContext ctx) {
        if (ctx.modifiers() == null || ctx.modifiers().isEmpty()) return new Modifiers();
        Modifiers resMods = new Modifiers();
        for (CarbonDFParser.ModifiersContext modCtx : ctx.modifiers()) {
            if (modCtx.vis_modifier() != null)
                resMods.setHidden(modCtx.vis_modifier().MOD_INVISIBLE() != null);
            else if (modCtx.extern_modifier() != null) resMods.setExtern(true);
            else if (modCtx.ls_cancel_modifier() != null) resMods.setLsCancel(true);
        }
        return resMods;
    }

    public boolean isExtern() {
        return extern;
    }

    public Modifiers setExtern(boolean extern) {
        this.extern = extern;
        return this;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isVisible() {
        return !hidden;
    }

    public Modifiers setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public boolean isLsCancel() {
        return lsCancel;
    }

    public Modifiers setLsCancel(boolean lscancel) {
        this.lsCancel = lscancel;
        return this;
    }
}
