// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class FreecamTranslator implements IConfigTranslator
{
    public static final FreecamTranslator INSTANCE;
    public float speed;
    
    private FreecamTranslator() {
        this.speed = 1.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("speed", (Number)this.speed);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.speed = this.getFloat(json, "speed", this.speed);
    }
    
    @Override
    public String name() {
        return "freecam";
    }
    
    static {
        INSTANCE = new FreecamTranslator();
    }
}
