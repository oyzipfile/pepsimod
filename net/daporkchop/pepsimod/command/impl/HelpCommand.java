// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import java.util.Iterator;
import net.daporkchop.pepsimod.command.CommandRegistry;
import net.daporkchop.pepsimod.command.api.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        String toSend = "";
        for (final String command : CommandRegistry.commandNames.keySet()) {
            toSend = toSend + command + ", ";
        }
        toSend = toSend.substring(0, toSend.length() - 2);
        Command.clientMessage(toSend);
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".help";
    }
}
