// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class AntiAFKTranslator implements IConfigTranslator
{
    public static final AntiAFKTranslator INSTANCE;
    public boolean spin;
    public boolean sneak;
    public boolean swingArm;
    public boolean move;
    public boolean strafe;
    public int delay;
    public boolean requireInactive;
    
    private AntiAFKTranslator() {
        this.spin = false;
        this.sneak = true;
        this.swingArm = true;
        this.move = false;
        this.strafe = false;
        this.delay = 5000;
        this.requireInactive = true;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("spin", Boolean.valueOf(this.spin));
        json.addProperty("sneak", Boolean.valueOf(this.sneak));
        json.addProperty("swingArm", Boolean.valueOf(this.swingArm));
        json.addProperty("move", Boolean.valueOf(this.move));
        json.addProperty("strafe", Boolean.valueOf(this.strafe));
        json.addProperty("delay", (Number)this.delay);
        json.addProperty("requireInactive", Boolean.valueOf(this.requireInactive));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.spin = this.getBoolean(json, "spin", this.spin);
        this.sneak = this.getBoolean(json, "sneak", this.sneak);
        this.swingArm = this.getBoolean(json, "swingArm", this.swingArm);
        this.move = this.getBoolean(json, "move", this.move);
        this.strafe = this.getBoolean(json, "strafe", this.strafe);
        this.delay = this.getInt(json, "delay", this.delay);
        this.requireInactive = this.getBoolean(json, "requireInactive", this.requireInactive);
    }
    
    @Override
    public String name() {
        return "antiafk";
    }
    
    static {
        INSTANCE = new AntiAFKTranslator();
    }
}
