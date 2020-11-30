// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.network;

import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.minecraft.util.text.ITextComponent;
import net.daporkchop.pepsimod.misc.TickRate;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.ModuleManager;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.annotation.Nullable;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager
{
    @Inject(method = { "dispatchPacket" }, at = { @At("HEAD") }, cancellable = true)
    public void preSend(final Packet<?> inPacket, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] futureListeners, final CallbackInfo callbackInfo) {
        if (ModuleManager.preSendPacket(inPacket)) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "dispatchPacket" }, at = { @At("RETURN") })
    public void postSend(final Packet<?> inPacket, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] futureListeners, final CallbackInfo callbackInfo) {
        ModuleManager.postSendPacket(inPacket);
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void preProcess(final ChannelHandlerContext p_channelRead0_1_, final Packet<?> p_channelRead0_2_, final CallbackInfo callbackInfo) {
        TickRate.update(p_channelRead0_2_);
        if (ModuleManager.preRecievePacket(p_channelRead0_2_)) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") })
    public void postProcess(final ChannelHandlerContext p_channelRead0_1_, final Packet<?> p_channelRead0_2_, final CallbackInfo callbackInfo) {
        ModuleManager.postRecievePacket(p_channelRead0_2_);
    }
    
    @Inject(method = { "closeChannel" }, at = { @At("HEAD") })
    public void preCloseChannel(final ITextComponent message, final CallbackInfo callbackInfo) {
        TickRate.reset();
        if (FreecamMod.INSTANCE.state.enabled) {
            ModuleManager.disableModule(FreecamMod.INSTANCE);
        }
    }
    
    @Inject(method = { "exceptionCaught" }, at = { @At("RETURN") })
    public void postExceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_, final CallbackInfo callbackInfo) {
        p_exceptionCaught_2_.printStackTrace(System.out);
    }
}
