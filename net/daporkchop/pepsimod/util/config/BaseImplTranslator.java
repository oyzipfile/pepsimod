// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config;

import com.google.gson.JsonObject;

public class BaseImplTranslator implements IConfigTranslator
{
    public static final BaseImplTranslator INSTANCE;
    
    private BaseImplTranslator() {
    }
    
    @Override
    public void encode(final JsonObject json) {
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
    }
    
    @Override
    public String name() {
        return "delet_this";
    }
    
    static {
        INSTANCE = new BaseImplTranslator();
    }
}
