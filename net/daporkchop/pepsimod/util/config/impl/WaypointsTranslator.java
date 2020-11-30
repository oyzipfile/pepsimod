// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import net.minecraft.client.multiplayer.ServerData;
import java.util.Collection;
import java.util.UUID;
import com.google.gson.JsonElement;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.misc.waypoints.Waypoint;
import net.daporkchop.pepsimod.util.misc.waypoints.DimensionWaypoints;
import java.util.Map;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.daporkchop.pepsimod.util.misc.waypoints.ServerWaypoints;
import java.util.Hashtable;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class WaypointsTranslator extends PepsiConstants implements IConfigTranslator
{
    public static final WaypointsTranslator INSTANCE;
    public boolean tracers;
    public int r;
    public int g;
    public int b;
    public boolean nametag;
    public boolean dist;
    public boolean coords;
    public Hashtable<String, ServerWaypoints> identifiersToServerWaypoints;
    
    public static String getCurrentServerIdentifier() {
        return WaypointsTranslator.mc.isIntegratedServerRunning() ? WaypointsTranslator.mc.getIntegratedServer().getFolderName() : Optional.ofNullable(WaypointsTranslator.mc.getCurrentServerData()).map(current -> current.serverIP).orElse("realms");
    }
    
    private WaypointsTranslator() {
        this.tracers = false;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.nametag = false;
        this.dist = true;
        this.coords = false;
        this.identifiersToServerWaypoints = new Hashtable<String, ServerWaypoints>();
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("tracers", Boolean.valueOf(this.tracers));
        json.addProperty("r", (Number)this.r);
        json.addProperty("g", (Number)this.g);
        json.addProperty("b", (Number)this.b);
        json.addProperty("nametag", Boolean.valueOf(this.nametag));
        json.addProperty("dist", Boolean.valueOf(this.dist));
        json.addProperty("coords", Boolean.valueOf(this.coords));
        for (final Map.Entry<String, ServerWaypoints> server : this.identifiersToServerWaypoints.entrySet()) {
            for (final Map.Entry<Integer, DimensionWaypoints> dimension : server.getValue().waypoints.entrySet()) {
                for (final Map.Entry<String, Waypoint> waypoint : dimension.getValue().waypoints.entrySet()) {
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".name", waypoint.getValue().name);
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".x", (Number)waypoint.getValue().x);
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".y", (Number)waypoint.getValue().y);
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".z", (Number)waypoint.getValue().z);
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".dim", (Number)waypoint.getValue().dim);
                    json.addProperty(server.getKey() + '.' + dimension.getKey() + '.' + waypoint.getKey() + ".shown", Boolean.valueOf(waypoint.getValue().shown));
                }
            }
        }
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.tracers = this.getBoolean(json, "tracers", this.tracers);
        this.r = this.getInt(json, "r", this.r);
        this.g = this.getInt(json, "g", this.g);
        this.b = this.getInt(json, "b", this.b);
        this.nametag = this.getBoolean(json, "nametag", this.nametag);
        this.dist = this.getBoolean(json, "dist", this.dist);
        this.coords = this.getBoolean(json, "coords", this.coords);
        for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().endsWith(".name")) {
                final String baseKey = entry.getKey().substring(0, entry.getKey().length() - 5);
                final String name = this.getString(json, baseKey + ".name", UUID.randomUUID().toString());
                final int x = this.getInt(json, baseKey + ".x", 0);
                final int y = this.getInt(json, baseKey + ".y", 0);
                final int z = this.getInt(json, baseKey + ".z", 0);
                final int dim = this.getInt(json, baseKey + ".dim", 0);
                final boolean shown = this.getBoolean(json, baseKey + ".shown", true);
                final String serverIdentifier = this.getString(json, baseKey + ".server", "localhost");
                final ServerWaypoints serber = this.identifiersToServerWaypoints.computeIfAbsent(serverIdentifier, k -> new ServerWaypoints());
                final DimensionWaypoints dimension = serber.waypoints.computeIfAbsent(dim, k -> new DimensionWaypoints());
                dimension.waypoints.put(name, new Waypoint(name, x, y, z, shown, dim));
            }
        }
    }
    
    @Override
    public String name() {
        return "waypoints";
    }
    
    public Collection<Waypoint> getWaypoints() {
        final ServerWaypoints serverWaypoints = this.identifiersToServerWaypoints.computeIfAbsent(getCurrentServerIdentifier(), k -> new ServerWaypoints());
        final DimensionWaypoints dimensionWaypoints = serverWaypoints.waypoints.computeIfAbsent(WaypointsTranslator.mc.player.dimension, k -> new DimensionWaypoints());
        return dimensionWaypoints.waypoints.values();
    }
    
    public Waypoint getWaypoint(final String name) {
        final ServerWaypoints serverWaypoints = this.identifiersToServerWaypoints.computeIfAbsent(getCurrentServerIdentifier(), k -> new ServerWaypoints());
        final DimensionWaypoints dimensionWaypoints = serverWaypoints.waypoints.computeIfAbsent(WaypointsTranslator.mc.player.dimension, k -> new DimensionWaypoints());
        return dimensionWaypoints.waypoints.get(name);
    }
    
    public Waypoint addWaypoint(final Waypoint waypoint) {
        final ServerWaypoints serverWaypoints = this.identifiersToServerWaypoints.computeIfAbsent(getCurrentServerIdentifier(), k -> new ServerWaypoints());
        final DimensionWaypoints dimensionWaypoints = serverWaypoints.waypoints.computeIfAbsent(WaypointsTranslator.mc.player.dimension, k -> new DimensionWaypoints());
        dimensionWaypoints.waypoints.put(waypoint.name, waypoint);
        return waypoint;
    }
    
    public Waypoint addWaypoint(final String name) {
        return this.addWaypoint(new Waypoint(name, WaypointsTranslator.mc.player.posX, WaypointsTranslator.mc.player.posY, WaypointsTranslator.mc.player.posZ, WaypointsTranslator.mc.player.dimension));
    }
    
    public Waypoint addWaypoint() {
        return this.addWaypoint(UUID.randomUUID().toString());
    }
    
    public Waypoint removeWaypoint(final String name) {
        final ServerWaypoints serverWaypoints = this.identifiersToServerWaypoints.computeIfAbsent(getCurrentServerIdentifier(), k -> new ServerWaypoints());
        final DimensionWaypoints dimensionWaypoints = serverWaypoints.waypoints.computeIfAbsent(WaypointsTranslator.mc.player.dimension, k -> new DimensionWaypoints());
        return dimensionWaypoints.waypoints.remove(name);
    }
    
    public void clearWaypoints() {
        this.identifiersToServerWaypoints.remove(getCurrentServerIdentifier());
    }
    
    public void hardClearWaypoints() {
        this.identifiersToServerWaypoints = new Hashtable<String, ServerWaypoints>();
    }
    
    static {
        INSTANCE = new WaypointsTranslator();
    }
}
