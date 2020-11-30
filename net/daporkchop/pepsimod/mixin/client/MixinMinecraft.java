// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;
import net.daporkchop.pepsimod.util.config.impl.CpuLimitTranslator;
import net.daporkchop.pepsimod.module.impl.render.UnfocusedCPUMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.Display;
import net.daporkchop.pepsimod.PepsimodMixinLoader;
import net.daporkchop.pepsimod.Pepsimod;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.impl.render.ZoomMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft
{
    @Shadow
    public EntityPlayerSP player;
    
    @Shadow
    private ByteBuffer readImageToBuffer(final InputStream imageStream) throws IOException {
        return null;
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;shutdown()V" }, at = { @At("HEAD") })
    public void saveSettingsOnShutdown(final CallbackInfo ci) {
        System.out.println("[PEPSIMOD] Saving config...");
        PepsiConstants.pepsimod.saveConfig();
        System.out.println("[PEPSIMOD] Saved.");
        if (ZoomMod.INSTANCE.state.enabled) {
            ModuleManager.disableModule(ZoomMod.INSTANCE);
            PepsiConstants.mc.gameSettings.fovSetting = ZoomMod.INSTANCE.fov;
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;runGameLoop()V" }, at = { @At("RETURN") })
    public void postOnClientPreTick(final CallbackInfo callbackInfo) {
        if (PepsiConstants.mc.player != null && PepsiConstants.mc.player.movementInput != null) {
            for (final Module module : ModuleManager.AVALIBLE_MODULES) {
                if (module.shouldTick()) {
                    module.tick();
                }
            }
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;runTickMouse()V" }, at = { @At(value = "INVOKE_ASSIGN", target = "Lorg/lwjgl/input/Mouse;getEventButton()I") })
    public void onMouseClick(final CallbackInfo ci) {
        try {
            if (Mouse.getEventButtonState()) {
                final int buttonID = Mouse.getEventButton();
                switch (buttonID) {
                    case 2: {
                        if (Minecraft.getMinecraft().objectMouseOver != null) {
                            final RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
                            if (result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer) {
                                if (FriendsTranslator.INSTANCE.isFriend(result.entityHit)) {
                                    this.player.sendMessage((ITextComponent)new TextComponentString("§0§l[§c§lpepsi§9§lmod§0§l]§rRemoved §c" + result.entityHit.getName() + "§r as a friend"));
                                    FriendsTranslator.INSTANCE.friends.remove(result.entityHit.getUniqueID());
                                }
                                else {
                                    this.player.sendMessage((ITextComponent)new TextComponentString("§0§l[§c§lpepsi§9§lmod§0§l]§rAdded §9" + result.entityHit.getName() + "§r as a friend"));
                                    FriendsTranslator.INSTANCE.friends.add(result.entityHit.getUniqueID());
                                }
                            }
                            FMLLog.log.info(result.entityHit.getClass().getCanonicalName());
                            break;
                        }
                        break;
                    }
                }
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @Redirect(method = { "Lnet/minecraft/client/Minecraft;createDisplay()V" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void changeWindowTitle(final String title) {
        Display.setTitle(Pepsimod.NAME_VERSION + (PepsimodMixinLoader.isObfuscatedEnvironment ? "" : " (dev environment)"));
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;setWindowIcon()V" }, at = { @At("HEAD") }, cancellable = true)
    public void preSetWindowIcon(final CallbackInfo callbackInfo) {
        try (final InputStream in = Pepsimod.class.getResourceAsStream("/pepsilogo.png")) {
            Display.setIcon(new ByteBuffer[] { this.readImageToBuffer(in) });
            callbackInfo.cancel();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    public void preDisplayGuiScreen(final GuiScreen guiScreen, final CallbackInfo callbackInfo) {
        if (ZoomMod.INSTANCE.state.enabled) {
            ModuleManager.disableModule(ZoomMod.INSTANCE);
            PepsiConstants.mc.gameSettings.fovSetting = ZoomMod.INSTANCE.fov;
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;getLimitFramerate()I" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetLimitFramerate(final CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if (UnfocusedCPUMod.INSTANCE.state.enabled && !Display.isActive()) {
                callbackInfoReturnable.setReturnValue(CpuLimitTranslator.INSTANCE.limit);
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @Inject(method = { "Lnet/minecraft/client/Minecraft;processKeyBinds()V" }, at = { @At("HEAD") })
    public void preProcessKeyBinds(final CallbackInfo ci) {
        if (PepsiConstants.mc.currentScreen == null && Keyboard.getEventCharacter() == '.') {
            PepsiConstants.mc.displayGuiScreen((GuiScreen)new GuiChat("."));
        }
    }
    
    @Redirect(method = { "Lnet/minecraft/client/Minecraft;clickMouse()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    public void preventAttackingRiddenEntity(final PlayerControllerMP controller, final EntityPlayer attacker, final Entity attacked) {
        if (!attacked.isPassenger((Entity)attacker)) {
            controller.attackEntity(attacker, attacked);
        }
    }
}
