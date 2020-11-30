// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.block;

import net.daporkchop.pepsimod.module.impl.movement.JesusMod;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockLiquid;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.block.Block;

@Mixin({ BlockLiquid.class })
public abstract class MixinBlockLiquid extends Block
{
    protected MixinBlockLiquid() {
        super((Material)null);
    }
    
    @Inject(method = { "isPassable" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsPassable(final IBlockAccess worldIn, final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (PepsiConstants.pepsimod.hasInitializedModules && XrayMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
    
    @Inject(method = { "getCollisionBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetCollisionBoundingBox(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> callbackInfoReturnable) {
        if (PepsiConstants.pepsimod.hasInitializedModules && JesusMod.INSTANCE.shouldBeSolid()) {
            callbackInfoReturnable.setReturnValue(MixinBlockLiquid.FULL_BLOCK_AABB);
        }
    }
}
