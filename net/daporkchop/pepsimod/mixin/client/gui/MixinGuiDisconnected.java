// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.client.multiplayer.ServerData;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.minecraft.client.gui.GuiButton;
import net.daporkchop.pepsimod.util.PepsiUtils;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.impl.render.ZoomMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiScreen;

@Mixin({ GuiDisconnected.class })
public abstract class MixinGuiDisconnected extends GuiScreen
{
    @Shadow
    @Final
    private GuiScreen parentScreen;
    @Shadow
    @Final
    private String reason;
    @Shadow
    @Final
    private ITextComponent message;
    @Shadow
    private int textHeight;
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    public void preDrawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo callbackInfo) {
        if (ZoomMod.INSTANCE.state.enabled) {
            ModuleManager.disableModule(ZoomMod.INSTANCE);
            this.mc.gameSettings.fovSetting = ZoomMod.INSTANCE.fov;
        }
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void postInitGui(final CallbackInfo callbackInfo) {
        PepsiUtils.autoReconnectWaitTime = 5;
        this.buttonList.add(PepsiUtils.reconnectButton = new GuiButton(1, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 22, this.height - 30 + 22), "Reconnect"));
        this.buttonList.add(PepsiUtils.autoReconnectButton = new GuiButton(2, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 44, this.height - 30 + 44), "AutoReconnect"));
        if (!GeneralTranslator.INSTANCE.autoReconnect) {
            PepsiUtils.autoReconnectButton.displayString = "AutoReconnect (§cDisabled§r)";
        }
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    public void preActionPerformed(final GuiButton button, final CallbackInfo callbackInfo) {
        if (button.id == 1) {
            final ServerData data = new ServerData("", PepsiUtils.lastIp + ':' + PepsiUtils.lastPort, false);
            data.setResourceMode(ServerData.ServerResourceMode.PROMPT);
            FMLClientHandler.instance().connectToServer(this.mc.currentScreen, data);
            callbackInfo.cancel();
        }
        else if (button.id == 2) {
            GeneralTranslator.INSTANCE.autoReconnect = !GeneralTranslator.INSTANCE.autoReconnect;
            if (GeneralTranslator.INSTANCE.autoReconnect) {
                PepsiUtils.autoReconnectWaitTime = 5;
                PepsiUtils.autoReconnectButton.displayString = "AutoReconnect (§a" + PepsiUtils.autoReconnectWaitTime + "§r)";
            }
            else {
                PepsiUtils.autoReconnectButton.displayString = "AutoReconnect (§cDisabled§r)";
            }
            callbackInfo.cancel();
        }
    }
}
