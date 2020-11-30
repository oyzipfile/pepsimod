// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class NameTagsTranslator implements IConfigTranslator
{
    public static final NameTagsTranslator INSTANCE;
    public float scale;
    
    private NameTagsTranslator() {
        this.scale = 1.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("scale", (Number)this.scale);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.scale = this.getFloat(json, "scale", this.scale);
    }
    
    @Override
    public String name() {
        return "nametags";
    }
    
    static {
        INSTANCE = new NameTagsTranslator();
    }
}
