// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import java.io.IOException;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.impl.misc.ClickGuiMod;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    public static ClickGUI INSTANCE;
    public Window[] windows;
    
    public ClickGUI() {
        ClickGUI.INSTANCE = this;
    }
    
    public void setWindows(final Window... windows) {
        this.windows = windows;
    }
    
    public void initWindows() {
        for (final Window window : this.windows) {
            window.init(window.category);
        }
    }
    
    protected void keyTyped(final char eventChar, final int eventKey) {
        if (eventKey == 1 || eventKey == ClickGuiMod.INSTANCE.keybind.getKeyCode()) {
            ModuleManager.disableModule(ClickGuiMod.INSTANCE);
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public synchronized void sendToFront(final Window window) {
        if (this.containsWindow(window)) {
            int panelIndex = 0;
            for (int i = 0; i < this.windows.length; ++i) {
                if (this.windows[i] == window) {
                    panelIndex = i;
                    break;
                }
            }
            final Window t = this.windows[0];
            this.windows[0] = this.windows[panelIndex];
            this.windows[panelIndex] = t;
        }
    }
    
    public void mouseClicked(final int x, final int y, final int b) throws IOException {
        for (final Window window : this.windows) {
            window.processMouseClick(x, y, b);
        }
        super.mouseClicked(x, y, b);
    }
    
    public void mouseReleased(final int x, final int y, final int state) {
        for (final Window window : this.windows) {
            window.processMouseRelease(x, y, state);
        }
        super.mouseReleased(x, y, state);
    }
    
    public void drawScreen(final int x, final int y, final float ticks) {
        for (final Window window : this.windows) {
            window.draw(x, y);
        }
        super.drawScreen(x, y, ticks);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean containsWindow(final Window window) {
        for (final Window window2 : this.windows) {
            if (window == window2) {
                return true;
            }
        }
        return false;
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dWheel = MathHelper.clamp(Mouse.getEventDWheel(), -1, 1);
        if (dWheel != 0) {
            dWheel *= -1;
            final int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
            final int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            for (final Window window : this.windows) {
                window.handleScroll(dWheel, x, y);
            }
        }
    }
}
