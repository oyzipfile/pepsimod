// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class CriticalsTranslator implements IConfigTranslator
{
    public static final CriticalsTranslator INSTANCE;
    public boolean packet;
    
    private CriticalsTranslator() {
        this.packet = true;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("packet", Boolean.valueOf(this.packet));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.packet = this.getBoolean(json, "packet", this.packet);
    }
    
    @Override
    public String name() {
        return "criticals";
    }
    
    static {
        INSTANCE = new CriticalsTranslator();
    }
}
