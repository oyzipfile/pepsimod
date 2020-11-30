// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.gui;

import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.minecraft.world.BossInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.BossInfoClient;
import java.util.UUID;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.daporkchop.pepsimod.util.BossinfoCounted;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiBossOverlay.class })
public abstract class MixinGuiBossOverlay extends Gui
{
    private final ArrayList<BossinfoCounted> counted_cache;
    public ResourceLocation GUI_BARS_TEXTURES_ALT;
    @Shadow
    @Final
    private Map<UUID, BossInfoClient> mapBossInfos;
    @Shadow
    @Final
    private Minecraft client;
    
    public MixinGuiBossOverlay() {
        this.counted_cache = new ArrayList<BossinfoCounted>();
        this.GUI_BARS_TEXTURES_ALT = new ResourceLocation("textures/gui/bars.png");
    }
    
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderBossHealth(final CallbackInfo callbackInfo) {
        if (!this.mapBossInfos.isEmpty()) {
            final ScaledResolution scaledresolution = new ScaledResolution(this.client);
            final int i = scaledresolution.getScaledWidth();
            int j = 12;
            for (final BossinfoCounted counted : this.counted_cache) {
                final int k = i / 2 - 91;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.client.getTextureManager().bindTexture(this.GUI_BARS_TEXTURES_ALT);
                this.render(k, j, (BossInfo)counted.info);
                final String s = counted.info.getName().getFormattedText() + ((counted.count > 1) ? (" (x" + counted.count + ')') : "");
                this.client.fontRenderer.drawStringWithShadow(s, (float)(i / 2 - this.client.fontRenderer.getStringWidth(s) / 2), (float)(j - 9), 16777215);
                j += 10 + this.client.fontRenderer.FONT_HEIGHT;
                if (j >= scaledresolution.getScaledHeight() / 3) {
                    break;
                }
            }
        }
        callbackInfo.cancel();
    }
    
    @Shadow
    private void render(final int x, final int y, final BossInfo info) {
    }
    
    @Inject(method = { "read" }, at = { @At("HEAD") })
    public void read(final SPacketUpdateBossInfo packetIn, final CallbackInfo callbackInfo) {
        this.updateCounter();
    }
    
    public void updateCounter() {
        this.counted_cache.clear();
        final ArrayList<String> known = new ArrayList<String>();
        for (final BossInfoClient infoLerping : this.mapBossInfos.values()) {
            if (known.contains(infoLerping.getName().getFormattedText())) {
                continue;
            }
            final String formattedText = infoLerping.getName().getFormattedText();
            final BossinfoCounted counted = new BossinfoCounted();
            counted.info = infoLerping;
            for (final BossInfoClient infoLerping2 : this.mapBossInfos.values()) {
                if (infoLerping2.getName().getFormattedText().equals(formattedText)) {
                    final BossinfoCounted bossinfoCounted = counted;
                    ++bossinfoCounted.count;
                }
            }
            known.add(formattedText);
            this.counted_cache.add(counted);
        }
    }
}
