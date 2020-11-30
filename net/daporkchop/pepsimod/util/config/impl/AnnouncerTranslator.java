// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class AnnouncerTranslator implements IConfigTranslator
{
    public static final AnnouncerTranslator INSTANCE;
    public boolean clientSide;
    public boolean join;
    public boolean leave;
    public boolean eat;
    public boolean walk;
    public boolean mine;
    public boolean place;
    public int delay;
    
    private AnnouncerTranslator() {
        this.clientSide = false;
        this.join = false;
        this.leave = false;
        this.eat = false;
        this.walk = false;
        this.mine = false;
        this.place = false;
        this.delay = 5000;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("clientSide", Boolean.valueOf(this.clientSide));
        json.addProperty("join", Boolean.valueOf(this.join));
        json.addProperty("leave", Boolean.valueOf(this.leave));
        json.addProperty("eat", Boolean.valueOf(this.eat));
        json.addProperty("walk", Boolean.valueOf(this.walk));
        json.addProperty("mine", Boolean.valueOf(this.mine));
        json.addProperty("place", Boolean.valueOf(this.place));
        json.addProperty("delay", (Number)this.delay);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.clientSide = this.getBoolean(json, "clientSide", this.clientSide);
        this.join = this.getBoolean(json, "join", this.join);
        this.leave = this.getBoolean(json, "leave", this.leave);
        this.eat = this.getBoolean(json, "eat", this.eat);
        this.walk = this.getBoolean(json, "walk", this.walk);
        this.mine = this.getBoolean(json, "mine", this.mine);
        this.place = this.getBoolean(json, "place", this.place);
        this.delay = this.getInt(json, "delay", this.delay);
    }
    
    @Override
    public String name() {
        return "announcer";
    }
    
    static {
        INSTANCE = new AnnouncerTranslator();
    }
}
