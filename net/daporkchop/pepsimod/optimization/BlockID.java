// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.optimization;

import java.util.ArrayList;
import net.minecraft.block.Block;
import java.util.List;

public interface BlockID
{
    public static final List<Block> BLOCK_LOOKUP = new ArrayList<Block>();
    
    int getBlockId();
    
    void internal_setBlockId(final int p0);
}
