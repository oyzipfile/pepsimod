// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.entity.passive;

import org.spongepowered.asm.mixin.Overwrite;
import net.daporkchop.pepsimod.module.impl.movement.EntitySpeedMod;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.World;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.passive.EntityAnimal;

@Mixin({ AbstractHorse.class })
public abstract class MixinAbstractHorse extends EntityAnimal
{
    public MixinAbstractHorse() {
        super((World)null);
    }
    
    @Shadow
    protected abstract boolean getHorseWatchableBoolean(final int p0);
    
    @Shadow
    public abstract boolean isTame();
    
    @Overwrite
    public boolean isHorseSaddled() {
        if (this.world.isRemote) {
            return this.getHorseWatchableBoolean(4) || (this.isTame() && EntitySpeedMod.INSTANCE.state.enabled);
        }
        return this.getHorseWatchableBoolean(4);
    }
}
