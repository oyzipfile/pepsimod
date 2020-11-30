// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.misc.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Collection;
import java.util.UUID;
import java.util.Map;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class Groups extends PepsiConstants implements AutoCloseable
{
    protected Map<UUID, Group> playerToGroup;
    protected Collection<Group> groups;
    
    public Groups() {
        this.playerToGroup = Collections.emptyMap();
        this.groups = (Collection<Group>)Collections.emptyList();
    }
    
    public void addGroup(final Group group) {
        if (this.groups.isEmpty()) {
            this.groups = new HashSet<Group>();
        }
        this.groups.add(group);
        group.members.forEach(uuid -> this.addPlayerMapping(uuid, group));
    }
    
    public void addPlayerMapping(final UUID uuid, final Group group) {
        if (this.playerToGroup.isEmpty()) {
            this.playerToGroup = new HashMap<UUID, Group>();
        }
        this.playerToGroup.put(uuid, group);
    }
    
    @Override
    public void close() {
        this.groups.forEach(Group::close);
        this.playerToGroup.clear();
        this.groups.clear();
    }
}
