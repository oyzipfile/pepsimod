// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.misc.data;

import java.util.function.Consumer;
import java.util.Collections;
import java.util.Objects;
import net.daporkchop.pepsimod.util.render.Texture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.UUID;
import java.util.Set;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class Group extends PepsiConstants implements AutoCloseable
{
    public final String id;
    public final String name;
    public final Set<UUID> members;
    public final int color;
    public final AtomicReference<Texture> cape;
    public final AtomicReference<Texture> icon;
    
    public Group(final String id, final String name, final Set<UUID> members, final int color, final Texture cape, final Texture icon) {
        this.cape = new AtomicReference<Texture>(null);
        this.icon = new AtomicReference<Texture>(null);
        this.id = Objects.requireNonNull(id, "id");
        this.name = ((name == null) ? id : name);
        this.members = Collections.unmodifiableSet((Set<? extends UUID>)Objects.requireNonNull((Set<? extends T>)members, "members"));
        this.color = (color & 0xFFFFFF);
        this.cape.set(cape);
        this.icon.set(icon);
    }
    
    public void doWithCapeIfPresent(final Consumer<Texture> callback) {
        final Texture cape = this.cape.get();
        if (cape != null) {
            callback.accept(cape);
        }
    }
    
    public void doWithIconIfPresent(final Consumer<Texture> callback) {
        final Texture icon = this.icon.get();
        if (icon != null) {
            callback.accept(icon);
        }
    }
    
    @Override
    public void close() {
        this.cape.updateAndGet(loc -> {
            loc.close();
            return null;
        });
        this.icon.updateAndGet(loc -> {
            loc.close();
            return null;
        });
    }
}
