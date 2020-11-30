// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class AntiHungerMod extends Module
{
    public static AntiHungerMod INSTANCE;
    public static boolean ANTI_HUNGER;
    
    public AntiHungerMod() {
        super("AntiHunger");
        AntiHungerMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        AntiHungerMod.ANTI_HUNGER = true;
    }
    
    @Override
    public void onDisable() {
        AntiHungerMod.ANTI_HUNGER = false;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    static {
        AntiHungerMod.ANTI_HUNGER = false;
    }
}
