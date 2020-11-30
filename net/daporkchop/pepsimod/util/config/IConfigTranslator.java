// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface IConfigTranslator
{
    void encode(final JsonObject p0);
    
    void decode(final String p0, final JsonObject p1);
    
    String name();
    
    default int getInt(final JsonObject object, final String name, final int def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().intValue();
            }
        }
        return def;
    }
    
    default short getShort(final JsonObject object, final String name, final short def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().shortValue();
            }
        }
        return def;
    }
    
    default byte getByte(final JsonObject object, final String name, final byte def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().byteValue();
            }
        }
        return def;
    }
    
    default long getLong(final JsonObject object, final String name, final long def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().longValue();
            }
        }
        return def;
    }
    
    default float getFloat(final JsonObject object, final String name, final float def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().floatValue();
            }
        }
        return def;
    }
    
    default double getDouble(final JsonObject object, final String name, final double def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber().doubleValue();
            }
        }
        return def;
    }
    
    default boolean getBoolean(final JsonObject object, final String name, final boolean def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
                return element.getAsJsonPrimitive().getAsBoolean();
            }
        }
        return def;
    }
    
    default String getString(final JsonObject object, final String name, final String def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                return element.getAsJsonPrimitive().getAsString();
            }
        }
        return def;
    }
    
    default JsonArray getArray(final JsonObject object, final String name, final JsonArray def) {
        if (object.has(name)) {
            final JsonElement element = object.get(name);
            if (element.isJsonArray()) {
                return element.getAsJsonArray();
            }
        }
        return def;
    }
}
