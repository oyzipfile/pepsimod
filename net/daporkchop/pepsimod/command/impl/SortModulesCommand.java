// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.api.ModuleSortType;
import net.daporkchop.pepsimod.command.api.Command;

public class SortModulesCommand extends Command
{
    public static final String[] MODES;
    
    public SortModulesCommand() {
        super("sortmodules");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        String resulttype = null;
        for (int i = 0; i < SortModulesCommand.MODES.length; ++i) {
            final String toanalyze = SortModulesCommand.MODES[i];
            if (toanalyze.startsWith(args[1])) {
                resulttype = toanalyze;
                break;
            }
        }
        if (resulttype == null) {
            Command.clientMessage("Invalid type: " + args[1]);
            Command.clientMessage("Valid types are: alphabetical, default, size, random");
        }
        else {
            final String s = resulttype;
            switch (s) {
                case "alphabetical": {
                    ModuleManager.sortModules(ModuleSortType.ALPHABETICAL);
                    break;
                }
                case "default": {
                    ModuleManager.sortModules(ModuleSortType.DEFAULT);
                    break;
                }
                case "size": {
                    ModuleManager.sortModules(ModuleSortType.SIZE);
                    break;
                }
                case "random": {
                    ModuleManager.sortModules(ModuleSortType.RANDOM);
                    break;
                }
            }
            Command.clientMessage("Sorted modules according to: §l" + resulttype);
        }
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                return ".sortmodules " + SortModulesCommand.MODES[0];
            }
            case 2: {
                if (args[1].isEmpty()) {
                    return ".sortmodules " + SortModulesCommand.MODES[0];
                }
                for (final String mode : SortModulesCommand.MODES) {
                    if (mode.startsWith(args[1])) {
                        return ".sortmodules " + mode;
                    }
                }
                break;
            }
        }
        return ".sortmodules";
    }
    
    static {
        MODES = new String[] { "alphabetical", "default", "size", "random" };
    }
}
