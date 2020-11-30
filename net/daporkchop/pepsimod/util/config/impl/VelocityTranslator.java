// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class VelocityTranslator implements IConfigTranslator
{
    public static final VelocityTranslator INSTANCE;
    public float multiplier;
    
    private VelocityTranslator() {
        this.multiplier = 1.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("multiplier", (Number)this.multiplier);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.multiplier = this.getFloat(json, "multiplier", this.multiplier);
    }
    
    @Override
    public String name() {
        return "velocity";
    }
    
    static {
        INSTANCE = new VelocityTranslator();
    }
}
