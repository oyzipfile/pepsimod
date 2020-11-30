// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config;

import com.google.gson.JsonObject;

public class NullConfigTranslator implements IConfigTranslator
{
    public static final IConfigTranslator INSTANCE;
    
    private NullConfigTranslator() {
    }
    
    @Override
    public void encode(final JsonObject json) {
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        System.out.println("[Warning] Config element with name " + fieldName + "is being ignored, discarding " + json.entrySet().size() + " values!");
    }
    
    @Override
    public String name() {
        return null;
    }
    
    static {
        INSTANCE = new NullConfigTranslator();
    }
}
