// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.util.config.impl.NameTagsTranslator;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class NameTagsMod extends Module
{
    public static NameTagsMod INSTANCE;
    
    public NameTagsMod() {
        super("NameTags");
        NameTagsMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        NameTagsMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)1.0f, "scale", new String[] { "0.5", "1.0", "1.5", "2.0" }, val -> {
                NameTagsTranslator.INSTANCE.scale = val;
                return Boolean.valueOf(true);
            }, () -> NameTagsTranslator.INSTANCE.scale, "Scale", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.1f, 5.0f, 0.1f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
}
