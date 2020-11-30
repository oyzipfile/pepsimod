// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.clickgui.Window;
import net.minecraft.client.gui.GuiScreen;
import net.daporkchop.pepsimod.clickgui.ClickGUI;
import net.daporkchop.pepsimod.module.api.Module;

public class ClickGuiMod extends Module
{
    public static ClickGuiMod INSTANCE;
    
    public ClickGuiMod(final boolean isEnabled, final int key) {
        super(isEnabled, "ClickGUI", key, true);
        ClickGuiMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (ClickGuiMod.pepsimod.isInitialized) {
            for (final Window window : ClickGUI.INSTANCE.windows) {
                window.openGui();
            }
            ClickGuiMod.mc.displayGuiScreen((GuiScreen)ClickGUI.INSTANCE);
        }
    }
    
    @Override
    public void onDisable() {
        if (ClickGuiMod.mc.currentScreen instanceof ClickGUI) {
            ClickGuiMod.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        ClickGuiMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public void registerKeybind(final String name, final int key) {
        ClientRegistry.registerKeyBinding(this.keybind = new KeyBinding("§cOpen ClickGUI", 54, "key.categories.pepsimod"));
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
}
