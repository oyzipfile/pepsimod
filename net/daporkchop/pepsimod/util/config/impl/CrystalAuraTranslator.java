// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class CrystalAuraTranslator implements IConfigTranslator
{
    public static final CrystalAuraTranslator INSTANCE;
    public float speed;
    public float range;
    
    private CrystalAuraTranslator() {
        this.speed = 1.0f;
        this.range = 3.8f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("speed", (Number)this.speed);
        json.addProperty("range", (Number)this.range);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.speed = this.getFloat(json, "speed", this.speed);
        this.range = this.getFloat(json, "range", this.range);
    }
    
    @Override
    public String name() {
        return "crystalaura";
    }
    
    static {
        INSTANCE = new CrystalAuraTranslator();
    }
}
