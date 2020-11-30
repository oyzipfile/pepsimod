// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.daporkchop.pepsimod.gui.mcleaks.GuiScreenMCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.gui.misc.GuiButtonTooBeeTooTee;
import net.daporkchop.pepsimod.gui.mcleaks.GuiButtonMCLeaks;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiMultiplayer;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiScreen;

@Mixin({ GuiMultiplayer.class })
public abstract class MixinGuiMultiplayer extends GuiScreen
{
    @Inject(method = { "createButtons" }, at = { @At("RETURN") })
    public void createButtons(final CallbackInfo ci) {
        this.buttonList.add(new GuiButtonMCLeaks(9, 6, 6, 20, 20));
        this.buttonList.add(new GuiButtonTooBeeTooTee(10, this.width - 26, 6, 20, 20));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    public void actionPerformed(final GuiButton button, final CallbackInfo ci) {
        if (button.id == 9) {
            final GuiScreenMCLeaks mcLeaks = new GuiScreenMCLeaks(this, Minecraft.getMinecraft());
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)mcLeaks);
            ci.cancel();
        }
        else if (button.id == 10) {
            FMLClientHandler.instance().connectToServer((GuiScreen)new GuiMainMenu(), PepsiUtils.TOOBEETOOTEE_DATA);
            ci.cancel();
        }
    }
}
