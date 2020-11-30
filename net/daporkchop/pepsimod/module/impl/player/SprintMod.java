// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class SprintMod extends Module
{
    public static SprintMod INSTANCE;
    
    public SprintMod() {
        super("Sprint");
        SprintMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        final boolean shouldSprint = SprintMod.mc.player.movementInput.moveForward > 0.0f && !SprintMod.mc.player.isSneaking();
        if (shouldSprint) {
            SprintMod.mc.player.setSprinting(true);
        }
    }
    
    @Override
    public void init() {
        SprintMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
}
