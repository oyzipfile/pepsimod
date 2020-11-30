// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.module.api.ModuleSortType;
import java.util.HashMap;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class GeneralTranslator implements IConfigTranslator
{
    public static final GeneralTranslator INSTANCE;
    public boolean autoReconnect;
    public HashMap<String, ModuleState> states;
    public ModuleSortType sortType;
    public JsonObject json;
    
    private GeneralTranslator() {
        this.autoReconnect = false;
        this.states = new HashMap<String, ModuleState>();
        this.sortType = ModuleSortType.SIZE;
        this.json = new JsonObject();
    }
    
    @Override
    public void encode(final JsonObject json) {
        for (final Module module : ModuleManager.AVALIBLE_MODULES) {
            json.addProperty("module.enabled." + module.nameFull, module.state.toString());
        }
        json.addProperty("autoReconnect", Boolean.valueOf(this.autoReconnect));
        json.addProperty("sortType", (Number)this.sortType.ordinal());
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.json = json;
        this.autoReconnect = this.getBoolean(json, "autoReconnect", this.autoReconnect);
        this.sortType = ModuleSortType.fromOrdinal(this.getInt(json, "sortType", this.sortType.ordinal()));
    }
    
    public ModuleState getState(final String name, final ModuleState fallback) {
        if (this.json.has("module.enabled." + name)) {
            return ModuleState.fromString(this.json.get("module.enabled." + name).getAsString());
        }
        return fallback;
    }
    
    @Override
    public String name() {
        return "general";
    }
    
    static {
        INSTANCE = new GeneralTranslator();
    }
    
    public static class ModuleState
    {
        public static ModuleState DEFAULT;
        public boolean enabled;
        public boolean hidden;
        
        public static ModuleState fromString(final String from) {
            final String[] split = from.split(" ");
            return new ModuleState(Boolean.parseBoolean(split[0]), Boolean.parseBoolean(split[1]));
        }
        
        public ModuleState(final boolean a, final boolean b) {
            this.enabled = a;
            this.hidden = b;
        }
        
        @Override
        public String toString() {
            return this.enabled + " " + this.hidden;
        }
        
        static {
            ModuleState.DEFAULT = new ModuleState(false, false);
        }
    }
}
