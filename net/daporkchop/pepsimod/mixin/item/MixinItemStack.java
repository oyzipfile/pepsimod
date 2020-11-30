// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.item;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.block.Block;
import net.daporkchop.pepsimod.module.impl.combat.BedBomberMod;
import net.minecraft.item.ItemBed;
import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemStack.class })
public abstract class MixinItemStack
{
    @Inject(method = { "onItemUse" }, at = { @At("HEAD") })
    public void preOnItemUse(final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final CallbackInfoReturnable<EnumActionResult> callbackInfo) {
        if (worldIn.isRemote) {
            final ItemStack this_ = ItemStack.class.cast(this);
            if (this_.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)this_.getItem()).getBlock();
                AnnouncerMod.INSTANCE.onPlaceBlock(block);
            }
            else if (this_.getItem() instanceof ItemBed) {
                BedBomberMod.INSTANCE.onPlaceBed();
            }
        }
    }
}
