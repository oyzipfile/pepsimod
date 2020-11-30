// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import net.minecraft.util.math.AxisAlignedBB;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class RotationUtils extends PepsiConstants
{
    private static boolean fakeRotation;
    private static float serverYaw;
    private static float serverPitch;
    
    public static Vec3d getEyesPos() {
        return new Vec3d(RotationUtils.mc.player.posX, RotationUtils.mc.player.posY + RotationUtils.mc.player.getEyeHeight(), RotationUtils.mc.player.posZ);
    }
    
    public static Vec3d getClientLookVec() {
        final float f = MathHelper.cos(-RotationUtils.mc.player.rotationYaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-RotationUtils.mc.player.rotationYaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-RotationUtils.mc.player.rotationPitch * 0.017453292f);
        final float f4 = MathHelper.sin(-RotationUtils.mc.player.rotationPitch * 0.017453292f);
        return new Vec3d((double)(f2 * f3), (double)(f4 + RotationUtils.mc.player.getEyeHeight()), (double)(f * f3));
    }
    
    public static Vec3d getServerLookVec() {
        final float f = MathHelper.cos(-RotationUtils.serverYaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-RotationUtils.serverYaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-RotationUtils.serverPitch * 0.017453292f);
        final float f4 = MathHelper.sin(-RotationUtils.serverPitch * 0.017453292f);
        return new Vec3d((double)(f2 * f3), (double)f4, (double)(f * f3));
    }
    
    private static float[] getNeededRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { RotationUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - RotationUtils.mc.player.rotationYaw), RotationUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - RotationUtils.mc.player.rotationPitch) };
    }
    
    public static float limitAngleChange(final float current, final float intended, final float maxChange) {
        float change = MathHelper.wrapDegrees(intended - current);
        change = MathHelper.clamp(change, -maxChange, maxChange);
        return MathHelper.wrapDegrees(current + change);
    }
    
    public static boolean faceVectorPacket(final Vec3d vec) {
        RotationUtils.fakeRotation = true;
        final float[] rotations = getNeededRotations(vec);
        RotationUtils.serverYaw = rotations[0];
        RotationUtils.serverPitch = (float)MathHelper.normalizeAngle((int)rotations[1], 360);
        return Math.abs(RotationUtils.serverYaw - rotations[0]) < 1.0f;
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        RotationUtils.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], (float)MathHelper.normalizeAngle((int)rotations[1], 360), RotationUtils.mc.player.onGround));
    }
    
    public static boolean faceVectorClient(final Vec3d vec) {
        final float[] rotations = getNeededRotations(vec);
        final float oldYaw = RotationUtils.mc.player.prevRotationYaw;
        final float oldPitch = RotationUtils.mc.player.prevRotationPitch;
        RotationUtils.mc.player.rotationYaw = rotations[0];
        RotationUtils.mc.player.rotationPitch = (float)MathHelper.normalizeAngle((int)rotations[1], 360);
        return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1.0f;
    }
    
    public static boolean faceEntityClient(final Entity entity) {
        final Vec3d eyesPos = getEyesPos();
        final Vec3d lookVec = getServerLookVec();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        return faceVectorClient(PepsiUtils.adjustVectorForBone(bb.getCenter(), entity, EntityUtils.DEFAULT_SETTINGS.getTargetBone())) || bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }
    
    public static boolean faceEntityPacket(final Entity entity) {
        final Vec3d eyesPos = getEyesPos();
        final Vec3d lookVec = getServerLookVec();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        return faceVectorPacket(PepsiUtils.adjustVectorForBone(bb.getCenter(), entity, EntityUtils.DEFAULT_SETTINGS.getTargetBone())) || bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }
    
    public static boolean faceVectorForWalking(final Vec3d vec) {
        final float[] rotations = getNeededRotations(vec);
        final float oldYaw = RotationUtils.mc.player.prevRotationYaw;
        RotationUtils.mc.player.rotationYaw = (float)MathHelper.normalizeAngle((int)rotations[0], 360);
        return Math.abs(oldYaw - rotations[0]) < 1.0f;
    }
    
    public static float getAngleToClientRotation(final Vec3d vec) {
        final float[] needed = getNeededRotations(vec);
        final float diffYaw = MathHelper.wrapDegrees(RotationUtils.mc.player.rotationYaw) - needed[0];
        final float diffPitch = MathHelper.wrapDegrees(RotationUtils.mc.player.rotationPitch) - needed[1];
        final float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }
    
    public static float getHorizontalAngleToClientRotation(final Vec3d vec) {
        final float[] needed = getNeededRotations(vec);
        final float angle = MathHelper.wrapDegrees(RotationUtils.mc.player.rotationYaw) - needed[0];
        return angle;
    }
    
    public static float getAngleToServerRotation(final Vec3d vec) {
        final float[] needed = getNeededRotations(vec);
        final float diffYaw = RotationUtils.serverYaw - needed[0];
        final float diffPitch = RotationUtils.serverPitch - needed[1];
        final float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }
    
    public static void updateServerRotation() {
        if (RotationUtils.fakeRotation) {
            RotationUtils.fakeRotation = false;
            return;
        }
        RotationUtils.serverYaw = limitAngleChange(RotationUtils.serverYaw, RotationUtils.mc.player.rotationYaw, 30.0f);
        RotationUtils.serverPitch = RotationUtils.mc.player.rotationPitch;
    }
    
    public static float getServerYaw() {
        return RotationUtils.serverYaw;
    }
    
    public static float getServerPitch() {
        return RotationUtils.serverPitch;
    }
}
