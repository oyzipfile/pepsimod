// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.util.config.impl.CpuLimitTranslator;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class UnfocusedCPUMod extends Module
{
    public static UnfocusedCPUMod INSTANCE;
    
    public UnfocusedCPUMod() {
        super("UnfocusedCPU");
        UnfocusedCPUMod.INSTANCE = this;
        UnfocusedCPUMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        UnfocusedCPUMod.INSTANCE = this;
    }
    
    @Override
    public void onDisable() {
        UnfocusedCPUMod.INSTANCE = this;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        UnfocusedCPUMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)CpuLimitTranslator.INSTANCE.limit, "limit", OptionCompletions.INTEGER, value -> {
                CpuLimitTranslator.INSTANCE.limit = Math.max(1, value);
                return Boolean.valueOf(true);
            }, () -> CpuLimitTranslator.INSTANCE.limit, "Limit", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 1, 60, 1)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        return String.valueOf(CpuLimitTranslator.INSTANCE.limit);
    }
}
