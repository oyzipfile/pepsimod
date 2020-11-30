// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.command.api.Command;

public class ListCommand extends Command
{
    public ListCommand() {
        super("list");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        String s = "";
        for (int i = 0; i < ModuleManager.AVALIBLE_MODULES.size(); ++i) {
            s = s + ModuleManager.AVALIBLE_MODULES.get(i).name + ((i + 1 == ModuleManager.AVALIBLE_MODULES.size()) ? "" : ", ");
        }
        Command.clientMessage("Available modules: §o" + s);
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".list";
    }
}
