// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class BoatFlyMod extends Module
{
    public static BoatFlyMod INSTANCE;
    
    public BoatFlyMod() {
        super("BoatFly");
        BoatFlyMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (BoatFlyMod.mc.player.isRiding()) {
            BoatFlyMod.mc.player.getRidingEntity().motionY = (ReflectionStuff.getPressed(BoatFlyMod.mc.gameSettings.keyBindJump) ? 0.3 : 0.0);
        }
    }
    
    @Override
    public void init() {
        BoatFlyMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
