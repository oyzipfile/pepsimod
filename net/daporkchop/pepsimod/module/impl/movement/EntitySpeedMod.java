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
import net.minecraft.util.MovementInput;
import net.daporkchop.pepsimod.util.config.impl.EntitySpeedTranslator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.passive.EntityPig;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.module.api.Module;

public class EntitySpeedMod extends Module
{
    public static EntitySpeedMod INSTANCE;
    public float fakedStepHeight;
    protected int stepDelay;
    
    public static AxisAlignedBB getMergedBBs(final Entity entity, AxisAlignedBB bb) {
        if (entity.world.isRemote) {
            for (final Entity passenger : entity.getPassengers()) {
                final AxisAlignedBB bb2 = passenger.getEntityBoundingBox();
                ReflectionStuff.setMaxY(bb2, passenger.getPositionEyes(0.0f).y);
                bb = bb.union(bb2);
            }
        }
        return bb;
    }
    
    public EntitySpeedMod() {
        super("EntitySpeed");
        EntitySpeedMod.INSTANCE = this;
        this.fakedStepHeight = 0.5f;
        this.stepDelay = 0;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        this.fakedStepHeight = 0.5f;
    }
    
    @Override
    public void tick() {
        final Entity ridingEntity = ReflectionStuff.getRidingEntity((Entity)EntitySpeedMod.mc.player);
        if (ridingEntity != null) {
            if (ridingEntity instanceof EntityPig) {
                if (this.stepDelay > 0) {
                    --this.stepDelay;
                    return;
                }
                final EntityPig pig = (EntityPig)ridingEntity;
                if (EntitySpeedMod.mc.player.movementInput.moveForward == 0.0f && EntitySpeedMod.mc.player.movementInput.moveStrafe == 0.0f) {
                    final EntityPig entityPig = pig;
                    final float n = 1.0f;
                    this.fakedStepHeight = n;
                    entityPig.stepHeight = n;
                }
                else {
                    final EntityPig entityPig2 = pig;
                    final float n2 = 0.5f;
                    this.fakedStepHeight = n2;
                    entityPig2.stepHeight = n2;
                    if (pig.collidedHorizontally) {
                        if (pig.onGround && !pig.isOnLadder() && !pig.isInWater()) {
                            if (!pig.isInLava()) {
                                if (!EntitySpeedMod.mc.player.movementInput.jump) {
                                    double stepHeight = -1.0;
                                    Vec3d stepDir = null;
                                    boolean found = false;
                                    final Vec3d[] dirs = { new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0) };
                                Label_0400:
                                    for (double d = 1.0; d > 0.0; d -= 0.0625) {
                                        for (final Vec3d dir : dirs) {
                                            final AxisAlignedBB bb = pig.getEntityBoundingBox().offset(dir.scale(0.0625));
                                            if (EntitySpeedMod.mc.world.getCollisionBoxes((Entity)pig, bb.offset(0.0, d, 0.0)).isEmpty()) {
                                                found = true;
                                                stepDir = dir;
                                                for (final AxisAlignedBB box : EntitySpeedMod.mc.world.getCollisionBoxes((Entity)pig, bb)) {
                                                    if (box.maxY > stepHeight) {
                                                        stepHeight = box.maxY;
                                                    }
                                                }
                                                break Label_0400;
                                            }
                                        }
                                    }
                                    if (found) {
                                        stepHeight -= pig.posY;
                                        if (stepHeight >= 0.0) {
                                            if (stepHeight <= 1.0) {
                                                final double yOrig = pig.posY;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.posY = yOrig + 0.24 * stepHeight;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.posY = yOrig + 0.48 * stepHeight;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.posY = yOrig + 0.72 * stepHeight;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.posY = yOrig + 0.96 * stepHeight;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.posY = yOrig + stepHeight;
                                                EntitySpeedMod.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove((Entity)pig));
                                                pig.setPosition(pig.posX + stepDir.x * 0.0625, yOrig + stepHeight, pig.posZ + stepDir.z * 0.0625);
                                                this.stepDelay = 5;
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            final MovementInput movementInput = EntitySpeedMod.mc.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = EntitySpeedMod.mc.player.rotationYaw;
            if (forward == 0.0 && strafe == 0.0) {
                ridingEntity.motionX = 0.0;
                ridingEntity.motionZ = 0.0;
            }
            else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += ((forward > 0.0) ? -45 : 45);
                    }
                    else if (strafe < 0.0) {
                        yaw += ((forward > 0.0) ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    }
                    else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                ridingEntity.motionX = forward * EntitySpeedTranslator.INSTANCE.speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * EntitySpeedTranslator.INSTANCE.speed * Math.sin(Math.toRadians(yaw + 90.0f));
                ridingEntity.motionZ = forward * EntitySpeedTranslator.INSTANCE.speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * EntitySpeedTranslator.INSTANCE.speed * Math.cos(Math.toRadians(yaw + 90.0f));
            }
        }
    }
    
    @Override
    public void init() {
        EntitySpeedMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)EntitySpeedTranslator.INSTANCE.speed, "speed", OptionCompletions.FLOAT, value -> {
                EntitySpeedTranslator.INSTANCE.speed = Math.max(0.0f, value);
                return Boolean.valueOf(true);
            }, () -> EntitySpeedTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 4.0f, 0.1f)), new ModuleOption((T)EntitySpeedTranslator.INSTANCE.idleSpeed, "idleSpeed", OptionCompletions.FLOAT, value -> {
                EntitySpeedTranslator.INSTANCE.idleSpeed = Math.max(0.0f, value);
                return Boolean.valueOf(true);
            }, () -> EntitySpeedTranslator.INSTANCE.idleSpeed, "Idle Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 2.0f, 0.1f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
