// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.entity.monster.EntityGolem;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.config.impl.ESPTranslator;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.daporkchop.pepsimod.util.RenderColor;
import net.daporkchop.pepsimod.module.api.Module;

public class ESPMod extends Module
{
    public static final RenderColor friendColor;
    public static final RenderColor monsterColor;
    public static final RenderColor animalColor;
    public static final RenderColor golemColor;
    public static final RenderColor playerColor;
    public static ESPMod INSTANCE;
    
    public ESPMod() {
        super("ESP");
        ESPMod.INSTANCE = this;
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
        ESPMod.INSTANCE = this;
    }
    
    @Override
    public void renderWorld(final WorldRenderer renderer) {
        if (!ESPTranslator.INSTANCE.box) {
            return;
        }
        for (final Entity entity : ESPMod.mc.world.loadedEntityList) {
            if (entity == ESPMod.mc.player) {
                continue;
            }
            final RenderColor color = this.chooseColor(entity);
            if (color == null) {
                continue;
            }
            renderer.color(color).outline(entity);
        }
    }
    
    public RenderColor chooseColor(final Entity entity) {
        if (!this.state.enabled) {
            return null;
        }
        if (entity.isInvisible() && !ESPTranslator.INSTANCE.invisible) {
            return null;
        }
        if (ESPTranslator.INSTANCE.animals && entity instanceof EntityAnimal) {
            return ESPMod.animalColor;
        }
        if (ESPTranslator.INSTANCE.monsters && entity instanceof EntityMob) {
            return ESPMod.monsterColor;
        }
        if (ESPTranslator.INSTANCE.players && entity instanceof EntityPlayer) {
            if (ESPTranslator.INSTANCE.friendColors && FriendsTranslator.INSTANCE.isFriend(entity)) {
                return ESPMod.friendColor;
            }
            return ESPMod.playerColor;
        }
        else {
            if (ESPTranslator.INSTANCE.golems && entity instanceof EntityGolem) {
                return ESPMod.golemColor;
            }
            return null;
        }
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)ESPTranslator.INSTANCE.monsters, "monsters", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.monsters = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.monsters, "Monsters"), new ModuleOption((T)ESPTranslator.INSTANCE.animals, "animals", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.animals = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.animals, "Animals"), new ModuleOption((T)ESPTranslator.INSTANCE.players, "players", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.players = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.players, "Players"), new ModuleOption((T)ESPTranslator.INSTANCE.golems, "golems", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.golems = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.golems, "Golems"), new ModuleOption((T)ESPTranslator.INSTANCE.invisible, "invisible", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.invisible = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.invisible, "Invisible"), new ModuleOption((T)ESPTranslator.INSTANCE.friendColors, "friendColors", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.friendColors = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.friendColors, "FriendColors"), new ModuleOption((T)ESPTranslator.INSTANCE.box, "box", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.box = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.box, "Box") };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
    
    static {
        friendColor = new RenderColor(76, 144, 255, 255);
        monsterColor = new RenderColor(128, 0, 0, 255);
        animalColor = new RenderColor(0, 0, 204, 255);
        golemColor = new RenderColor(179, 179, 179, 255);
        playerColor = new RenderColor(255, 255, 0, 255);
    }
}
