// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.world.GameType;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.daporkchop.pepsimod.module.impl.player.AutoEatMod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoToolMod extends Module
{
    public static AutoToolMod INSTANCE;
    public boolean digging;
    public int slot;
    
    public AutoToolMod() {
        super("AutoTool");
        this.digging = false;
        this.slot = -1;
        AutoToolMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        synchronized (this) {
            if (!AutoToolMod.mc.gameSettings.keyBindAttack.isKeyDown() && this.digging) {
                this.digging = false;
                if (this.slot != -1) {
                    ReflectionStuff.setCurrentPlayerItem(AutoToolMod.mc.player.inventory.currentItem = this.slot);
                    AutoToolMod.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.slot));
                    this.slot = -1;
                }
            }
        }
    }
    
    @Override
    public boolean preSendPacket(final Packet<?> packetIn) {
        if (!this.digging && AutoEatMod.INSTANCE.doneEating && packetIn instanceof CPacketPlayerDigging) {
            synchronized (this) {
                final CPacketPlayerDigging pck = (CPacketPlayerDigging)packetIn;
                if (!this.digging && AutoToolMod.mc.playerController.getCurrentGameType() != GameType.CREATIVE && pck.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                    this.digging = true;
                    final int bestIndex = PepsiUtils.getBestTool(AutoToolMod.mc.world.getBlockState(pck.getPosition()).getBlock());
                    if (bestIndex != -1 && bestIndex != AutoToolMod.mc.player.inventory.currentItem) {
                        if (this.slot == -1) {
                            this.slot = AutoToolMod.mc.player.inventory.currentItem;
                        }
                        ReflectionStuff.setCurrentPlayerItem(AutoToolMod.mc.player.inventory.currentItem = bestIndex);
                        AutoToolMod.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(bestIndex));
                        AutoToolMod.mc.getConnection().sendPacket((Packet)packetIn);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void init() {
        AutoToolMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
}
