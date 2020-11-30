// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui;

import net.daporkchop.pepsimod.util.BetterScaledResolution;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.clickgui.entry.SubSlider;
import net.daporkchop.pepsimod.clickgui.entry.SubButton;
import net.daporkchop.pepsimod.clickgui.entry.Button;
import net.daporkchop.pepsimod.util.colors.ColorUtils;
import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import java.awt.Color;
import net.daporkchop.pepsimod.util.PepsiUtils;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.clickgui.api.IEntry;
import java.util.List;
import net.daporkchop.pepsimod.clickgui.api.EntryImplBase;

public class Window extends EntryImplBase
{
    public final String text;
    public List<IEntry> entries;
    public boolean isOpen;
    public int modulesCounted;
    public int scroll;
    public ModuleCategory category;
    private int renderYButton;
    private boolean isDragging;
    private int dragX;
    private int dragY;
    
    public Window(final int x, final int y, final String name, final ModuleCategory category) {
        super(x, y, 100, 12);
        this.entries = Collections.synchronizedList(new ArrayList<IEntry>());
        this.isOpen = false;
        this.modulesCounted = 0;
        this.scroll = 0;
        this.renderYButton = 0;
        this.isDragging = false;
        this.dragX = 0;
        this.dragY = 0;
        this.text = name;
        this.category = category;
    }
    
    @Override
    public void processMouseClick(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered()) {
            ClickGUI.INSTANCE.sendToFront(this);
            if (button == 0) {
                this.isDragging = true;
                this.dragX = mouseX - this.getX();
                this.dragY = mouseY - this.getY();
            }
            else if (button == 1) {
                this.isOpen = !this.isOpen;
            }
            else if (button == 2) {}
        }
        for (final IEntry entry : this.entries) {
            if (entry.shouldRender()) {
                entry.processMouseClick(mouseX, mouseY, button);
            }
        }
    }
    
    @Override
    public void processMouseRelease(final int mouseX, final int mouseY, final int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isDragging) {
            this.isDragging = false;
        }
        for (final IEntry entry : this.entries) {
            if (entry.shouldRender()) {
                entry.processMouseRelease(mouseX, mouseY, button);
            }
        }
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(8256);
        this.scroll = Math.max(0, this.scroll);
        this.scroll = Math.min(this.getDisplayableCount() - this.getModulesToDisplay(), this.scroll);
        this.updateIsMouseHovered(mouseX, mouseY);
        this.renderYButton = this.getY();
        PepsiUtils.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.getDisplayedHeight()), this.getColor());
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        EntryImplBase.drawString(this.getX() + 2, this.getY() + 2, this.text, Color.BLACK.getRGB());
        if (this.isOpen) {
            if (this.shouldScroll()) {
                final int barHeight = this.getScrollbarHeight();
                int barY = this.getScrollbarY();
                barY = Math.min(barY, this.getScrollingModuleCount() * 13 - 1 - barHeight);
                PepsiUtils.drawRect((float)(this.getX() + 97), (float)(this.getY() + 13 + barY), (float)(this.getX() + 99), (float)Math.min(this.getY() + 13 + barY + barHeight, this.getY() + this.getDisplayedHeight() - 1), HUDTranslator.INSTANCE.getColor());
            }
            else {
                PepsiUtils.drawRect((float)(this.getX() + 97), (float)(this.getY() + 13), (float)(this.getX() + 99), (float)(this.getDisplayedHeight() - 1), HUDTranslator.INSTANCE.getColor());
            }
            this.modulesCounted = 0;
            for (int i = this.getScroll(); i < this.getModulesToDisplay() + this.getScroll(); ++i) {
                final IEntry entry = this.getNextEntry();
                ++this.modulesCounted;
                entry.draw(mouseX, mouseY);
            }
        }
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public int getScrollbarHeight() {
        final double maxHeight = this.maxDisplayHeight();
        final double maxAllowedModules = this.getScrollingModuleCount();
        final double displayable = this.getDisplayableCount();
        final int result = (int)Math.floor(maxHeight * (maxAllowedModules / displayable));
        return result;
    }
    
    public int getScrollbarY() {
        final int displayable = this.getDisplayableCount();
        final int rest = displayable - this.scroll;
        final int resultRaw = displayable - rest;
        return resultRaw * 13;
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
        int i = this.height + 1;
        for (final IEntry entry : this.entries) {
            if (entry.shouldRender()) {
                i += 13;
            }
        }
        return i;
    }
    
    public int getDisplayableCount() {
        int i = 0;
        for (final IEntry entry : this.entries) {
            if (entry.shouldRender()) {
                ++i;
            }
        }
        return i;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(1, this.isMouseHovered(), false);
    }
    
    public Button addButton(final Button b) {
        this.entries.add(b);
        return b;
    }
    
    public SubButton addSubButton(final SubButton b) {
        this.entries.add(this.entries.indexOf(b.parent) + 1, b);
        b.parent.subEntries.add(b);
        return b;
    }
    
    public SubSlider addSubSlider(final SubSlider slider) {
        this.entries.add(this.entries.indexOf(slider.parent) + 1, slider);
        slider.parent.subEntries.add(slider);
        return slider;
    }
    
    public int getRenderYButton() {
        return this.renderYButton += 13;
    }
    
    @Override
    public boolean shouldRender() {
        return true;
    }
    
    @Override
    public void openGui() {
        for (final IEntry entry : this.entries) {
            entry.openGui();
        }
    }
    
    public int getScroll() {
        if (this.shouldScroll()) {
            return this.scroll;
        }
        return 0;
    }
    
    public void init(final ModuleCategory category) {
        for (final Module module : ModuleManager.AVALIBLE_MODULES) {
            if (module.getCategory() != category) {
                continue;
            }
            final Button b = this.addButton(new Button(this, module));
            for (final ModuleOption option : module.options) {
                if (option.makeButton) {
                    if (option.extended == null) {
                        this.addSubButton(new SubButton(b, option));
                    }
                    else {
                        if (option.extended.getType() != ExtensionType.TYPE_SLIDER) {
                            throw new IllegalStateException("Option " + option.getName() + " uses an unsupported extension type!");
                        }
                        this.addSubSlider(new SubSlider(b, option));
                    }
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return this.text;
    }
    
    @Override
    public boolean isOpen() {
        return this.isOpen;
    }
    
    @Override
    public void setOpen(final boolean val) {
        this.isOpen = val;
    }
    
    public int maxDisplayHeight() {
        int height = BetterScaledResolution.INSTANCE.scaledHeight;
        height = Math.floorDiv(height, 13);
        height = --height * 13;
        return height;
    }
    
    public int getScrollingModuleCount() {
        int height = BetterScaledResolution.INSTANCE.scaledHeight;
        height = Math.floorDiv(height, 13);
        height -= 2;
        return height;
    }
    
    public int getModulesToDisplay() {
        if (this.shouldScroll()) {
            return this.getScrollingModuleCount();
        }
        return this.getDisplayableCount();
    }
    
    public boolean shouldScroll() {
        final boolean val = this.getScrollingModuleCount() < this.getDisplayableCount();
        return val;
    }
    
    public int getDisplayedHeight() {
        final int max = this.maxDisplayHeight();
        final int normal = this.getHeight();
        final int toReturn = Math.min(max, normal);
        return toReturn;
    }
    
    public IEntry getNextEntry() {
        int a = 0;
        int i = this.scroll;
        IEntry entry;
        while (true) {
            entry = this.entries.get(i);
            if (entry.shouldRender()) {
                if (this.modulesCounted == 0 || a >= this.modulesCounted) {
                    break;
                }
                ++a;
            }
            ++i;
        }
        return entry;
    }
    
    public void handleScroll(final int dWheel, final int x, final int y) {
        this.updateIsMouseHoveredFull(x, y);
        if (this.isMouseHovered() && this.shouldScroll()) {
            this.scroll += dWheel;
        }
    }
    
    protected void updateIsMouseHoveredFull(final int mouseX, final int mouseY) {
        final int x = this.getX();
        final int y = this.getY();
        final int maxX = x + this.width;
        final int maxY = y + this.getDisplayedHeight();
        this.isHoveredCached = (x <= mouseX && mouseX <= maxX && y <= mouseY && mouseY <= maxY);
    }
}
