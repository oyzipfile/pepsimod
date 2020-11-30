// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl.waypoint;

import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.command.api.Command;

public class WaypointClearCommand extends Command
{
    public WaypointClearCommand() {
        super("waypointclear");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        WaypointsTranslator.INSTANCE.clearWaypoints();
        Command.clientMessage("Deleted all waypoints for this server!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return cmd;
    }
    
    @Override
    public String[] aliases() {
        return new String[] { "waypointclear", "wclear" };
    }
}
