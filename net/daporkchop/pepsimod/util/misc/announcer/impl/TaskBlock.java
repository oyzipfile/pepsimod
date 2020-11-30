// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.announcer.impl;

import net.daporkchop.pepsimod.util.misc.announcer.MessagePrefixes;
import net.daporkchop.pepsimod.util.misc.announcer.TaskType;
import net.minecraft.block.Block;
import net.daporkchop.pepsimod.util.misc.announcer.QueuedTask;

public class TaskBlock extends QueuedTask
{
    public Block block;
    public int count;
    
    public TaskBlock(final TaskType type, final Block block) {
        super(type);
        this.count = 1;
        this.block = block;
    }
    
    @Override
    public String getMessage() {
        return MessagePrefixes.getMessage(this.type, this.block.getLocalizedName(), this.count);
    }
}
