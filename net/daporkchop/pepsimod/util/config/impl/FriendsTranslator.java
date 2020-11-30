// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class FriendsTranslator implements IConfigTranslator
{
    public static final FriendsTranslator INSTANCE;
    public Set<UUID> friends;
    
    private FriendsTranslator() {
        this.friends = new HashSet<UUID>();
    }
    
    @Override
    public void encode(final JsonObject json) {
        final JsonArray array = new JsonArray();
        for (final UUID uuid : this.friends) {
            final JsonObject object = new JsonObject();
            object.addProperty("msb", (Number)uuid.getMostSignificantBits());
            object.addProperty("lsb", (Number)uuid.getLeastSignificantBits());
            array.add((JsonElement)object);
        }
        json.add("friends", (JsonElement)array);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        final JsonArray array = this.getArray(json, "friends", new JsonArray());
        for (final JsonElement element : array) {
            if (element.isJsonPrimitive()) {
                this.friends.add(UUID.fromString(element.getAsString()));
            }
            else {
                final JsonObject object = element.getAsJsonObject();
                this.friends.add(new UUID(object.get("msb").getAsLong(), object.get("lsb").getAsLong()));
            }
        }
    }
    
    public boolean isFriend(final Entity entity) {
        return this.friends.contains(entity.getUniqueID());
    }
    
    @Override
    public String name() {
        return "friends";
    }
    
    static {
        INSTANCE = new FriendsTranslator();
    }
}
