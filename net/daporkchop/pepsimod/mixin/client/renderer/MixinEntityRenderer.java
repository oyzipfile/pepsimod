// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.renderer;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.module.impl.render.FullbrightMod;
import net.minecraft.client.settings.GameSettings;
import net.daporkchop.pepsimod.module.impl.render.AntiTotemAnimationMod;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RotationUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.daporkchop.pepsimod.module.impl.render.NoOverlayMod;
import net.daporkchop.pepsimod.module.impl.render.NoHurtCamMod;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.init.MobEffects;
import net.daporkchop.pepsimod.module.impl.render.AntiBlindMod;
import org.lwjgl.util.glu.Project;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.misc.IWurstRenderListener;
import java.util.function.Consumer;
import net.daporkchop.pepsimod.util.PepsiUtils;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    private float farPlaneDistance;
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private double cameraZoom;
    @Shadow
    private double cameraYaw;
    @Shadow
    private double cameraPitch;
    @Shadow
    private int rendererUpdateCount;
    @Shadow
    private int debugViewDirection;
    @Shadow
    private boolean debugView;
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 19) })
    public void preRenderHand(final CallbackInfo ci, final int pass, final float partialTicks, final long finishTimeNano) {
        PepsiUtils.toRemoveWurstRenderListeners.forEach(PepsiUtils.wurstRenderListeners::remove);
        PepsiUtils.toRemoveWurstRenderListeners.clear();
        PepsiUtils.wurstRenderListeners.forEach(listener -> listener.render(partialTicks));
    }
    
    @Overwrite
    public void setupCameraTransform(final float partialTicks, final int pass) {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        final float f = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(pass * 2 - 1) * 0.07f, 0.0f, 0.0f);
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.farPlaneDistance * MathHelper.SQRT_2);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(partialTicks);
        if (this.mc.gameSettings.viewBobbing) {
            this.applyBobbing(partialTicks);
        }
        final float f2 = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * partialTicks;
        if (f2 > 0.0f && !AntiBlindMod.INSTANCE.state.enabled) {
            int i = 20;
            if (this.mc.player.isPotionActive(MobEffects.NAUSEA)) {
                i = 7;
            }
            float f3 = 5.0f / (f2 * f2 + 5.0f) - f2 * 0.04f;
            f3 *= f3;
            GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * i, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / f3, 1.0f, 1.0f);
            GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * i, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(partialTicks);
        if (this.debugView) {
            switch (this.debugViewDirection) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void preHurtCameraEffect(final float partialTicks, final CallbackInfo callbackInfo) {
        if (NoHurtCamMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;setFogDensity(F)V"))
    public void changeFog(final float density) {
        if (NoOverlayMod.INSTANCE.state.enabled) {
            GlStateManager.setFogDensity(0.01f);
        }
        else {
            GlStateManager.setFogDensity(density);
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE) })
    public void renderLines(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        if (this.mc.gameSettings.viewBobbing) {
            this.mc.gameSettings.viewBobbing = false;
            GL11.glPushMatrix();
            this.setupCameraTransform(partialTicks, pass);
            try (final WorldRenderer renderer = new WorldRenderer(RotationUtils.getClientLookVec(), PepsiUtils.getPlayerPos(partialTicks), partialTicks)) {
                for (final Module module : ModuleManager.ENABLED_MODULES) {
                    module.renderOverlay(renderer);
                }
            }
            GL11.glPopMatrix();
            this.mc.gameSettings.viewBobbing = true;
            GL11.glPushMatrix();
            this.setupCameraTransform(partialTicks, pass);
            try (final WorldRenderer renderer = new WorldRenderer(RotationUtils.getClientLookVec(), PepsiUtils.getPlayerPos(partialTicks), partialTicks)) {
                for (final Module module : ModuleManager.ENABLED_MODULES) {
                    module.renderWorld(renderer);
                }
            }
            GL11.glPopMatrix();
        }
        else {
            try (final WorldRenderer renderer = new WorldRenderer(RotationUtils.getClientLookVec(), PepsiUtils.getPlayerPos(partialTicks), partialTicks)) {
                for (final Module module : ModuleManager.ENABLED_MODULES) {
                    module.renderOverlay(renderer);
                    module.renderWorld(renderer);
                }
            }
        }
    }
    
    @Inject(method = { "displayItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void preDisplayItemActivation(final ItemStack stack, final CallbackInfo callbackInfo) {
        if (AntiTotemAnimationMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderItemActivation(final int a, final int b, final float c, final CallbackInfo callbackInfo) {
        if (AntiTotemAnimationMod.INSTANCE.state.enabled) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "Lnet/minecraft/client/renderer/EntityRenderer;updateLightmap(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;gammaSetting:F"))
    public float redirectGammaSetting(final GameSettings settings) {
        return Math.max(FullbrightMod.INSTANCE.level * 0.5f, settings.gammaSetting);
    }
    
    @Redirect(method = { "Lnet/minecraft/client/renderer/EntityRenderer;getMouseOver(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEntityBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;", ordinal = 1))
    public AxisAlignedBB preventMousingOverRiddenEntity(final Entity possiblyRidden) {
        if (possiblyRidden.isPassenger(this.mc.getRenderViewEntity())) {
            return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        return possiblyRidden.getEntityBoundingBox();
    }
    
    @Shadow
    private float getFOVModifier(final float partialTicks, final boolean useFOVSetting) {
        return 0.0f;
    }
    
    @Shadow
    private void hurtCameraEffect(final float partialTicks) {
    }
    
    @Shadow
    private void applyBobbing(final float partialTicks) {
    }
    
    @Shadow
    private void orientCamera(final float partialTicks) {
    }
}
