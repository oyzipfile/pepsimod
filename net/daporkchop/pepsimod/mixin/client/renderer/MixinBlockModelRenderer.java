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
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.BlockModelRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockModelRenderer.class })
public abstract class MixinBlockModelRenderer
{
    @Inject(method = { "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Z)Z" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderModel(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final IBlockState blockStateIn, final BlockPos blockPosIn, final BufferBuilder buffer, final boolean checkSides, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (XrayMod.INSTANCE.state.enabled && !XrayTranslator.INSTANCE.isTargeted(blockStateIn.getBlock())) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelSmooth(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderQuadsSmooth(final IBlockAccess access, final IBakedModel model, final IBlockState stateIn, final BlockPos pos, final BufferBuilder bufferBuilder, final boolean idk, final long ok, final CallbackInfoReturnable<Boolean> returnable) {
        if (XrayMod.INSTANCE.state.enabled && !XrayTranslator.INSTANCE.isTargeted(stateIn.getBlock())) {
            returnable.setReturnValue(false);
            returnable.cancel();
        }
    }
}
