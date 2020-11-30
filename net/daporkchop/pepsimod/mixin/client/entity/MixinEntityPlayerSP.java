// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.entity;

import net.minecraft.client.gui.GuiScreen;
import net.daporkchop.pepsimod.module.impl.misc.HUDMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.impl.movement.StepMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.daporkchop.pepsimod.module.impl.movement.NoSlowdownMod;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.minecraft.entity.MoverType;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.daporkchop.pepsimod.util.event.MoveEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.entity.AbstractClientPlayer;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    protected Minecraft mc;
    public MoveEvent event;
    
    public MixinEntityPlayerSP() {
        super((World)null, (GameProfile)null);
        this.event = new MoveEvent();
    }
    
    @Overwrite
    public void move(final MoverType type, final double x, final double y, final double z) {
        this.event.x = x;
        this.event.y = y;
        this.event.z = z;
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.onPlayerMove(this.event);
        }
        super.move(type, this.event.x, this.event.y, this.event.z);
    }
    
    @Redirect(method = { "Lnet/minecraft/client/entity/EntityPlayerSP;onLivingUpdate()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isRiding()Z"))
    public boolean fixNoSlowdown(final Entity entity) {
        return entity.isRiding() && NoSlowdownMod.INSTANCE.state.enabled;
    }
    
    @Shadow
    protected boolean isCurrentViewEntity() {
        return false;
    }
    
    @Inject(method = { "isAutoJumpEnabled" }, at = { @At("HEAD") }, cancellable = true)
    public void preisAutoJumpEnabled(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (StepMod.INSTANCE.state.enabled) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/entity/EntityPlayerSP;setServerBrand(Ljava/lang/String;)V" }, at = { @At("HEAD") })
    public void setHUDBrand(final String brand, final CallbackInfo callbackInfo) {
        HUDMod.INSTANCE.serverBrand = brand;
    }
    
    @Redirect(method = { "Lnet/minecraft/client/entity/EntityPlayerSP;onLivingUpdate()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V", ordinal = 0))
    public void fixPortalGUIs_1(final EntityPlayerSP player) {
    }
    
    @Redirect(method = { "Lnet/minecraft/client/entity/EntityPlayerSP;onLivingUpdate()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", ordinal = 0))
    public void fixPortalGUIs_2(final Minecraft mc, final GuiScreen screen) {
    }
}
