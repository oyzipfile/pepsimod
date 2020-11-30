// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.config.impl.StepTranslator;
import net.daporkchop.pepsimod.module.api.Module;

public class StepMod extends Module
{
    public static StepMod INSTANCE;
    
    public StepMod() {
        super("Step");
        StepMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        if (StepMod.pepsimod.hasInitializedModules) {
            StepMod.mc.player.stepHeight = 0.5f;
        }
    }
    
    @Override
    public void tick() {
        if (StepTranslator.INSTANCE.legit) {
            final EntityPlayerSP player = StepMod.mc.player;
            player.stepHeight = 0.5f;
            if (!player.collidedHorizontally) {
                return;
            }
            if (!player.onGround || player.isOnLadder() || player.isInWater() || player.isInLava()) {
                return;
            }
            if (player.movementInput.moveForward == 0.0f && player.movementInput.moveStrafe == 0.0f) {
                return;
            }
            if (player.movementInput.jump) {
                return;
            }
            final AxisAlignedBB bb = player.getEntityBoundingBox().expand(0.0625, 0.0, 0.0625).expand(-0.0625, 0.0, -0.0625);
            boolean found = false;
            for (double d = 1.0; d > 0.0; d -= 0.0625) {
                if (StepMod.mc.world.getCollisionBoxes((Entity)player, bb.offset(0.0, d, 0.0)).isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return;
            }
            double stepHeight = -1.0;
            final List<AxisAlignedBB> bbs = (List<AxisAlignedBB>)StepMod.mc.world.getCollisionBoxes((Entity)player, bb);
            for (final AxisAlignedBB box : bbs) {
                if (box.maxY > stepHeight) {
                    stepHeight = box.maxY;
                }
            }
            stepHeight -= player.posY;
            if (stepHeight < 0.0 || stepHeight > 1.0) {
                return;
            }
            StepMod.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.42 * stepHeight, player.posZ, player.onGround));
            StepMod.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.753 * stepHeight, player.posZ, player.onGround));
            player.setPosition(player.posX, player.posY + 1.0 * stepHeight, player.posZ);
        }
        else {
            StepMod.mc.player.stepHeight = (float)StepTranslator.INSTANCE.height;
        }
    }
    
    @Override
    public void init() {
        StepMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)StepTranslator.INSTANCE.height, "height", OptionCompletions.INTEGER, value -> {
                StepTranslator.INSTANCE.height = Math.max(0, value);
                return Boolean.valueOf(true);
            }, () -> StepTranslator.INSTANCE.height, "Height", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 1, 64, 1)), new ModuleOption((T)StepTranslator.INSTANCE.legit, "legit", OptionCompletions.BOOLEAN, value -> {
                StepTranslator.INSTANCE.legit = value;
                return Boolean.valueOf(true);
            }, () -> StepTranslator.INSTANCE.legit, "Legit") };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        if (StepTranslator.INSTANCE.legit) {
            return "Legit";
        }
        return String.valueOf(StepTranslator.INSTANCE.height);
    }
}
