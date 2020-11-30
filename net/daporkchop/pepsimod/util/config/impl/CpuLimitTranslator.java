// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class CpuLimitTranslator implements IConfigTranslator
{
    public static final CpuLimitTranslator INSTANCE;
    public int limit;
    
    private CpuLimitTranslator() {
        this.limit = 5;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("limit", (Number)this.limit);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.limit = this.getInt(json, "limit", this.limit);
    }
    
    @Override
    public String name() {
        return "cpuLimit";
    }
    
    static {
        INSTANCE = new CpuLimitTranslator();
    }
}
