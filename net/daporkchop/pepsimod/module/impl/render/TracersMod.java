// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import java.util.Iterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.util.EntityFakePlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.util.config.impl.TracersTranslator;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.RenderColor;
import net.daporkchop.pepsimod.module.api.Module;

public class TracersMod extends Module
{
    public static final RenderColor friendColor;
    public static final RenderColor monsterColor;
    public static final RenderColor animalColor;
    public static final RenderColor itemColor;
    public static final RenderColor distSafe;
    public static final RenderColor dist20;
    public static final RenderColor dist15;
    public static final RenderColor dist10;
    public static final RenderColor dist5;
    public static TracersMod INSTANCE;
    
    public TracersMod() {
        super("Tracers");
        TracersMod.INSTANCE = this;
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
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)false, "sleeping", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.sleeping = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.sleeping, "Sleeping"), new ModuleOption((T)false, "invisible", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.invisible = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.invisible, "Invisible"), new ModuleOption((T)true, "friendColors", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.friendColors = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.friendColors, "FriendColors"), new ModuleOption((T)false, "animals", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.animals = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.animals, "Animals"), new ModuleOption((T)false, "monsters", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.monsters = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.monsters, "Monsters"), new ModuleOption((T)false, "players", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.players = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.players, "Players"), new ModuleOption((T)false, "items", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.items = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.items, "Items"), new ModuleOption((T)false, "everything", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.everything = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.everything, "Everything"), new ModuleOption((T)true, "distanceColor", OptionCompletions.BOOLEAN, value -> {
                TracersTranslator.INSTANCE.distanceColor = value;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.distanceColor, "DistanceColor"), new ModuleOption((T)2.0f, "width", new String[] { "0.5", "1.0", "1.5", "2.0", "2.5" }, val -> {
                TracersTranslator.INSTANCE.width = val;
                return Boolean.valueOf(true);
            }, () -> TracersTranslator.INSTANCE.width, "Width", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 10.0f, 0.1f)) };
    }
    
    @Override
    public void renderOverlay(final WorldRenderer renderer) {
        renderer.width(TracersTranslator.INSTANCE.width);
        RenderColor color = null;
        for (final Entity entity : TracersMod.mc.world.getLoadedEntityList()) {
            if (Math.abs(entity.posY - TracersMod.mc.player.posY) > 1000000.0) {
                continue;
            }
            if (!TracersTranslator.INSTANCE.invisible && entity.isInvisible()) {
                continue;
            }
            if ((TracersTranslator.INSTANCE.players || TracersTranslator.INSTANCE.everything) && entity instanceof EntityPlayer) {
                if (entity == TracersMod.mc.player) {
                    continue;
                }
                if (entity instanceof EntityFakePlayer) {
                    continue;
                }
                if (!TracersTranslator.INSTANCE.sleeping && ReflectionStuff.getSleeping((EntityPlayer)entity)) {
                    continue;
                }
                if (TracersTranslator.INSTANCE.friendColors && FriendsTranslator.INSTANCE.isFriend(entity)) {
                    color = TracersMod.friendColor;
                }
                else if (TracersTranslator.INSTANCE.distanceColor) {
                    final double dist = TracersMod.mc.player.getDistanceSq(entity);
                    if (dist >= 625.0) {
                        color = TracersMod.distSafe;
                    }
                    else if (dist >= 400.0) {
                        color = TracersMod.dist20;
                    }
                    else if (dist >= 225.0) {
                        color = TracersMod.dist15;
                    }
                    else if (dist >= 100.0) {
                        color = TracersMod.dist10;
                    }
                    else {
                        color = TracersMod.dist5;
                    }
                }
            }
            else if ((TracersTranslator.INSTANCE.monsters || TracersTranslator.INSTANCE.everything) && entity instanceof EntityMob) {
                if (TracersTranslator.INSTANCE.distanceColor) {
                    final double dist = TracersMod.mc.player.getDistanceSq(entity);
                    if (dist >= 625.0) {
                        color = TracersMod.distSafe;
                    }
                    else if (dist >= 400.0) {
                        color = TracersMod.dist20;
                    }
                    else if (dist >= 225.0) {
                        color = TracersMod.dist15;
                    }
                    else if (dist >= 100.0) {
                        color = TracersMod.dist10;
                    }
                    else {
                        color = TracersMod.dist5;
                    }
                }
                else {
                    color = TracersMod.monsterColor;
                }
            }
            else if ((TracersTranslator.INSTANCE.animals || TracersTranslator.INSTANCE.everything) && entity instanceof EntityAnimal) {
                color = TracersMod.animalColor;
            }
            else {
                if ((!TracersTranslator.INSTANCE.items && !TracersTranslator.INSTANCE.everything) || !(entity instanceof EntityItem)) {
                    continue;
                }
                color = TracersMod.itemColor;
            }
            renderer.color(color).lineFromEyes(entity);
        }
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
    
    static {
        friendColor = new RenderColor(76, 144, 255, 255);
        monsterColor = new RenderColor(128, 0, 0, 255);
        animalColor = new RenderColor(0, 0, 204, 255);
        itemColor = new RenderColor(179, 179, 179, 255);
        distSafe = new RenderColor(0, 255, 0, 255);
        dist20 = new RenderColor(128, 255, 0, 255);
        dist15 = new RenderColor(255, 255, 0, 255);
        dist10 = new RenderColor(255, 128, 0, 255);
        dist5 = new RenderColor(255, 0, 0, 255);
    }
}
