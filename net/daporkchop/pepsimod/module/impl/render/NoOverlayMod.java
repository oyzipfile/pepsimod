// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class NoOverlayMod extends Module
{
    public static NoOverlayMod INSTANCE;
    
    public NoOverlayMod() {
        super("NoOverlay");
        NoOverlayMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        NoOverlayMod.INSTANCE = this;
    }
    
    @Override
    public void onDisable() {
        NoOverlayMod.INSTANCE = this;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        NoOverlayMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
}
