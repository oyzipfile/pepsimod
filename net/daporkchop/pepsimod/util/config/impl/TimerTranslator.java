// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class TimerTranslator implements IConfigTranslator
{
    public static final TimerTranslator INSTANCE;
    public float multiplier;
    public boolean tpsSync;
    
    private TimerTranslator() {
        this.multiplier = 1.0f;
        this.tpsSync = false;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("multiplier", (Number)this.multiplier);
        json.addProperty("tpsSync", Boolean.valueOf(this.tpsSync));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.multiplier = this.getFloat(json, "multiplier", this.multiplier);
        this.tpsSync = this.getBoolean(json, "tpsSync", this.tpsSync);
    }
    
    @Override
    public String name() {
        return "timer";
    }
    
    static {
        INSTANCE = new TimerTranslator();
    }
}
