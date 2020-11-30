// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class HorseJumpPowerMod extends Module
{
    public static HorseJumpPowerMod INSTANCE;
    
    public HorseJumpPowerMod() {
        super("HorseJumpPower");
        HorseJumpPowerMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        ReflectionStuff.setHorseJumpPower(1.0f);
    }
    
    @Override
    public void init() {
        HorseJumpPowerMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
