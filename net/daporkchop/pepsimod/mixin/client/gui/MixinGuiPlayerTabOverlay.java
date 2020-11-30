// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.daporkchop.pepsimod.util.render.Texture;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.misc.data.Group;
import net.daporkchop.pepsimod.util.PepsiConstants;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.List;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiPlayerTabOverlay.class })
public abstract class MixinGuiPlayerTabOverlay extends Gui
{
    @Shadow
    @Final
    private Minecraft mc;
    
    @ModifyConstant(method = { "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;renderPlayerlist(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V" }, constant = { @Constant(intValue = 9, ordinal = 0) })
    public int changePlayerBoxWidthIncrease(final int old) {
        return old + 9;
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;renderPlayerlist(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawRect(IIIII)V", ordinal = 2) }, locals = LocalCapture.CAPTURE_FAILHARD)
    public void drawPlayerBoxBackgroundCustom(final int width, final Scoreboard scoreboardIn, final ScoreObjective scoreObjectiveIn, final CallbackInfo ci, final NetHandlerPlayClient nethandlerplayclient, final List<NetworkPlayerInfo> list, final int i, final int j, final int l3, final int i4, final int j4, final boolean flag, final int l, final int i1, final int j1, final int k1, final int l1, final List<String> list1, final List<String> list2, final int k4, final int l4, final int i5, final int j2, final int k2) {
        int color = 553648126;
        if (k4 < list.size()) {
            final Group group = PepsiConstants.pepsimod.data.getGroup(list.get(k4));
            if (group != null && group.color != 0) {
                color = (group.color | Integer.MIN_VALUE);
            }
        }
        drawRect(j2, k2, j2 + i1, k2 + 8, color);
    }
    
    @ModifyConstant(method = { "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;renderPlayerlist(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V" }, constant = { @Constant(intValue = 553648127) })
    public int changePlayerBoxBackgroundColor(final int old) {
        return 0;
    }
    
    @Redirect(method = { "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawPing(IIILnet/minecraft/client/network/NetworkPlayerInfo;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    public void preventExtraPingTextureBind(final TextureManager manager, final ResourceLocation location) {
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawPing(IIILnet/minecraft/client/network/NetworkPlayerInfo;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawTexturedModalRect(IIIIII)V") })
    public void drawIconIfPossible(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_, final NetworkPlayerInfo info, final CallbackInfo callbackInfo) {
        final Group group = PepsiConstants.pepsimod.data.getGroup(info);
        if (group != null) {
            group.doWithIconIfPresent(tex -> tex.render((float)(p_175245_2_ + p_175245_1_ - 11 - 9), (float)p_175245_3_, 8.0f, 8.0f));
        }
        this.mc.getTextureManager().bindTexture(MixinGuiPlayerTabOverlay.ICONS);
    }
}
