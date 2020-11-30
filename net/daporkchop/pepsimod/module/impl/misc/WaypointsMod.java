// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import java.awt.Color;
import net.daporkchop.pepsimod.util.PepsiUtils;
import java.util.Iterator;
import java.util.ArrayList;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.misc.waypoints.Waypoint;
import java.util.Collection;
import net.daporkchop.pepsimod.module.api.Module;

public class WaypointsMod extends Module
{
    public static WaypointsMod INSTANCE;
    protected Collection<Waypoint> renderCache;
    
    public WaypointsMod() {
        super("Waypoints");
        WaypointsMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        WaypointsMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)WaypointsTranslator.INSTANCE.tracers, "tracers", OptionCompletions.BOOLEAN, value -> {
                WaypointsTranslator.INSTANCE.tracers = value;
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.tracers, "Tracers"), new ModuleOption((T)WaypointsTranslator.INSTANCE.nametag, "nametag", OptionCompletions.BOOLEAN, value -> {
                WaypointsTranslator.INSTANCE.nametag = value;
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.nametag, "Nametag"), new ModuleOption((T)WaypointsTranslator.INSTANCE.dist, "distance", OptionCompletions.BOOLEAN, value -> {
                WaypointsTranslator.INSTANCE.dist = value;
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.dist, "Distance"), new ModuleOption((T)WaypointsTranslator.INSTANCE.coords, "coords", OptionCompletions.BOOLEAN, value -> {
                WaypointsTranslator.INSTANCE.coords = value;
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.coords, "Coords"), new ModuleOption((T)WaypointsTranslator.INSTANCE.r, "red", new String[] { "0", "256" }, value -> {
                WaypointsTranslator.INSTANCE.r = Math.max(0, Math.min(value, 255));
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.r, "Red", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)), new ModuleOption((T)WaypointsTranslator.INSTANCE.g, "green", new String[] { "0", "256" }, value -> {
                WaypointsTranslator.INSTANCE.g = Math.max(0, Math.min(value, 255));
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.g, "Green", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)), new ModuleOption((T)WaypointsTranslator.INSTANCE.b, "blue", new String[] { "0", "256" }, value -> {
                WaypointsTranslator.INSTANCE.b = Math.max(0, Math.min(value, 255));
                return Boolean.valueOf(true);
            }, () -> WaypointsTranslator.INSTANCE.b, "Blue", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)) };
    }
    
    @Override
    public void renderOverlay(final WorldRenderer renderer) {
        this.renderCache = new ArrayList<Waypoint>(WaypointsTranslator.INSTANCE.getWaypoints());
        renderer.color(WaypointsTranslator.INSTANCE.r, WaypointsTranslator.INSTANCE.g, WaypointsTranslator.INSTANCE.b);
        for (final Waypoint waypoint : this.renderCache) {
            renderer.lineFromEyes(waypoint.x, waypoint.y, waypoint.z);
        }
    }
    
    public void renderText() {
        if (this.state.enabled && WaypointsTranslator.INSTANCE.nametag) {
            if (this.renderCache == null) {
                this.renderCache = WaypointsTranslator.INSTANCE.getWaypoints();
            }
            for (final Waypoint waypoint : this.renderCache) {
                String text = waypoint.name;
                if (WaypointsTranslator.INSTANCE.coords) {
                    text = text + " §7" + waypoint.x + "§f, §7" + waypoint.y + "§f, §7" + waypoint.z;
                }
                if (WaypointsTranslator.INSTANCE.dist) {
                    text = text + " §f (§b" + PepsiUtils.roundFloatForSlider((float)WaypointsMod.mc.player.getDistance((double)waypoint.x, (double)waypoint.y, (double)waypoint.z)) + "§f)";
                }
                PepsiUtils.renderFloatingText(text, (float)waypoint.x, (float)(waypoint.y + 1), (float)waypoint.z, Color.white.getRGB(), true, 1.0f);
            }
            this.renderCache.clear();
        }
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
}
