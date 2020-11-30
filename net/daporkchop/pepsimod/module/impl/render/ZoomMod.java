// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.api.ModuleLaunchState;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.client.settings.GameSettings;
import net.daporkchop.pepsimod.module.api.Module;

public class ZoomMod extends Module
{
    public static ZoomMod INSTANCE;
    public float fov;
    
    public ZoomMod(final int key) {
        super(false, "Zoom", key, true);
        this.fov = -1.0f;
        ZoomMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (this.fov == -1.0f || ZoomMod.mc.gameSettings.fovSetting == this.fov) {
            this.fov = ZoomMod.mc.gameSettings.fovSetting;
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (this.state.enabled) {
            if (ZoomMod.mc.gameSettings.fovSetting > 12.0f) {
                for (int i = 0; i < 100; ++i) {
                    if (ZoomMod.mc.gameSettings.fovSetting > 12.0f) {
                        final GameSettings gameSettings = ZoomMod.mc.gameSettings;
                        gameSettings.fovSetting -= 0.1f;
                    }
                }
            }
        }
        else if (ZoomMod.mc.gameSettings.fovSetting < this.fov) {
            for (int i = 0; i < 100; ++i) {
                final GameSettings gameSettings2 = ZoomMod.mc.gameSettings;
                gameSettings2.fovSetting += 0.1f;
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
    
    @Override
    public ModuleLaunchState getLaunchState() {
        return ModuleLaunchState.DISABLED;
    }
}
