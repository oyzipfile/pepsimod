// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class WPlayer extends PepsiConstants
{
    public static void swingArmClient() {
        WPlayer.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public static void swingArmPacket() {
        WPlayer.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    }
    
    public static void attackEntity(final Entity entity) {
        Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)WPlayer.mc.player, entity);
        swingArmClient();
    }
    
    public static void sendAttackPacket(final Entity entity) {
        WPlayer.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
    }
    
    public static float getCooldown() {
        return WPlayer.mc.player.getCooledAttackStrength(0.0f);
    }
    
    public static void addPotionEffect(final Potion potion) {
        WPlayer.mc.player.addPotionEffect(new PotionEffect(potion, 10801220));
    }
    
    public static void removePotionEffect(final Potion potion) {
        WPlayer.mc.player.removePotionEffect(potion);
    }
}
