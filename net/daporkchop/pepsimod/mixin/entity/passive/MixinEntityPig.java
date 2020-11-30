// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.entity.passive;

import net.daporkchop.pepsimod.util.config.impl.EntitySpeedTranslator;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.init.Items;
import net.daporkchop.pepsimod.module.impl.movement.EntitySpeedMod;
import net.minecraft.entity.player.EntityPlayer;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;
import net.daporkchop.pepsimod.optimization.SizeSettable;
import net.minecraft.entity.passive.EntityAnimal;

@Mixin({ EntityPig.class })
public abstract class MixinEntityPig extends EntityAnimal implements SizeSettable
{
    public int riddenTicks;
    
    public MixinEntityPig() {
        super((World)null);
        this.riddenTicks = 0;
    }
    
    @Shadow
    @Nullable
    public abstract Entity getControllingPassenger();
    
    @Overwrite
    public boolean canBeSteered() {
        final Entity entity = this.getControllingPassenger();
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer entityplayer = (EntityPlayer)entity;
        return (this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled) || entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
    }
    
    public void onLivingUpdate() {
        this.forceSetSize(0.9f, 0.9f);
        if (this.world.isRemote && this.isBeingRidden()) {
            if (this.riddenTicks++ >= 2) {
                if (this.riddenTicks > 1000) {
                    this.riddenTicks = 1000;
                }
                final AxisAlignedBB bb = EntitySpeedMod.getMergedBBs((Entity)this, this.getEntityBoundingBox());
                this.forceSetSize((float)Math.max(bb.maxX - bb.minX, bb.maxZ - bb.minZ), (float)(bb.maxY - bb.minY));
            }
        }
        else {
            this.riddenTicks = 0;
        }
        super.onLivingUpdate();
    }
    
    public double getMountedYOffset() {
        return 0.675;
    }
    
    @ModifyConstant(method = { "Lnet/minecraft/entity/passive/EntityPig;travel(FFF)V" }, constant = { @Constant(floatValue = 1.0f, ordinal = 0) })
    public float modifyStepHeight(final float orig) {
        return (this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled) ? EntitySpeedMod.INSTANCE.fakedStepHeight : orig;
    }
    
    @ModifyConstant(method = { "Lnet/minecraft/entity/passive/EntityPig;travel(FFF)V" }, constant = { @Constant(floatValue = 1.0f, ordinal = 1) })
    public float modifyIdleSpeed(final float orig) {
        return (this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled) ? EntitySpeedTranslator.INSTANCE.idleSpeed : orig;
    }
}
