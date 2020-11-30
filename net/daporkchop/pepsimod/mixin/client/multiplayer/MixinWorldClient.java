// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.multiplayer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.World;

@Mixin({ WorldClient.class })
public abstract class MixinWorldClient extends World
{
    public MixinWorldClient() {
        super((ISaveHandler)null, (WorldInfo)null, (WorldProvider)null, (Profiler)null, false);
    }
    
    @Inject(method = { "doVoidFogParticles" }, at = { @At("HEAD") }, cancellable = true)
    public void preDoVoidFogParticles(final int posX, final int posY, final int posZ, final CallbackInfo callbackInfo) {
        if (FreecamMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
}
