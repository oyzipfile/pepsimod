// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.render.NoOverlayMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiIngame.class })
public abstract class MixinGuiIngame extends Gui
{
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void preRenderPumpkinOverlay(final ScaledResolution scaledRes, final CallbackInfo callbackInfo) {
        if (NoOverlayMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void prerenderPotionEffects(final ScaledResolution resolution, final CallbackInfo callbackInfo) {
        if (!HUDTranslator.INSTANCE.effects) {
            callbackInfo.cancel();
        }
    }
}
