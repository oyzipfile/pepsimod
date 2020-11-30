// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config.impl;

import net.daporkchop.pepsimod.optimization.BlockID;
import net.minecraft.util.ResourceLocation;
import java.util.function.Function;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import it.unimi.dsi.fastutil.ints.IntIterator;
import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class XrayTranslator implements IConfigTranslator
{
    public static final XrayTranslator INSTANCE;
    public IntOpenHashSet target_blocks;
    
    private XrayTranslator() {
        this.target_blocks = new IntOpenHashSet();
    }
    
    @Override
    public void encode(final JsonObject json) {
        final JsonArray array = new JsonArray();
        for (final int id : this.target_blocks) {
            array.add(((Block)Block.REGISTRY.getObjectById(id)).getRegistryName().toString());
        }
        json.add("targetBlocks_v2", (JsonElement)array);
    }
    
    @Override
    public void decode(final String fieldName, final JsonObject json) {
        this.target_blocks.clear();
        StreamSupport.stream((Spliterator<Object>)this.getArray(json, "targetBlocks", new JsonArray()).spliterator(), false).mapToInt(JsonElement::getAsInt).forEach(this.target_blocks::add);
        StreamSupport.stream((Spliterator<Object>)this.getArray(json, "targetBlocks_v2", new JsonArray()).spliterator(), false).map((Function<? super Object, ?>)JsonElement::getAsString).map((Function<? super Object, ?>)ResourceLocation::new).map((Function<? super Object, ?>)Block.REGISTRY::func_82594_a).map((Function<? super Object, ?>)BlockID.class::cast).mapToInt(BlockID::getBlockId).forEach(this.target_blocks::add);
        this.target_blocks.trim();
    }
    
    @Override
    public String name() {
        return "xray";
    }
    
    public boolean isTargeted(final Block block) {
        return this.target_blocks.contains(((BlockID)block).getBlockId());
    }
    
    public boolean isTargeted(final BlockID block) {
        return this.target_blocks.contains(block.getBlockId());
    }
    
    static {
        INSTANCE = new XrayTranslator();
    }
}
