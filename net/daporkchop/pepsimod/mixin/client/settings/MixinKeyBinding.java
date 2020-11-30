// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.settings;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import net.daporkchop.pepsimod.optimization.OverrideCounter;

@Mixin({ KeyBinding.class })
public abstract class MixinKeyBinding implements OverrideCounter
{
    public int overrideCounter;
    
    public MixinKeyBinding() {
        this.overrideCounter = 0;
    }
    
    @Override
    public void incrementOverride() {
        ++this.overrideCounter;
    }
    
    @Override
    public void decrementOverride() {
        final int overrideCounter = this.overrideCounter - 1;
        this.overrideCounter = overrideCounter;
        if (overrideCounter < 0) {
            this.overrideCounter = 0;
        }
    }
    
    @Override
    public int getOverride() {
        return this.overrideCounter;
    }
}
