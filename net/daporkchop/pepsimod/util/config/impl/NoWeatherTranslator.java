// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class NoWeatherTranslator implements IConfigTranslator
{
    public static final NoWeatherTranslator INSTANCE;
    public boolean disableRain;
    public boolean changeTime;
    public int time;
    
    private NoWeatherTranslator() {
        this.disableRain = false;
        this.changeTime = false;
        this.time = 0;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("disableRain", Boolean.valueOf(this.disableRain));
        json.addProperty("changeTime", Boolean.valueOf(this.changeTime));
        json.addProperty("time", (Number)this.time);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.disableRain = this.getBoolean(json, "disableRain", this.disableRain);
        this.changeTime = this.getBoolean(json, "changeTime", this.changeTime);
        this.time = this.getInt(json, "time", this.time);
    }
    
    @Override
    public String name() {
        return "noWeather";
    }
    
    static {
        INSTANCE = new NoWeatherTranslator();
    }
}
