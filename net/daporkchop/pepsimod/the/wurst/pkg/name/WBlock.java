// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class WBlock extends PepsiConstants
{
    public static IBlockState getState(final BlockPos pos) {
        return WBlock.mc.world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static int getId(final BlockPos pos) {
        return Block.getIdFromBlock(getBlock(pos));
    }
    
    public static String getName(final Block block) {
        return "" + Block.REGISTRY.getNameForObject((Object)block);
    }
    
    public static Material getMaterial(final BlockPos pos) {
        return getState(pos).getMaterial();
    }
    
    public static AxisAlignedBB getBoundingBox(final BlockPos pos) {
        return getState(pos).getBoundingBox((IBlockAccess)WBlock.mc.world, pos).offset(pos);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static float getHardness(final BlockPos pos) {
        return getState(pos).getPlayerRelativeBlockHardness((EntityPlayer)WBlock.mc.player, (World)WBlock.mc.world, pos);
    }
}
