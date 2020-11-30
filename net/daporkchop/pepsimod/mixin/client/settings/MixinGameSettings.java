// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.settings;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.render.ZoomMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GameSettings.class })
public abstract class MixinGameSettings
{
    @Inject(method = { "setOptionFloatValue" }, at = { @At("HEAD") })
    public void preSetOptionFloatValue(final GameSettings.Options settingsOption, final float value, final CallbackInfo callbackInfo) {
        if (settingsOption == GameSettings.Options.FOV) {
            ZoomMod.INSTANCE.fov = value;
        }
    }
}
