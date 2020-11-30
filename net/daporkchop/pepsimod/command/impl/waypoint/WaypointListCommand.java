// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl.waypoint;

import java.util.Iterator;
import java.util.Collection;
import net.daporkchop.pepsimod.util.misc.waypoints.Waypoint;
import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.command.api.Command;

public class WaypointListCommand extends Command
{
    public WaypointListCommand() {
        super("waypointlist");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        String s = "";
        final Collection<Waypoint> waypoints = WaypointsTranslator.INSTANCE.getWaypoints();
        for (final Waypoint waypoint : waypoints) {
            s = s + waypoint.name + ", ";
        }
        s = s.substring(0, s.length() - 2);
        Command.clientMessage(s);
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return cmd;
    }
    
    @Override
    public String[] aliases() {
        return new String[] { "waypointlist", "wlist" };
    }
}
