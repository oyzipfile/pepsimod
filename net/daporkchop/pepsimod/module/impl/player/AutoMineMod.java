// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoMineMod extends Module
{
    public static AutoMineMod INSTANCE;
    
    public AutoMineMod() {
        super("AutoMine");
        AutoMineMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        if (AutoMineMod.pepsimod.hasInitializedModules) {
            ReflectionStuff.setPressed(AutoMineMod.mc.gameSettings.keyBindAttack, false);
        }
    }
    
    @Override
    public void tick() {
        if (AutoMineMod.mc.objectMouseOver == null || AutoMineMod.mc.objectMouseOver.getBlockPos() == null) {
            return;
        }
        if (AutoMineMod.mc.gameSettings.keyBindAttack.isPressed() && !AutoMineMod.mc.playerController.getIsHittingBlock()) {
            ReflectionStuff.setPressed(AutoMineMod.mc.gameSettings.keyBindAttack, false);
            return;
        }
        final IBlockState state = AutoMineMod.mc.world.getBlockState(AutoMineMod.mc.objectMouseOver.getBlockPos());
        ReflectionStuff.setPressed(AutoMineMod.mc.gameSettings.keyBindAttack, state.getBlock().getMaterial(state) != Material.AIR);
    }
    
    @Override
    public void init() {
        AutoMineMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
}
