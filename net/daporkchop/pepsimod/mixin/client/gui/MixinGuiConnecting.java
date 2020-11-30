// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.PepsiUtils;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiConnecting.class })
public abstract class MixinGuiConnecting
{
    @Inject(method = { "connect" }, at = { @At("HEAD") })
    public void preConnect(final String ip, final int port, final CallbackInfo callbackInfo) {
        PepsiUtils.lastIp = ip;
        PepsiUtils.lastPort = port;
    }
}
