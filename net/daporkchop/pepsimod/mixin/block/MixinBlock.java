// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.block;

import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.movement.NoClipMod;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.daporkchop.pepsimod.util.config.impl.XrayTranslator;
import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import net.daporkchop.pepsimod.optimization.BlockID;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mixin({ Block.class })
public abstract class MixinBlock extends IForgeRegistryEntry.Impl<Block> implements BlockID
{
    public int pepsimod_id;
    
    public MixinBlock() {
        this.pepsimod_id = 0;
    }
    
    public int getBlockId() {
        return this.pepsimod_id;
    }
    
    public void internal_setBlockId(final int id) {
        this.pepsimod_id = id;
    }
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsFullCube(final IBlockState state, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (PepsiConstants.pepsimod.hasInitializedModules) {
            if (XrayMod.INSTANCE.state.enabled) {
                callbackInfoReturnable.setReturnValue(XrayTranslator.INSTANCE.isTargeted(this));
            }
            else if (FreecamMod.INSTANCE.state.enabled || NoClipMod.INSTANCE.state.enabled) {
                callbackInfoReturnable.setReturnValue(false);
            }
        }
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void preShouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callbackInfo) {
        if (PepsiConstants.pepsimod.hasInitializedModules && XrayMod.INSTANCE.state.enabled) {
            callbackInfo.setReturnValue(true);
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroy" }, at = { @At("HEAD") })
    public void preOnPlayerDestroy(final World worldIn, final BlockPos pos, final IBlockState state, final CallbackInfo callbackInfo) {
        if (worldIn.isRemote) {
            AnnouncerMod.INSTANCE.onBreakBlock(state);
        }
    }
}
