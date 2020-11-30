// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.waypoints;

import java.util.Hashtable;

public class ServerWaypoints
{
    public Hashtable<Integer, DimensionWaypoints> waypoints;
    
    public ServerWaypoints() {
        this.waypoints = new Hashtable<Integer, DimensionWaypoints>();
    }
}
