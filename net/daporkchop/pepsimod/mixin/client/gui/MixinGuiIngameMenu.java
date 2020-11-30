// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.PepsiConstants;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.impl.render.ZoomMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiIngameMenu.class })
public abstract class MixinGuiIngameMenu
{
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void preActionPerformed(final GuiButton button, final CallbackInfo callbackInfo) {
        if (ZoomMod.INSTANCE.state.enabled) {
            ModuleManager.disableModule(ZoomMod.INSTANCE);
            PepsiConstants.mc.gameSettings.fovSetting = ZoomMod.INSTANCE.fov;
        }
    }
}
