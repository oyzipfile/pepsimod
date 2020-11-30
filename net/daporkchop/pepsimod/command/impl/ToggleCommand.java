// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.command.api.Command;

public class ToggleCommand extends Command
{
    public ToggleCommand() {
        super("toggle");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length < 2) {
            String s = "";
            for (int i = 0; i < ModuleManager.AVALIBLE_MODULES.size(); ++i) {
                s = s + ModuleManager.AVALIBLE_MODULES.get(i).name + ((i + 1 == ModuleManager.AVALIBLE_MODULES.size()) ? "" : ", ");
            }
            Command.clientMessage("Available modules: §o" + s);
            return;
        }
        final Module module = ModuleManager.getModuleByName(args[1]);
        if (module == null) {
            Command.clientMessage("No module was found by the name: §o" + args[1]);
        }
        else {
            ModuleManager.toggleModule(module);
            Command.clientMessage("Toggled module: §o" + module.name);
        }
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                return ".toggle " + ModuleManager.AVALIBLE_MODULES.get(0).name;
            }
            case 2: {
                if (args[1].isEmpty()) {
                    return ".toggle " + ModuleManager.AVALIBLE_MODULES.get(0).name;
                }
                for (final Module module : ModuleManager.AVALIBLE_MODULES) {
                    if (module.name.startsWith(args[1])) {
                        return ".toggle " + module.name;
                    }
                }
                return "";
            }
            default: {
                return ".toggle";
            }
        }
    }
}
