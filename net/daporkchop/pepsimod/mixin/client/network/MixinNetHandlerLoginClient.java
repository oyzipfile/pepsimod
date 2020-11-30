// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.network;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.MCLeaks;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.network.NetworkManager;
import net.minecraft.client.network.NetHandlerLoginClient;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.login.INetHandlerLoginClient;

@Mixin({ NetHandlerLoginClient.class })
public abstract class MixinNetHandlerLoginClient implements INetHandlerLoginClient
{
    @Shadow
    @Final
    private NetworkManager networkManager;
    
    @Inject(method = { "handleEncryptionRequest" }, at = { @At("HEAD") }, cancellable = true)
    public void handleEncryptionRequest(final SPacketEncryptionRequest packetIn, final CallbackInfo ci) {
        if (PepsiConstants.pepsimod.isMcLeaksAccount) {
            MCLeaks.joinServerStuff(packetIn, this.networkManager);
            ci.cancel();
        }
    }
}
