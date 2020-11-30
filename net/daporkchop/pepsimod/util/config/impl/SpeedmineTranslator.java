// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class SpeedmineTranslator implements IConfigTranslator
{
    public static final SpeedmineTranslator INSTANCE;
    public float speed;
    
    private SpeedmineTranslator() {
        this.speed = 0.4f;
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
        return "speedmine";
    }
    
    static {
        INSTANCE = new SpeedmineTranslator();
    }
}
