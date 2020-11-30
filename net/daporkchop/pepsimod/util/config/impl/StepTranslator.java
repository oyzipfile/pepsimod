// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class StepTranslator implements IConfigTranslator
{
    public static final StepTranslator INSTANCE;
    public boolean legit;
    public int height;
    
    private StepTranslator() {
        this.legit = false;
        this.height = 1;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("legit", Boolean.valueOf(this.legit));
        json.addProperty("height", (Number)this.height);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.legit = this.getBoolean(json, "legit", this.legit);
        this.height = this.getInt(json, "height", this.height);
    }
    
    @Override
    public String name() {
        return "step";
    }
    
    static {
        INSTANCE = new StepTranslator();
    }
}
