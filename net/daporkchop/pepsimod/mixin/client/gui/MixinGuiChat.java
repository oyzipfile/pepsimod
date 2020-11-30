// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.command.CommandRegistry;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiChat;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiScreen;

@Mixin({ GuiChat.class })
public abstract class MixinGuiChat extends GuiScreen
{
    public String prevText;
    public String prevSuggestion;
    @Shadow
    protected GuiTextField inputField;
    
    public MixinGuiChat() {
        this.prevText = "";
        this.prevSuggestion = "";
    }
    
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;drawTextBox()V") })
    public void drawSemiTransparentText(final CallbackInfo ci) {
        if (this.inputField.getText().startsWith(".")) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            final int x = this.inputField.x;
            final int y = this.inputField.y;
            if (!this.prevText.equals(this.inputField.getText())) {
                this.prevText = this.inputField.getText();
                this.prevSuggestion = CommandRegistry.getSuggestionFor(this.prevText);
            }
            this.fontRenderer.drawString(this.prevSuggestion, (float)x, (float)y, 1610612735, false);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
    
    @Inject(method = { "keyTyped" }, at = { @At("HEAD") }, cancellable = true)
    public void checkIfIsCommandAndProcess(final char typedChar, final int keyCode, final CallbackInfo ci) {
        if ((keyCode == 28 || keyCode == 156) && this.inputField.getText().startsWith(".")) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(this.inputField.getText());
            this.mc.displayGuiScreen((GuiScreen)null);
            CommandRegistry.runCommand(this.inputField.getText());
            ci.cancel();
        }
    }
}
