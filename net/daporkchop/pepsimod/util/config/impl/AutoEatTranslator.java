// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class AutoEatTranslator implements IConfigTranslator
{
    public static final AutoEatTranslator INSTANCE;
    public float threshold;
    
    private AutoEatTranslator() {
        this.threshold = 7.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("threshold", (Number)this.threshold);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.threshold = this.getFloat(json, "threshold", this.threshold);
    }
    
    @Override
    public String name() {
        return "autoeat";
    }
    
    static {
        INSTANCE = new AutoEatTranslator();
    }
}
