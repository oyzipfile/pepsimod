// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import net.daporkchop.lib.unsafe.PCleaner;
import net.minecraft.util.ResourceLocation;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class Texture extends PepsiConstants implements AutoCloseable
{
    public final ResourceLocation texture;
    protected final PCleaner cleaner;
    
    public Texture(final byte[] in) throws IOException {
        this(new ByteArrayInputStream(in));
    }
    
    public Texture(final InputStream in) throws IOException {
        this(ImageIO.read(in));
    }
    
    public Texture(final BufferedImage img) {
        this(Texture.mc.getTextureManager().getDynamicTextureLocation(UUID.randomUUID().toString(), new DynamicTexture(img)), true);
    }
    
    public Texture(final ResourceLocation texture) {
        this(texture, false);
    }
    
    public Texture(final ResourceLocation texture, final boolean clean) {
        this.texture = texture;
        this.cleaner = (clean ? PCleaner.cleaner(this, () -> Texture.mc.getTextureManager().deleteTexture(texture)) : null);
    }
    
    public void render(final float x, final float y, final float width, final float height) {
        this.bindTexture();
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)x, (double)(y + height), 0.0).tex(0.0, 1.0).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0).tex(1.0, 1.0).endVertex();
        renderer.pos((double)(x + width), (double)y, 0.0).tex(1.0, 0.0).endVertex();
        renderer.pos((double)x, (double)y, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }
    
    public void bindTexture() {
        Texture.mc.getTextureManager().bindTexture(this.texture);
        GlStateManager.enableTexture2D();
    }
    
    @Override
    public void close() {
        if (this.cleaner != null) {
            this.cleaner.clean();
        }
    }
    
    @Override
    public String toString() {
        return this.texture.getPath();
    }
}
