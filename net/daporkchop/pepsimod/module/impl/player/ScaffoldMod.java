// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.daporkchop.pepsimod.the.wurst.pkg.name.BlockUtils;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.daporkchop.pepsimod.module.api.Module;

public class ScaffoldMod extends Module
{
    public static ScaffoldMod INSTANCE;
    
    public ScaffoldMod() {
        super("Scaffold");
        ScaffoldMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        final BlockPos belowPlayer = new BlockPos((Entity)ScaffoldMod.mc.player).down();
        final IBlockState state = ScaffoldMod.mc.world.getBlockState(belowPlayer);
        if (!state.getBlock().isReplaceable((IBlockAccess)ScaffoldMod.mc.world, belowPlayer)) {
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ScaffoldMod.mc.player.inventory.getStackInSlot(i);
            if (stack != null && !stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = Block.getBlockFromItem(stack.getItem());
                    if (block.getDefaultState().isFullBlock() || block instanceof BlockPistonBase) {
                        newSlot = i;
                        break;
                    }
                }
            }
        }
        if (newSlot == -1) {
            return;
        }
        final int oldSlot = ScaffoldMod.mc.player.inventory.currentItem;
        ScaffoldMod.mc.player.inventory.currentItem = newSlot;
        BlockUtils.placeBlockScaffold(belowPlayer);
        ScaffoldMod.mc.player.inventory.currentItem = oldSlot;
    }
    
    @Override
    public void init() {
        ScaffoldMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
}
