// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.entity;

import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.daporkchop.pepsimod.util.config.impl.ESPTranslator;
import net.daporkchop.pepsimod.module.impl.render.ESPMod;
import net.daporkchop.pepsimod.module.impl.render.AntiInvisibleMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.player.EntityPlayer;
import net.daporkchop.pepsimod.module.impl.movement.NoClipMod;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.movement.VelocityMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    private AxisAlignedBB boundingBox;
    
    @Shadow
    public abstract void resetPositionToBB();
    
    @Inject(method = { "setVelocity" }, at = { @At("HEAD") }, cancellable = true)
    public void preSetVelocity(final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        float strength = 1.0f;
        if (Entity.class.cast(this) == PepsiConstants.mc.player) {
            strength = VelocityMod.INSTANCE.getVelocity();
        }
        this.motionX = x * strength;
        this.motionY = y * strength;
        this.motionZ = z * strength;
        callbackInfo.cancel();
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    public void preMove(final MoverType type, final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        final Entity thisAsEntity = Entity.class.cast(this);
        if ((FreecamMod.INSTANCE.state.enabled || NoClipMod.INSTANCE.state.enabled) && thisAsEntity instanceof EntityPlayer) {
            this.boundingBox = this.boundingBox.offset(x, y, z);
            this.resetPositionToBB();
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "isInvisibleToPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsInvisibleToPlayer(final EntityPlayer player, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (AntiInvisibleMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "isGlowing" }, at = { @At("HEAD") }, cancellable = true)
    public void preisGlowing(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ESPMod.INSTANCE.state.enabled && !ESPTranslator.INSTANCE.box) {
            final Entity this_ = Entity.class.cast(this);
            if (this_.isInvisible() && !ESPTranslator.INSTANCE.invisible) {
                return;
            }
            if (ESPTranslator.INSTANCE.animals && this_ instanceof EntityAnimal) {
                callbackInfoReturnable.setReturnValue(true);
            }
            else if (ESPTranslator.INSTANCE.monsters && this_ instanceof EntityMob) {
                callbackInfoReturnable.setReturnValue(true);
            }
            else if (ESPTranslator.INSTANCE.players && this_ instanceof EntityPlayer) {
                callbackInfoReturnable.setReturnValue(true);
            }
            else if (ESPTranslator.INSTANCE.golems && this_ instanceof EntityGolem) {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = { "isInWater" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsInWater(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (FreecamMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
