// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class ESPTranslator implements IConfigTranslator
{
    public static final ESPTranslator INSTANCE;
    public boolean basic;
    public boolean trapped;
    public boolean ender;
    public boolean hopper;
    public boolean furnace;
    public boolean monsters;
    public boolean animals;
    public boolean players;
    public boolean golems;
    public boolean invisible;
    public boolean friendColors;
    public boolean box;
    
    private ESPTranslator() {
        this.basic = false;
        this.trapped = false;
        this.ender = false;
        this.hopper = false;
        this.furnace = false;
        this.monsters = false;
        this.animals = false;
        this.players = false;
        this.golems = false;
        this.invisible = false;
        this.friendColors = true;
        this.box = false;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("basic", Boolean.valueOf(this.basic));
        json.addProperty("trapped", Boolean.valueOf(this.trapped));
        json.addProperty("ender", Boolean.valueOf(this.ender));
        json.addProperty("hopper", Boolean.valueOf(this.hopper));
        json.addProperty("furnace", Boolean.valueOf(this.furnace));
        json.addProperty("monsters", Boolean.valueOf(this.monsters));
        json.addProperty("animals", Boolean.valueOf(this.animals));
        json.addProperty("players", Boolean.valueOf(this.players));
        json.addProperty("golems", Boolean.valueOf(this.golems));
        json.addProperty("invisible", Boolean.valueOf(this.invisible));
        json.addProperty("friendColors", Boolean.valueOf(this.friendColors));
        json.addProperty("box", Boolean.valueOf(this.box));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.basic = this.getBoolean(json, "basic", this.basic);
        this.trapped = this.getBoolean(json, "trapped", this.trapped);
        this.ender = this.getBoolean(json, "ender", this.ender);
        this.hopper = this.getBoolean(json, "hopper", this.hopper);
        this.furnace = this.getBoolean(json, "furnace", this.furnace);
        this.monsters = this.getBoolean(json, "monsters", this.monsters);
        this.animals = this.getBoolean(json, "animals", this.animals);
        this.players = this.getBoolean(json, "players", this.players);
        this.golems = this.getBoolean(json, "golems", this.golems);
        this.invisible = this.getBoolean(json, "invisible", this.invisible);
        this.friendColors = this.getBoolean(json, "friendColors", this.friendColors);
        this.box = this.getBoolean(json, "box", this.box);
    }
    
    @Override
    public String name() {
        return "esp";
    }
    
    static {
        INSTANCE = new ESPTranslator();
    }
}
