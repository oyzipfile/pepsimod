// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui.entry;

import net.daporkchop.pepsimod.util.colors.ColorUtils;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.module.ModuleManager;
import java.util.Collections;
import java.util.ArrayList;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.clickgui.Window;
import net.daporkchop.pepsimod.clickgui.api.IEntry;
import java.util.List;
import net.daporkchop.pepsimod.clickgui.api.EntryImplBase;

public class Button extends EntryImplBase
{
    public List<IEntry> subEntries;
    public boolean isOpen;
    public Window window;
    public Module module;
    
    public Button(final Window window, final Module module) {
        super(window.getX() + 2, window.getY() + 2, window.getWidth() - 6, 12);
        this.subEntries = Collections.synchronizedList(new ArrayList<IEntry>());
        this.isOpen = false;
        this.window = window;
        this.module = module;
    }
    
    @Override
    public void processMouseClick(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered()) {
            if (button == 0) {
                ModuleManager.toggleModule(this.module);
            }
            else if (button == 1) {
                this.isOpen = !this.isOpen;
            }
        }
    }
    
    @Override
    public void processMouseRelease(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 2;
        this.updateIsMouseHovered(mouseX, mouseY);
        PepsiUtils.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.height), this.getColor());
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        EntryImplBase.drawString(this.getX() + 2, this.getY() + 2, this.module.nameFull, Color.BLACK.getRGB());
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
        int i = this.height;
        if (this.isOpen) {
            i += 13 * this.subEntries.size();
        }
        return i;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(0, this.isMouseHovered(), this.module.state.enabled);
    }
    
    @Override
    public boolean shouldRender() {
        return this.window.isOpen;
    }
    
    @Override
    public void openGui() {
    }
    
    @Override
    public String getName() {
        return this.module.name;
    }
    
    @Override
    public boolean isOpen() {
        return this.isOpen;
    }
    
    @Override
    public void setOpen(final boolean val) {
        this.isOpen = val;
    }
}
