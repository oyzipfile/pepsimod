// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl.waypoint;

import net.daporkchop.pepsimod.util.misc.waypoints.Waypoint;
import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.command.api.Command;

public class WaypointAddCommand extends Command
{
    public WaypointAddCommand() {
        super("waypointadd");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        Waypoint waypoint = null;
        if (args.length >= 5) {
            if (WaypointsTranslator.INSTANCE.getWaypoint(args[1]) != null) {
                Command.clientMessage("Waypoint §o" + args[1] + "§r already exists!");
                return;
            }
            int x;
            try {
                x = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                Command.clientMessage("Invalid integer: " + args[2]);
                return;
            }
            int y;
            try {
                y = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException e) {
                Command.clientMessage("Invalid integer: " + args[3]);
                return;
            }
            int z;
            try {
                z = Integer.parseInt(args[4]);
            }
            catch (NumberFormatException e) {
                Command.clientMessage("Invalid integer: " + args[4]);
                return;
            }
            WaypointsTranslator.INSTANCE.addWaypoint(waypoint = new Waypoint(args[1], x, y, z, WaypointAddCommand.mc.player.dimension));
        }
        else if (args.length >= 2) {
            if (WaypointsTranslator.INSTANCE.getWaypoint(args[1]) != null) {
                Command.clientMessage("Waypoint §o" + args[1] + "§r already exists!");
                return;
            }
            waypoint = WaypointsTranslator.INSTANCE.addWaypoint(args[1]);
        }
        else {
            waypoint = WaypointsTranslator.INSTANCE.addWaypoint();
        }
        Command.clientMessage("Added waypoint: §o" + waypoint.name + "§r in dimension " + waypoint.dim + " at XYZ " + waypoint.x + ", " + waypoint.y + ", " + waypoint.z);
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return cmd;
    }
    
    @Override
    public String[] aliases() {
        return new String[] { "waypointadd", "wadd" };
    }
}
