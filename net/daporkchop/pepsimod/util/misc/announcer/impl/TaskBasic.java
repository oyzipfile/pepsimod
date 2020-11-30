// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.announcer.impl;

import net.daporkchop.pepsimod.util.misc.announcer.TaskType;
import net.daporkchop.pepsimod.util.misc.announcer.QueuedTask;

public class TaskBasic extends QueuedTask
{
    public String message;
    
    public TaskBasic(final TaskType type, final String message) {
        super(type);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
}
