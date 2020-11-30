// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.misc.waypoints;

import net.minecraft.util.math.Vec3d;

public class Waypoint
{
    public final String name;
    public final int x;
    public final int y;
    public final int z;
    public final int dim;
    public boolean shown;
    
    public Waypoint(final String name, final double x, final double y, final double z, final int dim) {
        this(name, x, y, z, true, dim);
    }
    
    public Waypoint(final String name, final double x, final double y, final double z, final boolean shown, final int dim) {
        this(name, (int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z), shown, dim);
    }
    
    public Waypoint(final String name, final int x, final int y, final int z, final int dim) {
        this(name, x, y, z, true, dim);
    }
    
    public Waypoint(final String name, final int x, final int y, final int z, final boolean shown, final int dim) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.shown = shown;
        this.dim = dim;
    }
    
    public Vec3d getPosition() {
        return new Vec3d((double)this.x, (double)this.y, (double)this.z);
    }
}
