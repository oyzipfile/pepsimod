// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class NotificationsTranslator implements IConfigTranslator
{
    public static final NotificationsTranslator INSTANCE;
    public boolean queue;
    public boolean death;
    public boolean chat;
    public boolean player;
    
    private NotificationsTranslator() {
        this.queue = false;
        this.death = false;
        this.chat = false;
        this.player = false;
    }
    
    @Override
    public void encode(final JsonObject json) {
        json.addProperty("queue", Boolean.valueOf(this.queue));
        json.addProperty("death", Boolean.valueOf(this.death));
        json.addProperty("chat", Boolean.valueOf(this.chat));
        json.addProperty("player", Boolean.valueOf(this.player));
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.queue = this.getBoolean(json, "queue", this.queue);
        this.death = this.getBoolean(json, "death", this.death);
        this.chat = this.getBoolean(json, "chat", this.chat);
        this.player = this.getBoolean(json, "player", this.player);
    }
    
    @Override
    public String name() {
        return "notifications";
    }
    
    static {
        INSTANCE = new NotificationsTranslator();
    }
}
