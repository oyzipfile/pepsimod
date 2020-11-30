// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.config.impl.SpeedmineTranslator;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class SpeedmineMod extends Module
{
    public static SpeedmineMod INSTANCE;
    
    public SpeedmineMod() {
        super("Speedmine");
        SpeedmineMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (SpeedmineMod.mc.world != null) {
            if (ReflectionStuff.getCurBlockDamageMP() < SpeedmineTranslator.INSTANCE.speed) {
                ReflectionStuff.setCurBlockDamageMP(SpeedmineTranslator.INSTANCE.speed);
            }
            if (ReflectionStuff.getBlockHitDelay() > 1) {
                ReflectionStuff.setBlockHitDelay(1);
            }
        }
    }
    
    @Override
    public void init() {
        SpeedmineMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)SpeedmineTranslator.INSTANCE.speed, "speed", OptionCompletions.FLOAT, value -> {
                SpeedmineTranslator.INSTANCE.speed = Math.max(0.0f, value);
                return Boolean.valueOf(true);
            }, () -> SpeedmineTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.1f, 1.0f, 0.1f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
}
