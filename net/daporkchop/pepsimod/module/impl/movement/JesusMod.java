// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.daporkchop.pepsimod.module.api.Module;

public class JesusMod extends Module
{
    public static JesusMod INSTANCE;
    private int tickTimer;
    private int packetTimer;
    
    public JesusMod() {
        super("Jesus");
        this.tickTimer = 10;
        this.packetTimer = 0;
        JesusMod.INSTANCE = this;
    }
    
    public boolean isOverLiquid() {
        if (JesusMod.mc.player == null) {
            return false;
        }
        final Entity entity = (Entity)(JesusMod.mc.player.isRiding() ? JesusMod.mc.player.getRidingEntity() : JesusMod.mc.player);
        boolean foundLiquid = false;
        boolean foundSolid = false;
        for (final AxisAlignedBB bb : JesusMod.mc.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().offset(0.0, -0.5, 0.0))) {
            final BlockPos pos = new BlockPos(bb.getCenter());
            final IBlockState state = JesusMod.mc.world.getBlockState(pos);
            final Material material = state.getBlock().getMaterial(state);
            if (material == Material.WATER || material == Material.LAVA) {
                foundLiquid = true;
            }
            else {
                if (material == Material.AIR) {
                    continue;
                }
                foundSolid = true;
            }
        }
        return foundLiquid && !foundSolid;
    }
    
    public boolean shouldBeSolid() {
        if (JesusMod.mc.player == null) {
            return false;
        }
        final Entity entity = (Entity)(JesusMod.mc.player.isRiding() ? JesusMod.mc.player.getRidingEntity() : JesusMod.mc.player);
        return this.state.enabled && entity.fallDistance <= 3.0f && !JesusMod.mc.gameSettings.keyBindSneak.isPressed() && !entity.isInWater();
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (JesusMod.mc.player == null) {
            return;
        }
        final Entity entity = (Entity)(JesusMod.mc.player.isRiding() ? JesusMod.mc.player.getRidingEntity() : JesusMod.mc.player);
        if (JesusMod.mc.gameSettings.keyBindSneak.isPressed()) {
            return;
        }
        if (entity.isInWater()) {
            entity.motionY = 0.11;
            this.tickTimer = 0;
            return;
        }
        if (this.tickTimer == 0) {
            entity.motionY = 0.3;
        }
        else if (this.tickTimer == 1) {
            entity.motionY = 0.0;
        }
        ++this.tickTimer;
    }
    
    @Override
    public boolean preSendPacket(final Packet<?> packet) {
        if (JesusMod.mc.player == null) {
            return false;
        }
        final Entity entity = (Entity)(JesusMod.mc.player.isRiding() ? JesusMod.mc.player.getRidingEntity() : JesusMod.mc.player);
        if (!JesusMod.mc.player.isRiding() && packet instanceof CPacketPlayer) {
            if (packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.PositionRotation) {
                if (!entity.isInWater()) {
                    if (entity.fallDistance <= 3.0f) {
                        if (this.isOverLiquid()) {
                            if (JesusMod.mc.player.movementInput == null) {
                                return true;
                            }
                            ++this.packetTimer;
                            if (this.packetTimer >= 4) {
                                final CPacketPlayer pck = (CPacketPlayer)packet;
                                double y = pck.getY(0.0);
                                if (entity.ticksExisted % 2 == 0) {
                                    y -= 0.05;
                                }
                                else {
                                    y += 0.05;
                                }
                                ReflectionStuff.setCPacketPlayer_y(pck, y);
                                ReflectionStuff.setcPacketPlayer_onGround(pck, true);
                            }
                        }
                    }
                }
            }
        }
        else if (JesusMod.mc.player.isRiding() && packet instanceof CPacketVehicleMove) {
            if (!entity.isInWater()) {
                if (entity.fallDistance <= 3.0f) {
                    if (this.isOverLiquid()) {
                        if (JesusMod.mc.player.movementInput == null) {
                            return true;
                        }
                        ++this.packetTimer;
                        if (this.packetTimer >= 4) {
                            final CPacketVehicleMove pck2 = (CPacketVehicleMove)packet;
                            double y = pck2.getY();
                            if (entity.ticksExisted % 2 == 0) {
                                y -= 0.05;
                            }
                            else {
                                y += 0.05;
                            }
                            ReflectionStuff.setcPacketVehicleMove_y(pck2, y);
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void init() {
        JesusMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
