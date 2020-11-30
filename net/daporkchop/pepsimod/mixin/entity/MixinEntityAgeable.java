// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.entity;

import net.minecraft.world.World;
import net.minecraft.entity.EntityAgeable;
import org.spongepowered.asm.mixin.Mixin;
import net.daporkchop.pepsimod.optimization.SizeSettable;
import net.minecraft.entity.EntityCreature;

@Mixin({ EntityAgeable.class })
public abstract class MixinEntityAgeable extends EntityCreature implements SizeSettable
{
    public MixinEntityAgeable() {
        super((World)null);
    }
    
    public void forceSetSize(final float width, final float height) {
        super.setSize(width, height);
    }
}
