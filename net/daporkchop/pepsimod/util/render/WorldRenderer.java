// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.render;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import java.awt.Color;
import net.daporkchop.pepsimod.util.RenderColor;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WorldRenderer implements AutoCloseable
{
    protected final double startX;
    protected final double startY;
    protected final double startZ;
    protected final double x;
    protected final double y;
    protected final double z;
    protected final float partialTicks;
    
    public WorldRenderer(final double startX, final double startY, final double startZ, final double x, final double y, final double z, final float partialTicks) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.x = x;
        this.y = y;
        this.z = z;
        this.partialTicks = partialTicks;
        this.init();
    }
    
    public WorldRenderer init() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        return this.resume();
    }
    
    @Override
    public void close() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.pause();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
    }
    
    public WorldRenderer resume() {
        GL11.glBegin(1);
        return this;
    }
    
    public WorldRenderer pause() {
        GL11.glEnd();
        return this;
    }
    
    public WorldRenderer(final Vec3d start, final double x, final double y, final double z, final float partialTicks) {
        this(start.x, start.y, start.z, x, y, z, partialTicks);
    }
    
    public WorldRenderer(final Vec3d start, final Vec3d pos, final float partialTicks) {
        this(start.x, start.y, start.z, pos.x, pos.y, pos.z, partialTicks);
    }
    
    public WorldRenderer color(final float r, final float g, final float b) {
        GL11.glColor4f(r, g, b, 1.0f);
        return this;
    }
    
    public WorldRenderer color(final float r, final float g, final float b, final float a) {
        GL11.glColor4f(r, g, b, a);
        return this;
    }
    
    public WorldRenderer color(final int r, final int g, final int b) {
        GL11.glColor4f(r * 0.003921569f, g * 0.003921569f, b * 0.003921569f, 1.0f);
        return this;
    }
    
    public WorldRenderer color(final int r, final int g, final int b, final int a) {
        GL11.glColor4f(r * 0.003921569f, g * 0.003921569f, b * 0.003921569f, a * 0.003921569f);
        return this;
    }
    
    public WorldRenderer color(final RenderColor color) {
        GL11.glColor4b(color.r, color.g, color.b, color.a);
        return this;
    }
    
    public WorldRenderer color(final Color color) {
        GL11.glColor4f(color.getRed() * 0.003921569f, color.getGreen() * 0.003921569f, color.getBlue() * 0.003921569f, color.getAlpha() * 0.003921569f);
        return this;
    }
    
    public WorldRenderer width(final float width) {
        this.pause();
        GL11.glLineWidth(width);
        return this.resume();
    }
    
    public WorldRenderer line(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        GL11.glVertex3d(x1 - this.x, y1 - this.y, z1 - this.z);
        GL11.glVertex3d(x2 - this.x, y2 - this.y, z2 - this.z);
        return this;
    }
    
    public WorldRenderer line(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        GL11.glVertex3d(x1 - this.x, y1 - this.y, z1 - this.z);
        GL11.glVertex3d(x2 - this.x, y2 - this.y, z2 - this.z);
        return this;
    }
    
    public WorldRenderer line(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        GL11.glVertex3d(x1 - this.x, y1 - this.y, z1 - this.z);
        GL11.glVertex3d(x2 - this.x, y2 - this.y, z2 - this.z);
        return this;
    }
    
    public WorldRenderer line(final Vec3d pos1, final Vec3d pos2) {
        return this.line(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z);
    }
    
    public WorldRenderer line(final Vec3d pos1, final Entity pos2) {
        return this.line(pos1.x, pos1.y, pos1.z, pos2.posX, pos2.posY, pos2.posZ);
    }
    
    public WorldRenderer line(final Entity pos1, final Entity pos2) {
        return this.line(pos1.posX, pos1.posY, pos1.posZ, pos2.posX, pos2.posY, pos2.posZ);
    }
    
    public WorldRenderer lineFromEyes(final double x, final double y, final double z) {
        GL11.glVertex3d(this.startX, this.startY, this.startZ);
        GL11.glVertex3d(x - this.x, y - this.y, z - this.z);
        return this;
    }
    
    public WorldRenderer lineFromEyes(final Vec3d pos) {
        return this.lineFromEyes(pos.x, pos.y, pos.z);
    }
    
    public WorldRenderer lineFromEyes(final Entity entity, final float partialTicks) {
        if (partialTicks == 1.0f) {
            return this.lineFromEyes(entity.posX, entity.posY, entity.posZ);
        }
        final double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        final double y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
        final double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        return this.lineFromEyes(x, y, z);
    }
    
    public WorldRenderer lineFromEyes(final Entity entity) {
        return this.lineFromEyes(entity, this.partialTicks);
    }
    
    public WorldRenderer outline(final AxisAlignedBB bb) {
        return this.outline(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }
    
    public WorldRenderer outline(final Entity entity) {
        final double x = entity.prevPosX + (entity.posX - entity.prevPosX) * this.partialTicks;
        final double y = entity.prevPosY + (entity.posY - entity.prevPosY) * this.partialTicks;
        final double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * this.partialTicks;
        final double halfwidth = entity.width * 0.5;
        return this.outline(x - halfwidth, y, z - halfwidth, x + halfwidth, y + entity.height, z + halfwidth);
    }
    
    public WorldRenderer outline(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return this.internal_outline(minX - this.x, minY - this.y, minZ - this.z, maxX - this.x, maxY - this.y, maxZ - this.z);
    }
    
    protected WorldRenderer internal_outline(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        GL11.glVertex3d(minX, minY, minZ);
        GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ);
        GL11.glVertex3d(minX, minY, minZ);
        GL11.glVertex3d(minX, minY, minZ);
        GL11.glVertex3d(minX, maxY, minZ);
        GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ);
        GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glVertex3d(minX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glVertex3d(minX, maxY, minZ);
        return this;
    }
}
