// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.api.option;

public class ExtensionSlider extends OptionExtended
{
    public final ExtensionType dataType;
    public final Object min;
    public final Object max;
    public final Object step;
    
    public ExtensionSlider(final ExtensionType dataType, final Object min, final Object max, final Object step) {
        this.dataType = dataType;
        this.min = min;
        this.max = max;
        this.step = step;
    }
    
    @Override
    public ExtensionType getType() {
        return ExtensionType.TYPE_SLIDER;
    }
}
