// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class TracersTranslator implements IConfigTranslator
{
    public static final TracersTranslator INSTANCE;
    public boolean sleeping;
    public boolean invisible;
    public boolean friendColors;
    public boolean animals;
    public boolean monsters;
    public boolean players;
    public boolean items;
    public boolean everything;
    public boolean distanceColor;
    public float width;
    
    private TracersTranslator() {
        this.sleeping = false;
        this.invisible = false;
        this.friendColors = true;
        this.animals = false;
        this.monsters = false;
        this.players = false;
        this.items = false;
        this.everything = false;
        this.distanceColor = true;
        this.width = 2.0f;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("sleeping", Boolean.valueOf(this.sleeping));
        json.addProperty("invisible", Boolean.valueOf(this.invisible));
        json.addProperty("friendColors", Boolean.valueOf(this.friendColors));
        json.addProperty("animals", Boolean.valueOf(this.animals));
        json.addProperty("monsters", Boolean.valueOf(this.monsters));
        json.addProperty("players", Boolean.valueOf(this.players));
        json.addProperty("items", Boolean.valueOf(this.items));
        json.addProperty("everything", Boolean.valueOf(this.everything));
        json.addProperty("distanceColor", Boolean.valueOf(this.distanceColor));
        json.addProperty("width", (Number)this.width);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.sleeping = this.getBoolean(json, "sleeping", this.sleeping);
        this.invisible = this.getBoolean(json, "invisible", this.invisible);
        this.friendColors = this.getBoolean(json, "friendColors", this.friendColors);
        this.animals = this.getBoolean(json, "animals", this.animals);
        this.monsters = this.getBoolean(json, "monsters", this.monsters);
        this.players = this.getBoolean(json, "players", this.players);
        this.items = this.getBoolean(json, "items", this.items);
        this.everything = this.getBoolean(json, "everything", this.everything);
        this.distanceColor = this.getBoolean(json, "distanceColor", this.distanceColor);
        this.width = this.getFloat(json, "width", this.width);
    }
    
    @Override
    public String name() {
        return "tracers";
    }
    
    static {
        INSTANCE = new TracersTranslator();
    }
}
