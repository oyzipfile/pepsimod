// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.daporkchop.pepsimod.util.config.impl.FlightTranslator;
import net.daporkchop.pepsimod.module.api.Module;

public class FlightMod extends Module
{
    public static FlightMod INSTANCE;
    
    public FlightMod() {
        super("Flight");
        FlightMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        FreecamMod.doMove(FlightTranslator.INSTANCE.speed);
    }
    
    @Override
    public void init() {
        FlightMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)FlightTranslator.INSTANCE.speed, "speed", OptionCompletions.FLOAT, value -> {
                FlightTranslator.INSTANCE.speed = Math.max(0.0f, value);
                return Boolean.valueOf(true);
            }, () -> FlightTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.1f, 10.0f, 0.1f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
