// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors.rainbow;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.client.gui.Gui;
import net.daporkchop.pepsimod.util.colors.FixedColorElement;
import net.daporkchop.pepsimod.util.colors.PlainColorElement;
import net.minecraft.client.gui.FontRenderer;
import net.daporkchop.pepsimod.util.colors.ColorizedElement;
import net.daporkchop.pepsimod.util.colors.ColorizedText;

public class RainbowText extends ColorizedText
{
    private final ColorizedElement[] elements;
    private final int width;
    private final FontRenderer fontRenderer;
    public String text;
    private int offset;
    
    public RainbowText(final String text) {
        this(text, 0);
    }
    
    public RainbowText(final String text, final int offset) {
        this.fontRenderer = RainbowText.mc.fontRenderer;
        this.offset = offset;
        final String[] split = text.split("§custom");
        final String[] split2 = split[0].split("");
        this.elements = new ColorizedElement[split2.length + ((split.length > 1) ? 1 : 0)];
        for (int i = 0; i < split2.length; ++i) {
            this.elements[i] = new PlainColorElement(split2[i]);
        }
        if (split.length > 1) {
            this.elements[this.elements.length - 1] = new FixedColorElement(Integer.parseInt(split[1].substring(0, Math.min(split[1].length(), 6)), 16), split[1].substring(6));
            int i = 0;
            for (final ColorizedElement element : this.elements) {
                i += element.width;
            }
            this.width = i;
            this.text = split[0] + split[1].substring(6);
        }
        else {
            this.width = this.fontRenderer.getStringWidth(text);
        }
        this.text = text;
    }
    
    @Override
    public void drawAtPos(final Gui screen, final int x, final int y) {
        int i = 0;
        RainbowCycle cycle = PepsiUtils.rainbowCycle(this.offset, PepsiUtils.rainbowCycle.clone());
        for (final ColorizedElement element : this.elements) {
            if (element instanceof FixedColorElement) {
                screen.drawString(Minecraft.getMinecraft().fontRenderer, element.text, x + i, y, ((FixedColorElement)element).color);
                return;
            }
            cycle = PepsiUtils.rainbowCycle(1, cycle);
            final Color color = new Color(PepsiUtils.ensureRange(cycle.r, 0, 255), PepsiUtils.ensureRange(cycle.g, 0, 255), PepsiUtils.ensureRange(cycle.b, 0, 255));
            screen.drawString(Minecraft.getMinecraft().fontRenderer, element.text, x + i, y, color.getRGB());
            i += element.width;
        }
    }
    
    public void drawAtPos(final Gui screen, final int x, final int y, final int offset) {
        final int tempOffset = this.offset;
        this.offset = offset;
        this.drawAtPos(screen, x, y);
        this.offset = tempOffset;
    }
    
    @Override
    public int width() {
        return this.width;
    }
    
    @Override
    public String getRawText() {
        return this.text;
    }
}
