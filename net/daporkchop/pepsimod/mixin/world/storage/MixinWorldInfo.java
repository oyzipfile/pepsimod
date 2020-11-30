// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.world.storage;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.config.impl.NoWeatherTranslator;
import net.daporkchop.pepsimod.module.impl.render.NoWeatherMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ WorldInfo.class })
public abstract class MixinWorldInfo
{
    @Inject(method = { "getWorldTime" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetWorldTime(final CallbackInfoReturnable<Long> callbackInfoReturnable) {
        if (NoWeatherMod.INSTANCE.state.enabled && NoWeatherTranslator.INSTANCE.changeTime) {
            callbackInfoReturnable.setReturnValue((long)NoWeatherTranslator.INSTANCE.time);
        }
    }
}
