// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowText;
import net.daporkchop.pepsimod.util.PepsiUtils;
import java.awt.Color;
import net.daporkchop.pepsimod.Pepsimod;
import org.spongepowered.asm.mixin.Shadow;
import net.daporkchop.pepsimod.util.colors.ColorizedText;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiScreen;

@Mixin({ GuiMainMenu.class })
public abstract class MixinGuiMainMenu extends GuiScreen
{
    public final ColorizedText PEPSIMOD_TEXT_GRADIENT;
    public final ColorizedText PEPSIMOD_AUTHOR_GRADIENT;
    @Shadow
    private String splashText;
    
    public MixinGuiMainMenu() {
        this.PEPSIMOD_TEXT_GRADIENT = PepsiUtils.getGradientFromStringThroughColor(Pepsimod.NAME_VERSION + " for Minecraft " + "1.12.2", new Color(255, 0, 0), new Color(0, 0, 255), new Color(255, 255, 255));
        this.PEPSIMOD_AUTHOR_GRADIENT = new RainbowText("Made by DaPorkchop_");
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiMainMenu;<init>()V" }, at = { @At("RETURN") })
    public void postConstructor(final CallbackInfo callbackInfo) {
        this.splashText = PepsiConstants.pepsimod.data.mainMenu.getRandomSplash();
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiMainMenu;initGui()V" }, at = { @At("RETURN") })
    public void addPepsiIconAndChangeSplash(final CallbackInfo ci) {
        PepsiConstants.pepsimod.isInitialized = true;
        if (!PepsiConstants.pepsimod.hasInitializedModules) {
            for (final Module module : ModuleManager.AVALIBLE_MODULES) {
                module.doInit();
            }
            PepsiUtils.setBlockIdFields();
            PepsiConstants.pepsimod.hasInitializedModules = true;
        }
        ModuleManager.sortModules(GeneralTranslator.INSTANCE.sortType);
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;initGui()V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiMainMenu;splashText:Ljava/lang/String;", opcode = 181))
    public void preventSettingSplashInInitGui(final GuiMainMenu menu, final String val) {
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiMainMenu;splashText:Ljava/lang/String;", opcode = 181))
    public void preventSettingSplashInDrawScreen(final GuiMainMenu menu, final String val) {
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    public void removeMenuLogoInit(final TextureManager textureManager, final ResourceLocation resource) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        PepsiConstants.pepsimod.data.mainMenu.banner.render((float)(this.width / 2 - 150), 10.0f, 300.0f, 100.0f);
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawTexturedModalRect(IIIIII)V"))
    public void removeMenuLogoRendering(final GuiMainMenu guiMainMenu, final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawModalRectWithCustomSizedTexture(IIFFIIFF)V"))
    public void removeSubLogoRendering(final int x, final int y, final float a, final float b, final int c, final int d, final float e, final float f) {
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"))
    public void removeAllDrawStrings(final GuiMainMenu guiMainMenu, final FontRenderer fontRenderer1, final String string, final int i1, final int i2, final int i3) {
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiMainMenu;drawScreen(IIF)V" }, at = { @At("RETURN") })
    public void addDrawPepsiStuff(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        this.drawString(this.fontRenderer, "§cCopyright Mojang AB. Do not distribute!", this.width - this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!") - 2, this.height - 10, -1);
        this.PEPSIMOD_TEXT_GRADIENT.drawAtPos((Gui)this, 2, this.height - 20);
        this.PEPSIMOD_AUTHOR_GRADIENT.drawAtPos((Gui)this, 2, this.height - 10);
    }
}
