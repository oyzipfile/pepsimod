// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.util.config.impl.VelocityTranslator;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class VelocityMod extends Module
{
    public static VelocityMod INSTANCE;
    
    public VelocityMod() {
        super("Velocity");
        VelocityMod.INSTANCE = this;
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
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)1.0f, "strength", new String[] { "1.0", "0.0" }, value -> {
                VelocityTranslator.INSTANCE.multiplier = value;
                return Boolean.valueOf(true);
            }, () -> VelocityTranslator.INSTANCE.multiplier, "Strength", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 1.0f, 0.1f)) };
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        return String.valueOf((float)this.getOptionByName("strength").getValue());
    }
    
    public float getVelocity() {
        if (this.state.enabled) {
            return VelocityTranslator.INSTANCE.multiplier;
        }
        return 1.0f;
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
