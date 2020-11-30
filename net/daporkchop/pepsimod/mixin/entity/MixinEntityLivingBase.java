// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.entity;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.daporkchop.pepsimod.util.config.impl.ElytraFlyTranslator;
import net.daporkchop.pepsimod.module.impl.movement.ElytraFlyMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.render.AntiBlindMod;
import net.minecraft.init.MobEffects;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends Entity
{
    @Shadow
    public float jumpMovementFactor;
    @Shadow
    public float prevLimbSwingAmount;
    @Shadow
    public float limbSwingAmount;
    @Shadow
    public float limbSwing;
    
    public MixinEntityLivingBase() {
        super((World)null);
    }
    
    @Inject(method = { "isPotionActive" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsPotionActive(final Potion potionIn, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (potionIn == MobEffects.BLINDNESS && AntiBlindMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "onLivingUpdate" }, at = { @At("HEAD") })
    public void preOnLivingUpdate(final CallbackInfo callbackInfo) {
        final EntityLivingBase thisAsEntity = EntityLivingBase.class.cast(this);
        if (thisAsEntity == PepsiConstants.mc.player && ElytraFlyMod.INSTANCE.state.enabled && ElytraFlyTranslator.INSTANCE.mode == ElytraFlyTranslator.ElytraFlyMode.PACKET) {
            this.motionY = 0.0;
        }
    }
    
    @Shadow
    public boolean isElytraFlying() {
        return false;
    }
    
    @Inject(method = { "travel" }, at = { @At("HEAD") })
    public void preTravel(final float x, final float y, final float z, final CallbackInfo callbackInfo) {
        final EntityLivingBase thisAsEntity = EntityLivingBase.class.cast(this);
        if (thisAsEntity == PepsiConstants.mc.player && ElytraFlyMod.INSTANCE.state.enabled && ElytraFlyTranslator.INSTANCE.mode == ElytraFlyTranslator.ElytraFlyMode.PACKET) {
            this.motionY = 0.0;
        }
    }
    
    @Shadow
    protected SoundEvent getFallSound(final int heightIn) {
        return null;
    }
    
    @Shadow
    public boolean isServerWorld() {
        return true;
    }
    
    @Shadow
    public float getAIMoveSpeed() {
        return 0.0f;
    }
    
    @Shadow
    public boolean isOnLadder() {
        return false;
    }
    
    @Shadow
    public boolean isPotionActive(final Potion potionIn) {
        return false;
    }
    
    @Shadow
    public PotionEffect getActivePotionEffect(final Potion potionIn) {
        return null;
    }
    
    @Shadow
    protected float getWaterSlowDown() {
        return 0.0f;
    }
}
