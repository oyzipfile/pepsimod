// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.minecraft.util.math.BlockPos;
import net.daporkchop.pepsimod.command.api.Command;

public class GoToCommand extends Command
{
    public static GoToCommand INSTANCE;
    public boolean enabled;
    public BlockPos endGoal;
    
    public GoToCommand() {
        super("goto");
        GoToCommand.INSTANCE = this;
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        Command.clientMessage("§cThe pathfiner is currently disabled.");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".goto";
    }
}
