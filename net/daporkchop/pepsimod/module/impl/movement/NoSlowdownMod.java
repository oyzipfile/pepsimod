// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.minecraft.init.Blocks;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class NoSlowdownMod extends Module
{
    public static NoSlowdownMod INSTANCE;
    
    public NoSlowdownMod() {
        super("NoSlowdown");
        NoSlowdownMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        this.fastIce();
    }
    
    @Override
    public void onDisable() {
        this.normalIce();
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        NoSlowdownMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
    
    private void fastIce() {
        Blocks.ICE.slipperiness = 0.39f;
        Blocks.PACKED_ICE.slipperiness = 0.39f;
    }
    
    private void normalIce() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
    }
}
