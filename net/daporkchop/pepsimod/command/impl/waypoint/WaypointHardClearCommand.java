// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl.waypoint;

import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.command.api.Command;

public class WaypointHardClearCommand extends Command
{
    public WaypointHardClearCommand() {
        super("waypointhardclear");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        WaypointsTranslator.INSTANCE.hardClearWaypoints();
        Command.clientMessage("Cleared all waypoints on all servers!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return cmd;
    }
    
    @Override
    public String[] aliases() {
        return new String[] { "waypointhardclear", "whardclear" };
    }
}
