// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class FullbrightMod extends Module
{
    public static FullbrightMod INSTANCE;
    public int level;
    
    public FullbrightMod() {
        super("Fullbright");
        this.level = 0;
        FullbrightMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (this.state.enabled || XrayMod.INSTANCE.state.enabled) {
            final int max = 32;
            if (this.level < 32) {
                ++this.level;
            }
            else if (this.level > 32) {
                this.level = 32;
            }
        }
        else {
            final int min = 8;
            if (this.level > 8) {
                this.level = 8;
            }
            else if (this.level > 0) {
                --this.level;
            }
            else if (this.level < 0) {
                this.level = 0;
            }
        }
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public boolean shouldTick() {
        return true;
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
}
