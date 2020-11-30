// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.api;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.daporkchop.pepsimod.util.PepsiConstants;

public abstract class Command extends PepsiConstants
{
    public String name;
    
    public static void clientMessage(final String toSend) {
        Command.mc.player.sendMessage((ITextComponent)new TextComponentString("§0§l[§c§lpepsi§9§lmod§0§l]§r" + toSend));
    }
    
    public Command(final String name) {
        this.name = name;
    }
    
    public abstract void execute(final String p0, final String[] p1);
    
    public abstract String getSuggestion(final String p0, final String[] p1);
    
    public String[] aliases() {
        return new String[] { this.name };
    }
}
