// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class EntitySpeedTranslator implements IConfigTranslator
{
    public static final EntitySpeedTranslator INSTANCE;
    public float speed;
    public float idleSpeed;
    
    private EntitySpeedTranslator() {
        this.speed = 1.0f;
        this.idleSpeed = 1.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("speed", (Number)this.speed);
        json.addProperty("idleSpeed", (Number)this.idleSpeed);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.speed = this.getFloat(json, "speed", this.speed);
        this.idleSpeed = this.getFloat(json, "idleSpeed", this.idleSpeed);
    }
    
    @Override
    public String name() {
        return "entityspeed";
    }
    
    static {
        INSTANCE = new EntitySpeedTranslator();
    }
}
