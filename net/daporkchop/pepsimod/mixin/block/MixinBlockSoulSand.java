// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.block;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.movement.NoSlowdownMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockSoulSand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockSoulSand.class })
public abstract class MixinBlockSoulSand
{
    @Inject(method = { "onEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void preOnEntityCollision(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn, final CallbackInfo callbackInfo) {
        if (NoSlowdownMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
}
