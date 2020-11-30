// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.misc.TickRate;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.util.config.impl.TimerTranslator;
import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class TimerMod extends Module
{
    public static TimerMod INSTANCE;
    
    public TimerMod() {
        super("Timer");
        TimerMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        TimerMod.INSTANCE = this;
    }
    
    @Override
    public void onDisable() {
        TimerMod.INSTANCE = this;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        TimerMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)1.0f, "multiplier", new String[] { "1.0", "0.0" }, value -> {
                if (value <= 0.0f) {
                    Command.clientMessage("Multiplier cannot be negative or 0!");
                    return Boolean.valueOf(false);
                }
                else {
                    TimerTranslator.INSTANCE.multiplier = value;
                    return Boolean.valueOf(true);
                }
            }, () -> TimerTranslator.INSTANCE.multiplier, "Multiplier", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 1.0f, 0.01f)), new ModuleOption((T)false, "tps_sync", OptionCompletions.BOOLEAN, value -> {
                TimerTranslator.INSTANCE.tpsSync = value;
                return Boolean.valueOf(true);
            }, () -> TimerTranslator.INSTANCE.tpsSync, "TpsSync") };
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        return TickRate.format.format(this.getMultiplier());
    }
    
    public float getMultiplier() {
        if (!this.state.enabled) {
            return 1.0f;
        }
        if (TimerTranslator.INSTANCE.tpsSync) {
            return TickRate.TPS / 20.0f * TimerTranslator.INSTANCE.multiplier;
        }
        return TimerTranslator.INSTANCE.multiplier;
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
}
