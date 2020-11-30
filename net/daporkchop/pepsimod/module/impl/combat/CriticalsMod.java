// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.minecraft.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.Packet;
import net.daporkchop.pepsimod.util.config.impl.CriticalsTranslator;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class CriticalsMod extends Module
{
    public static CriticalsMod INSTANCE;
    
    public CriticalsMod() {
        super("Criticals");
        CriticalsMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)true, "packet", OptionCompletions.BOOLEAN, value -> {
                CriticalsTranslator.INSTANCE.packet = value;
                return Boolean.valueOf(true);
            }, () -> CriticalsTranslator.INSTANCE.packet, "Packet") };
    }
    
    @Override
    public boolean preSendPacket(final Packet<?> packetIn) {
        if (packetIn instanceof CPacketUseEntity && ((CPacketUseEntity)packetIn).getAction() == CPacketUseEntity.Action.ATTACK) {
            this.doCrit();
        }
        return false;
    }
    
    public void doCrit() {
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().player;
        if (!player.onGround) {
            return;
        }
        if (player.isInWater() || player.isInLava()) {
            return;
        }
        if (this.getOptionByName("packet").getValue()) {
            final double x = player.posX;
            final double y = player.posY;
            final double z = player.posZ;
            final NetworkManager manager = Minecraft.getMinecraft().getConnection().getNetworkManager();
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.0625, z, true));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y + 1.1E-5, z, false));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
        }
        else {
            player.motionY = 0.10000000149011612;
            player.fallDistance = 0.1f;
            player.onGround = false;
        }
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        if (this.getOptionByName("packet").getValue()) {
            return "Packet";
        }
        return "Jump";
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
}
