// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import java.util.Iterator;
import java.util.Map;
import net.daporkchop.pepsimod.command.api.Command;
import java.util.HashMap;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class CommandRegistry extends PepsiConstants
{
    public static HashMap<String, Command> commandNames;
    
    public static String getSuggestionFor(final String input) {
        if (input.length() == 1) {
            return "." + CommandRegistry.commandNames.values().iterator().next().name;
        }
        final String[] split = input.replace(" ", " \u0000").split("\\s+");
        for (int i = split.length - 1; i >= 0; --i) {
            split[i] = split[i].replace("\u0000", "");
        }
        try {
            final String commandName = split[0].substring(1);
            final Command command = CommandRegistry.commandNames.get(commandName);
            if (command != null) {
                return command.getSuggestion(input, split);
            }
            for (final Map.Entry<String, Command> entry : CommandRegistry.commandNames.entrySet()) {
                if (entry.getKey().startsWith(commandName)) {
                    return "." + entry.getKey();
                }
            }
        }
        catch (StringIndexOutOfBoundsException ex) {}
        return "";
    }
    
    public static void registerCommand(final Command command) {
        if (!CommandRegistry.commandNames.values().contains(command)) {
            for (final String s : command.aliases()) {
                CommandRegistry.commandNames.put(s, command);
            }
        }
    }
    
    public static void registerCommands(final Command... toRegister) {
        for (final Command command : toRegister) {
            registerCommand(command);
        }
    }
    
    public static void runCommand(final String command) {
        try {
            final String[] split = command.split(" ");
            final String commandName = split[0].substring(1);
            for (final Map.Entry<String, Command> entry : CommandRegistry.commandNames.entrySet()) {
                if (entry.getKey().equals(commandName)) {
                    entry.getValue().execute(command, split);
                    return;
                }
            }
            CommandRegistry.mc.player.sendMessage((ITextComponent)new TextComponentString("§0§l[§c§lpepsi§9§lmod§0§l]§r§cUnknown command! Use .help for a list of commands!"));
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        catch (StringIndexOutOfBoundsException ex2) {}
    }
    
    static {
        CommandRegistry.commandNames = new HashMap<String, Command>();
    }
}
