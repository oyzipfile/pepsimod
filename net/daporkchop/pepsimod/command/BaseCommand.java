// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command;

import net.daporkchop.pepsimod.command.api.Command;

public class BaseCommand extends Command
{
    public BaseCommand() {
        super("delet_this");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".delet_this";
    }
}
