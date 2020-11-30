// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.announcer.impl;

import net.daporkchop.pepsimod.util.misc.announcer.MessagePrefixes;
import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import net.daporkchop.pepsimod.util.misc.announcer.TaskType;
import net.minecraft.util.math.Vec3d;
import net.daporkchop.pepsimod.util.misc.announcer.QueuedTask;

public class TaskMove extends QueuedTask
{
    public double dist;
    public Vec3d lastPos;
    
    public TaskMove(final TaskType type) {
        super(type);
        this.dist = 0.0;
        this.lastPos = null;
        this.lastPos = TaskMove.mc.player.getPositionVector();
    }
    
    @Override
    public String getMessage() {
        if (!AnnouncerMod.INSTANCE.state.enabled || this.dist == 0.0) {
            return null;
        }
        return MessagePrefixes.getMessage(TaskType.WALK, this.dist);
    }
    
    public void update(final Vec3d nextPos) {
        if (this.lastPos != null && nextPos != null) {
            this.dist += this.lastPos.distanceTo(nextPos);
        }
        this.lastPos = nextPos;
    }
}
