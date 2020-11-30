// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.world;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.config.impl.NoWeatherTranslator;
import net.daporkchop.pepsimod.module.impl.render.NoWeatherMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ World.class })
public abstract class MixinWorld
{
    @Inject(method = { "getCelestialAngle" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetCelestialAngle(final float partialTicks, final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (NoWeatherMod.INSTANCE.state.enabled && NoWeatherTranslator.INSTANCE.changeTime) {
            callbackInfoReturnable.setReturnValue(NoWeatherTranslator.INSTANCE.time + 0.0f);
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "getRainStrength" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetRainStrength(final float partialTicks, final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (NoWeatherMod.INSTANCE.state.enabled && NoWeatherTranslator.INSTANCE.disableRain) {
            callbackInfoReturnable.setReturnValue(0.0f);
            callbackInfoReturnable.cancel();
        }
    }
}
