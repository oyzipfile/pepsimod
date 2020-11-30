// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.renderer.chunk;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ VisGraph.class })
public abstract class MixinVisGraph
{
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void preSetOpaqueCube(final BlockPos pos, final CallbackInfo callbackInfo) {
        if (XrayMod.INSTANCE.state.enabled || FreecamMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
}
