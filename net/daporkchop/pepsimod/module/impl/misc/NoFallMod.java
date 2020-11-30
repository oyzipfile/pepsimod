// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class NoFallMod extends Module
{
    public static boolean NO_FALL;
    public static NoFallMod INSTANCE;
    
    public NoFallMod() {
        super("NoFall");
        NoFallMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        NoFallMod.NO_FALL = true;
    }
    
    @Override
    public void onDisable() {
        NoFallMod.NO_FALL = false;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        NoFallMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    static {
        NoFallMod.NO_FALL = false;
    }
}
