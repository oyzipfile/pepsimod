// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import org.lwjgl.opengl.GL11;
import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class HUDTranslator implements IConfigTranslator
{
    public static final HUDTranslator INSTANCE;
    public boolean drawLogo;
    public boolean arrayList;
    public boolean TPS;
    public boolean coords;
    public boolean netherCoords;
    public boolean arrayListTop;
    public boolean serverBrand;
    public boolean rainbow;
    public int r;
    public int g;
    public int b;
    public boolean direction;
    public boolean armor;
    public boolean effects;
    public boolean fps;
    public boolean ping;
    public JsonObject json;
    
    private HUDTranslator() {
        this.drawLogo = true;
        this.arrayList = true;
        this.TPS = false;
        this.coords = false;
        this.netherCoords = false;
        this.arrayListTop = true;
        this.serverBrand = false;
        this.rainbow = true;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.direction = true;
        this.armor = false;
        this.effects = false;
        this.fps = true;
        this.ping = true;
        this.json = new JsonObject();
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("drawLogo", Boolean.valueOf(this.drawLogo));
        json.addProperty("arrayList", Boolean.valueOf(this.arrayList));
        json.addProperty("tps", Boolean.valueOf(this.TPS));
        json.addProperty("coords", Boolean.valueOf(this.coords));
        json.addProperty("netherCoords", Boolean.valueOf(this.netherCoords));
        json.addProperty("arrayListTop", Boolean.valueOf(this.arrayListTop));
        json.addProperty("serverBrand", Boolean.valueOf(this.serverBrand));
        json.addProperty("rainbow", Boolean.valueOf(this.rainbow));
        json.addProperty("r", (Number)this.r);
        json.addProperty("g", (Number)this.g);
        json.addProperty("b", (Number)this.b);
        json.addProperty("direction", Boolean.valueOf(this.direction));
        json.addProperty("armor", Boolean.valueOf(this.armor));
        json.addProperty("effects", Boolean.valueOf(this.effects));
        json.addProperty("fps", Boolean.valueOf(this.fps));
        json.addProperty("ping", Boolean.valueOf(this.ping));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.json = json;
    }
    
    public void parseConfigLate() {
        this.drawLogo = this.getBoolean(this.json, "drawLogo", this.drawLogo);
        this.arrayList = this.getBoolean(this.json, "arrayList", this.arrayList);
        this.TPS = this.getBoolean(this.json, "tps", this.TPS);
        this.coords = this.getBoolean(this.json, "coords", this.coords);
        this.netherCoords = this.getBoolean(this.json, "netherCoords", this.netherCoords);
        this.arrayListTop = this.getBoolean(this.json, "arrayListTop", this.arrayListTop);
        this.serverBrand = this.getBoolean(this.json, "serverBrand", this.serverBrand);
        this.rainbow = this.getBoolean(this.json, "rainbow", this.rainbow);
        this.r = this.getInt(this.json, "r", this.r);
        this.g = this.getInt(this.json, "g", this.g);
        this.b = this.getInt(this.json, "b", this.b);
        this.direction = this.getBoolean(this.json, "direction", this.direction);
        this.armor = this.getBoolean(this.json, "armor", this.armor);
        this.effects = this.getBoolean(this.json, "effects", this.effects);
        this.fps = this.getBoolean(this.json, "fps", this.fps);
        this.ping = this.getBoolean(this.json, "ping", this.ping);
    }
    
    @Override
    public String name() {
        return "hud";
    }
    
    public void bindColor() {
        final byte r = (byte)Math.floorDiv(this.r, 2);
        final byte g = (byte)Math.floorDiv(this.g, 2);
        final byte b = (byte)Math.floorDiv(this.b, 2);
        GL11.glColor3b(r, g, b);
    }
    
    public int getColor() {
        return 0xFF000000 | (this.r & 0xFF) << 16 | (this.g & 0xFF) << 8 | (this.b & 0xFF);
    }
    
    static {
        INSTANCE = new HUDTranslator();
    }
}
