// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.announcer;

import net.daporkchop.pepsimod.util.PepsiConstants;

public abstract class QueuedTask extends PepsiConstants
{
    public final TaskType type;
    
    public QueuedTask(final TaskType type) {
        this.type = type;
    }
    
    public abstract String getMessage();
}
