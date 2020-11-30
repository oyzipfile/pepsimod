// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui.entry;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.util.colors.ColorUtils;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.clickgui.Window;
import net.daporkchop.pepsimod.clickgui.api.EntryImplBase;

public class SubSlider extends EntryImplBase
{
    public final Button parent;
    public Window window;
    public ModuleOption option;
    public ExtensionSlider slider;
    public int intValue;
    public float floatValue;
    public int currentWidth;
    public boolean isFloat;
    public boolean dragging;
    
    public SubSlider(final Button parent, final ModuleOption option) {
        super(parent.window.getX() + 4, parent.getY() + 4, parent.window.getWidth() - 8, 12);
        this.dragging = false;
        this.parent = parent;
        this.window = parent.window;
        this.slider = (ExtensionSlider)option.extended;
        final boolean isFloat = this.slider.dataType == ExtensionType.VALUE_FLOAT;
        this.isFloat = isFloat;
        if (isFloat) {
            this.floatValue = option.getValue();
        }
        else {
            this.intValue = (Integer)(Object)option.getValue();
        }
        this.option = option;
    }
    
    @Override
    public void processMouseClick(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered() && button == 0) {
            this.dragging = true;
        }
    }
    
    @Override
    public void processMouseRelease(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.dragging && button == 0) {
            this.dragging = false;
            this.getWidthFromValue();
        }
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.currentWidth = mouseX - this.getX();
            if (this.currentWidth < 0) {
                this.currentWidth = 0;
            }
            else if (this.currentWidth > 92) {
                this.currentWidth = 92;
            }
            this.updateValueFromWidth();
        }
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 4;
        this.updateIsMouseHovered(mouseX, mouseY);
        PepsiUtils.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.height), ColorUtils.BACKGROUND);
        PepsiUtils.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.currentWidth), (float)(this.getY() + this.height), this.getColor());
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        SubSlider.mc.fontRenderer.drawString(this.option.displayName + ": " + (this.isFloat ? PepsiUtils.roundFloatForSlider(this.floatValue) : Integer.valueOf(this.intValue)), this.getX() + 2, this.getY() + 2, Color.BLACK.getRGB());
    }
    
    public void updateValueFromWidth() {
        float val = this.currentWidth / 92.0f;
        val *= this.getMax() - this.getMin();
        val += this.getMin();
        val = PepsiUtils.round(val, this.getStep());
        val = PepsiUtils.ensureRange(val, this.getMin(), this.getMax());
        if (this.isFloat) {
            this.floatValue = val;
            this.option.setValue(val);
        }
        else {
            this.intValue = (int)val;
            this.option.setValue((int)val);
        }
    }
    
    public float getMax() {
        final float val = (float)(this.isFloat ? this.slider.max : ((int)this.slider.max + 0.0f));
        return val;
    }
    
    public float getMin() {
        final float val = (float)(this.isFloat ? this.slider.min : ((int)this.slider.min + 0.0f));
        return val;
    }
    
    public float getStep() {
        final float val = (float)(this.isFloat ? this.slider.step : ((int)this.slider.step + 0.0f));
        return val;
    }
    
    public int getWidthFromValue() {
        float val = this.isFloat ? this.floatValue : (this.intValue + 0.0f);
        val -= this.getMin();
        val /= this.getMax() - this.getMin();
        val *= 92.0f;
        return this.currentWidth = PepsiUtils.ensureRange((int)val, 0, 92);
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
        return ColorUtils.getColorForGuiEntry(2, this.isMouseHovered(), false);
    }
    
    @Override
    public boolean shouldRender() {
        return this.parent.isOpen && this.parent.shouldRender();
    }
    
    @Override
    public void openGui() {
        if (this.isFloat) {
            this.floatValue = this.option.getValue();
        }
        else {
            this.intValue = this.option.getValue();
        }
        this.getWidthFromValue();
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
