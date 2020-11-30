// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class BedBomberTranslator implements IConfigTranslator
{
    public static final BedBomberTranslator INSTANCE;
    public float range;
    public int delay;
    public boolean resupply;
    
    private BedBomberTranslator() {
        this.range = 4.0f;
        this.delay = 500;
        this.resupply = true;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("range", (Number)this.range);
        json.addProperty("delay", (Number)this.delay);
        json.addProperty("resupply", Boolean.valueOf(this.resupply));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.range = this.getFloat(json, "range", this.range);
        this.delay = this.getInt(json, "delay", this.delay);
        this.resupply = this.getBoolean(json, "resupply", this.resupply);
    }
    
    @Override
    public String name() {
        return "bedbomber";
    }
    
    static {
        INSTANCE = new BedBomberTranslator();
    }
}
