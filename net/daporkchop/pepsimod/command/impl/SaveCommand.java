// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.daporkchop.pepsimod.command.api.Command;

public class SaveCommand extends Command
{
    public SaveCommand() {
        super("save");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        SaveCommand.pepsimod.saveConfig();
        Command.clientMessage("Saved config!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".save";
    }
}
