// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import java.util.function.Consumer;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayDeque;
import com.google.common.collect.AbstractIterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class BlockUtils
{
    private static final Minecraft mc;
    
    public static boolean placeBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            if (WBlock.canBeClicked(neighbor)) {
                final Vec3d dirVec = new Vec3d(side.getDirectionVec());
                final Vec3d hitVec = posVec.add(dirVec.scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    if (distanceSqPosVec <= eyesPos.squareDistanceTo(posVec.add(dirVec))) {
                        if (BlockUtils.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                            RotationUtils.faceVectorPacketInstant(hitVec);
                            WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
                            WPlayer.swingArmClient();
                            ReflectionStuff.setRightClickDelayTimer(4);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (eyesPos.squareDistanceTo(new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5)) < eyesPos.squareDistanceTo(new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5))) {
                if (WBlock.canBeClicked(neighbor)) {
                    final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                    if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                        RotationUtils.faceVectorPacketInstant(hitVec);
                        WPlayerController.processRightClickBlock(neighbor, side2, hitVec);
                        WPlayer.swingArmClient();
                        ReflectionStuff.setRightClickDelayTimer(4);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean placeBlockSimple(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            if (WBlock.canBeClicked(neighbor)) {
                final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 36.0) {
                    WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean prepareToBreakBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 18.0625) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    if (BlockUtils.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                        return RotationUtils.faceVectorPacket(hitVec) || true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean breakBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 18.0625) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    if (BlockUtils.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                        if (!BlockUtils.mc.playerController.onPlayerDamageBlock(pos, side)) {
                            return false;
                        }
                        WPlayer.swingArmPacket();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean breakBlockExtraLegit(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 18.0625) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    if (BlockUtils.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                        if (!RotationUtils.faceVectorClient(hitVec)) {
                            return true;
                        }
                        if (BlockUtils.mc.gameSettings.keyBindAttack.isPressed() && !BlockUtils.mc.playerController.getIsHittingBlock()) {
                            ReflectionStuff.setPressed(BlockUtils.mc.gameSettings.keyBindAttack, false);
                            return true;
                        }
                        ReflectionStuff.setPressed(BlockUtils.mc.gameSettings.keyBindAttack, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean breakBlockSimple(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 36.0) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    RotationUtils.faceVectorPacket(hitVec);
                    if (!BlockUtils.mc.playerController.onPlayerDamageBlock(pos, side)) {
                        return false;
                    }
                    WPlayer.swingArmPacket();
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void breakBlockPacketSpam(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            if (eyesPos.squareDistanceTo(hitVec) < distanceSqPosVec) {
                BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                return;
            }
        }
    }
    
    public static boolean rightClickBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 18.0625) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    if (BlockUtils.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                        if (!RotationUtils.faceVectorPacket(hitVec)) {
                            return true;
                        }
                        WPlayerController.processRightClickBlock(pos, side, hitVec);
                        WPlayer.swingArmClient();
                        ReflectionStuff.setRightClickDelayTimer(4);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean rightClickBlockSimple(final BlockPos pos) {
        final Vec3d eyesPos = RotationUtils.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= 36.0) {
                if (distanceSqHitVec < distanceSqPosVec) {
                    WPlayerController.processRightClickBlock(pos, side, hitVec);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Iterable<BlockPos> getValidBlocksByDistance(final double range, final boolean ignoreVisibility, final BlockValidator validator) {
        final Vec3d eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        final double rangeSq = Math.pow(range + 0.5, 2.0);
        final BlockPos startPos = new BlockPos(RotationUtils.getEyesPos());
        return () -> new AbstractIterator<BlockPos>() {
            private ArrayDeque queue;
            private HashSet visited;
            final /* synthetic */ BlockPos val$startPos;
            final /* synthetic */ Vec3d val$eyesPos;
            final /* synthetic */ double val$rangeSq;
            final /* synthetic */ boolean val$ignoreVisibility;
            final /* synthetic */ BlockValidator val$validator;
            
            {
                this.queue = new ArrayDeque((Collection<? extends E>)Arrays.asList(this.val$startPos));
                this.visited = new HashSet();
            }
            
            protected BlockPos computeNext() {
                while (!this.queue.isEmpty()) {
                    final BlockPos current = this.queue.pop();
                    if (this.val$eyesPos.squareDistanceTo(new Vec3d((Vec3i)current)) > this.val$rangeSq) {
                        continue;
                    }
                    final boolean canBeClicked = WBlock.canBeClicked(current);
                    if (this.val$ignoreVisibility || !canBeClicked) {
                        for (final EnumFacing facing : EnumFacing.values()) {
                            final BlockPos next = current.offset(facing);
                            if (!this.visited.contains(next)) {
                                this.queue.add(next);
                                this.visited.add(next);
                            }
                        }
                    }
                    if (canBeClicked && this.val$validator.isValid(current)) {
                        return current;
                    }
                }
                return (BlockPos)this.endOfData();
            }
        };
    }
    
    public static Iterable<BlockPos> getValidBlocksByDistanceReversed(final double range, final boolean ignoreVisibility, final BlockValidator validator) {
        final ArrayDeque<BlockPos> validBlocks = new ArrayDeque<BlockPos>();
        getValidBlocksByDistance(range, ignoreVisibility, validator).forEach(validBlocks::push);
        return validBlocks;
    }
    
    public static Iterable<BlockPos> getValidBlocks(final double range, final BlockValidator validator) {
        final Vec3d eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        final double rangeSq = Math.pow(range + 0.5, 2.0);
        return getValidBlocks((int)Math.ceil(range), pos -> eyesPos.squareDistanceTo(new Vec3d((Vec3i)pos)) <= rangeSq && validator.isValid(pos));
    }
    
    public static Iterable<BlockPos> getValidBlocks(final int blockRange, final BlockValidator validator) {
        final BlockPos playerPos = new BlockPos(RotationUtils.getEyesPos());
        final BlockPos min = playerPos.add(-blockRange, -blockRange, -blockRange);
        final BlockPos max = playerPos.add(blockRange, blockRange, blockRange);
        return () -> new AbstractIterator<BlockPos>() {
            private BlockPos last;
            final /* synthetic */ BlockPos val$min;
            final /* synthetic */ BlockPos val$max;
            final /* synthetic */ BlockValidator val$validator;
            
            private BlockPos computeNextUnchecked() {
                if (this.last == null) {
                    return this.last = this.val$min;
                }
                int x = this.last.getX();
                int y = this.last.getY();
                int z = this.last.getZ();
                if (z < this.val$max.getZ()) {
                    ++z;
                }
                else if (x < this.val$max.getX()) {
                    z = this.val$min.getZ();
                    ++x;
                }
                else {
                    if (y >= this.val$max.getY()) {
                        return null;
                    }
                    z = this.val$min.getZ();
                    x = this.val$min.getX();
                    ++y;
                }
                return this.last = new BlockPos(x, y, z);
            }
            
            protected BlockPos computeNext() {
                BlockPos pos;
                while ((pos = this.computeNextUnchecked()) != null) {
                    if (WBlock.getMaterial(pos) == Material.AIR) {
                        continue;
                    }
                    if (!this.val$validator.isValid(pos)) {
                        continue;
                    }
                    return pos;
                }
                return (BlockPos)this.endOfData();
            }
        };
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public interface BlockValidator
    {
        boolean isValid(final BlockPos p0);
    }
}
