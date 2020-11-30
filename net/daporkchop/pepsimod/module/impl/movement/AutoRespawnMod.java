// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoRespawnMod extends Module
{
    public AutoRespawnMod INSTANCE;
    
    public AutoRespawnMod() {
        super("AutoRespawn");
        this.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (AutoRespawnMod.mc.player != null && AutoRespawnMod.mc.player.getHealth() <= 0.0f) {
            AutoRespawnMod.mc.player.respawnPlayer();
        }
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
