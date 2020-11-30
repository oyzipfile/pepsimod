// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class ElytraFlyTranslator implements IConfigTranslator
{
    public static final ElytraFlyTranslator INSTANCE;
    public boolean easyStart;
    public boolean stopInWater;
    public boolean fly;
    public float speed;
    public ElytraFlyMode mode;
    
    private ElytraFlyTranslator() {
        this.easyStart = false;
        this.stopInWater = true;
        this.fly = false;
        this.speed = 0.2f;
        this.mode = ElytraFlyMode.getMode(0);
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("easyStart", Boolean.valueOf(this.easyStart));
        json.addProperty("stopInWater", Boolean.valueOf(this.stopInWater));
        json.addProperty("fly", Boolean.valueOf(this.fly));
        json.addProperty("speed", (Number)this.speed);
        json.addProperty("mode", (Number)this.mode.ordinal());
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.easyStart = this.getBoolean(json, "easyStart", this.easyStart);
        this.stopInWater = this.getBoolean(json, "stopInWater", this.stopInWater);
        this.fly = this.getBoolean(json, "fly", this.fly);
        this.speed = this.getFloat(json, "speed", this.speed);
        this.mode = ElytraFlyMode.getMode(this.getInt(json, "mode", this.mode.ordinal()));
    }
    
    @Override
    public String name() {
        return "elytraFly";
    }
    
    static {
        INSTANCE = new ElytraFlyTranslator();
    }
    
    public enum ElytraFlyMode
    {
        NORMAL, 
        PACKET;
        
        public static ElytraFlyMode getMode(final int id) {
            return values()[id];
        }
    }
}
