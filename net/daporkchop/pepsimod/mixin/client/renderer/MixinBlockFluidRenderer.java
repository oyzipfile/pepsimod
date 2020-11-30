// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.config.impl.XrayTranslator;
import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.BlockFluidRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockFluidRenderer.class })
public abstract class MixinBlockFluidRenderer
{
    @Inject(method = { "renderFluid" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderFluid(final IBlockAccess blockAccess, final IBlockState blockStateIn, final BlockPos blockPosIn, final BufferBuilder worldRendererIn, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (XrayMod.INSTANCE.state.enabled && !XrayTranslator.INSTANCE.isTargeted(blockStateIn.getBlock())) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
}
