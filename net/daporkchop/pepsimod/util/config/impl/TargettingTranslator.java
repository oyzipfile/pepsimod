// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class TargettingTranslator implements IConfigTranslator
{
    public static final TargettingTranslator INSTANCE;
    public boolean players;
    public boolean animals;
    public boolean monsters;
    public boolean golems;
    public boolean sleeping;
    public boolean invisible;
    public boolean teams;
    public boolean friends;
    public boolean through_walls;
    public boolean use_cooldown;
    public boolean silent;
    public boolean rotate;
    public TargetBone targetBone;
    public float fov;
    public float reach;
    public int delay;
    
    private TargettingTranslator() {
        this.players = false;
        this.animals = false;
        this.monsters = false;
        this.golems = false;
        this.sleeping = false;
        this.invisible = false;
        this.teams = false;
        this.friends = false;
        this.through_walls = false;
        this.use_cooldown = false;
        this.silent = false;
        this.rotate = false;
        this.targetBone = TargetBone.FEET;
        this.fov = 360.0f;
        this.reach = 4.25f;
        this.delay = 20;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("players", Boolean.valueOf(this.players));
        json.addProperty("animals", Boolean.valueOf(this.animals));
        json.addProperty("monsters", Boolean.valueOf(this.monsters));
        json.addProperty("golems", Boolean.valueOf(this.golems));
        json.addProperty("sleeping", Boolean.valueOf(this.sleeping));
        json.addProperty("invisible", Boolean.valueOf(this.invisible));
        json.addProperty("teams", Boolean.valueOf(this.teams));
        json.addProperty("friends", Boolean.valueOf(this.friends));
        json.addProperty("through_walls", Boolean.valueOf(this.through_walls));
        json.addProperty("use_cooldown", Boolean.valueOf(this.use_cooldown));
        json.addProperty("silent", Boolean.valueOf(this.silent));
        json.addProperty("rotate", Boolean.valueOf(this.rotate));
        json.addProperty("bone", (Number)this.targetBone.ordinal());
        json.addProperty("fov", (Number)this.fov);
        json.addProperty("reach", (Number)this.reach);
        json.addProperty("delay", (Number)this.delay);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.players = this.getBoolean(json, "players", this.players);
        this.animals = this.getBoolean(json, "animals", this.animals);
        this.monsters = this.getBoolean(json, "monsters", this.monsters);
        this.golems = this.getBoolean(json, "golems", this.golems);
        this.sleeping = this.getBoolean(json, "sleeping", this.sleeping);
        this.invisible = this.getBoolean(json, "invisible", this.invisible);
        this.teams = this.getBoolean(json, "teams", this.teams);
        this.friends = this.getBoolean(json, "friends", this.friends);
        this.through_walls = this.getBoolean(json, "through_walls", this.through_walls);
        this.use_cooldown = this.getBoolean(json, "use_cooldown", this.use_cooldown);
        this.silent = this.getBoolean(json, "silent", this.silent);
        this.rotate = this.getBoolean(json, "rotate", this.rotate);
        this.targetBone = TargetBone.getBone(this.getInt(json, "bone", this.targetBone.ordinal()));
        this.fov = this.getFloat(json, "fov", this.fov);
        this.reach = this.getFloat(json, "reach", this.reach);
        this.delay = this.getInt(json, "delay", this.delay);
    }
    
    @Override
    public String name() {
        return "targetting";
    }
    
    static {
        INSTANCE = new TargettingTranslator();
    }
    
    public enum TargetBone
    {
        HEAD, 
        FEET, 
        MIDDLE;
        
        public static TargetBone getBone(final int id) {
            return values()[id];
        }
    }
}
