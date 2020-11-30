// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.render.NoOverlayMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemRenderer.class })
public abstract class MixinItemRenderer
{
    @Inject(method = { "renderWaterOverlayTexture" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderWaterOverlayTexture(final float partialTicks, final CallbackInfo callbackInfo) {
        if (NoOverlayMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderFireInFirstPerson(final CallbackInfo callbackInfo) {
        if (NoOverlayMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
}
