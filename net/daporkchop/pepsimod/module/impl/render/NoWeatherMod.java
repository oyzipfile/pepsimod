// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.util.config.impl.NoWeatherTranslator;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class NoWeatherMod extends Module
{
    public static NoWeatherMod INSTANCE;
    
    public NoWeatherMod() {
        super("NoWeather");
        NoWeatherMod.INSTANCE = this;
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
        NoWeatherMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)false, "disableRain", OptionCompletions.BOOLEAN, value -> {
                NoWeatherTranslator.INSTANCE.disableRain = value;
                return Boolean.valueOf(true);
            }, () -> NoWeatherTranslator.INSTANCE.disableRain, "Disable Rain"), new ModuleOption((T)false, "changeTime", OptionCompletions.BOOLEAN, value -> {
                NoWeatherTranslator.INSTANCE.changeTime = value;
                return Boolean.valueOf(true);
            }, () -> NoWeatherTranslator.INSTANCE.changeTime, "Change Time"), new ModuleOption((T)NoWeatherTranslator.INSTANCE.time, "time", new String[] { "0", "6000", "12000", "18000", "24000" }, value -> {
                if (value < 0 || value > 24000) {
                    Command.clientMessage("Time must be in range 0-24000!");
                    return Boolean.valueOf(false);
                }
                else {
                    NoWeatherTranslator.INSTANCE.time = value;
                    return Boolean.valueOf(true);
                }
            }, () -> NoWeatherTranslator.INSTANCE.time, "Time", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 24000, 500)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
}
