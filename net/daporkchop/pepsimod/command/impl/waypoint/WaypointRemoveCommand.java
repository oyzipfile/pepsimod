// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl.waypoint;

import java.util.Iterator;
import java.util.Collection;
import net.daporkchop.pepsimod.util.misc.waypoints.Waypoint;
import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.command.api.Command;

public class WaypointRemoveCommand extends Command
{
    public WaypointRemoveCommand() {
        super("waypointremove");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        Waypoint waypoint = null;
        if (args.length < 2) {
            Command.clientMessage("§cNo waypoint given!");
            return;
        }
        if ((waypoint = WaypointsTranslator.INSTANCE.removeWaypoint(args[1])) == null) {
            Command.clientMessage("No waypoint with name:§o" + args[1] + "§r!");
            return;
        }
        Command.clientMessage("Removed waypoint: §o" + waypoint.name + "§r!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        if (args.length == 1) {
            return cmd + " " + WaypointsTranslator.INSTANCE.getWaypoints().iterator().next().name;
        }
        if (args.length == 2) {
            final Collection<Waypoint> waypoints = WaypointsTranslator.INSTANCE.getWaypoints();
            for (final Waypoint waypoint : waypoints) {
                if (waypoint.name.startsWith(args[1])) {
                    return args[0] + " " + waypoint.name;
                }
            }
        }
        return cmd;
    }
    
    @Override
    public String[] aliases() {
        return new String[] { "waypointremove", "wremove", "waypointdelete", "wdelete" };
    }
}
