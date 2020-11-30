// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class WPlayerController extends PepsiConstants
{
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }
    
    public static ItemStack windowClick_PICKUP(final int slot) {
        return getPlayerController().windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WPlayerController.mc.player);
    }
    
    public static ItemStack windowClick_QUICK_MOVE(final int slot) {
        return getPlayerController().windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WPlayerController.mc.player);
    }
    
    public static ItemStack windowClick_THROW(final int slot) {
        return getPlayerController().windowClick(0, slot, 1, ClickType.THROW, (EntityPlayer)WPlayerController.mc.player);
    }
    
    public static void processRightClick() {
        getPlayerController().processRightClick((EntityPlayer)WPlayerController.mc.player, (World)WPlayerController.mc.world, EnumHand.MAIN_HAND);
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(WPlayerController.mc.player, WPlayerController.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
}
