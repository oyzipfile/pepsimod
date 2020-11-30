// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.optimization.OverrideCounter;
import java.util.concurrent.atomic.AtomicBoolean;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoWalkMod extends Module
{
    public static AutoWalkMod INSTANCE;
    protected final AtomicBoolean incremented;
    
    public AutoWalkMod() {
        super("AutoWalk");
        AutoWalkMod.INSTANCE = this;
        this.incremented = new AtomicBoolean(false);
    }
    
    @Override
    public void onEnable() {
        if (!this.incremented.getAndSet(true)) {
            ((OverrideCounter)AutoWalkMod.mc.gameSettings.keyBindForward).incrementOverride();
        }
    }
    
    @Override
    public void onDisable() {
        if (this.incremented.getAndSet(false)) {
            ((OverrideCounter)AutoWalkMod.mc.gameSettings.keyBindForward).decrementOverride();
        }
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        AutoWalkMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
