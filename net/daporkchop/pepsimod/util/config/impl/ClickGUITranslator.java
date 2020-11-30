// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import java.util.Iterator;
import net.daporkchop.pepsimod.clickgui.Window;
import net.daporkchop.pepsimod.clickgui.api.IEntry;
import net.daporkchop.pepsimod.clickgui.ClickGUI;
import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class ClickGUITranslator implements IConfigTranslator
{
    public static final ClickGUITranslator INSTANCE;
    
    private ClickGUITranslator() {
    }
    
    @Override
    public void encode(final JsonObject json) {
        for (final Window window : ClickGUI.INSTANCE.windows) {
            json.addProperty(window.text + ".x", (Number)window.x);
            json.addProperty(window.text + ".y", (Number)window.y);
            json.addProperty(window.text + ".open", Boolean.valueOf(window.isOpen()));
            for (final IEntry entry : window.entries) {
                json.addProperty(window.text + '.' + entry.getName() + ".open", Boolean.valueOf(entry.isOpen()));
            }
        }
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        for (final Window window : ClickGUI.INSTANCE.windows) {
            window.setX(this.getInt(json, window.text + ".x", window.x));
            window.setY(this.getInt(json, window.text + ".y", window.y));
            window.setOpen(this.getBoolean(json, window.text + ".open", window.isOpen()));
            for (final IEntry entry : window.entries) {
                entry.setOpen(this.getBoolean(json, window.text + '.' + entry.getName() + ".open", entry.isOpen()));
            }
        }
    }
    
    @Override
    public String name() {
        return "clickgui";
    }
    
    static {
        INSTANCE = new ClickGUITranslator();
    }
}
