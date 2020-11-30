// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui.entry;

import net.daporkchop.pepsimod.util.colors.ColorUtils;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.clickgui.Window;
import net.daporkchop.pepsimod.clickgui.api.EntryImplBase;

public class SubButton extends EntryImplBase
{
    public final Button parent;
    public Window window;
    public ModuleOption option;
    
    public SubButton(final Button parent, final ModuleOption option) {
        super(parent.window.getX() + 4, parent.getY() + 4, parent.window.getWidth() - 8, 12);
        this.parent = parent;
        this.window = parent.window;
        this.option = option;
    }
    
    @Override
    public void processMouseClick(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered() && button == 0) {
            this.option.setValue(!this.option.getValue());
        }
    }
    
    @Override
    public void processMouseRelease(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 4;
        this.updateIsMouseHovered(mouseX, mouseY);
        PepsiUtils.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.height), this.getColor());
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        SubButton.mc.fontRenderer.drawString(this.option.displayName, this.getX() + 2, this.getY() + 2, Color.BLACK.getRGB());
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public void setX(final int x) {
        this.x = x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public void setY(final int y) {
        this.y = y;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(0, this.isMouseHovered(), this.option.getValue());
    }
    
    @Override
    public boolean shouldRender() {
        return this.parent.isOpen && this.parent.shouldRender();
    }
    
    @Override
    public void openGui() {
    }
    
    @Override
    public String getName() {
        return this.option.getName();
    }
    
    @Override
    public boolean isOpen() {
        return false;
    }
    
    @Override
    public void setOpen(final boolean val) {
    }
}
