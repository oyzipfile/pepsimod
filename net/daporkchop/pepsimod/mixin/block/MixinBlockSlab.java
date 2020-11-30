// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.block;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockSlab;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.block.Block;

@Mixin({ BlockSlab.class })
public abstract class MixinBlockSlab extends Block
{
    protected MixinBlockSlab() {
        super((Material)null);
    }
    
    @Shadow
    public abstract boolean isDouble();
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsFullCube(final IBlockState state, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (PepsiConstants.pepsimod.hasInitializedModules && FreecamMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
