// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.util.NonNullList;
import java.util.Iterator;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.daporkchop.pepsimod.util.config.impl.BedBomberTranslator;
import net.daporkchop.pepsimod.the.wurst.pkg.name.BlockUtils;
import net.daporkchop.pepsimod.module.api.TimeModule;

public class BedBomberMod extends TimeModule
{
    public static BedBomberMod INSTANCE;
    private static BlockUtils.BlockValidator validator;
    public int itemMoveTick;
    public int bedSlot;
    private int itemTimer;
    private boolean shouldRestock;
    
    public BedBomberMod() {
        super("BedBomber");
        this.itemMoveTick = 3;
        this.bedSlot = -1;
        this.shouldRestock = false;
        BedBomberMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        this.updateMS();
        if (this.hasTimePassedM(BedBomberTranslator.INSTANCE.delay) && (BedBomberMod.mc.player.dimension == -1 || BedBomberMod.mc.player.dimension == 1)) {
            final Iterable<BlockPos> validBlocks = BlockUtils.getValidBlocksByDistance(BedBomberTranslator.INSTANCE.range, false, BedBomberMod.validator);
            for (final BlockPos pos : validBlocks) {
                if (BlockUtils.rightClickBlockLegit(pos)) {
                    return;
                }
            }
        }
        this.replaceBed(-1);
        if (this.shouldRestock && BedBomberTranslator.INSTANCE.resupply && this.itemMoveTick == 3) {
            if (this.itemTimer > 0) {
                --this.itemTimer;
                return;
            }
            final ItemStack hand = BedBomberMod.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            final NonNullList<ItemStack> inv = (NonNullList<ItemStack>)BedBomberMod.mc.player.inventory.mainInventory;
            if (hand == null || hand.isEmpty()) {
                for (int inventoryIndex = 0; inventoryIndex < inv.size(); ++inventoryIndex) {
                    if (inventoryIndex != BedBomberMod.mc.player.inventory.currentItem) {
                        final ItemStack stack = (ItemStack)inv.get(inventoryIndex);
                        if (!stack.isEmpty() && stack.getItem() instanceof ItemBed) {
                            this.replaceBed(inventoryIndex);
                            break;
                        }
                    }
                }
                this.shouldRestock = false;
            }
        }
    }
    
    @Override
    public void init() {
        BedBomberMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)BedBomberTranslator.INSTANCE.range, "range", OptionCompletions.FLOAT, value -> {
                BedBomberTranslator.INSTANCE.range = Math.max(value, 0.0f);
                return Boolean.valueOf(true);
            }, () -> BedBomberTranslator.INSTANCE.range, "Range", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 10.0f, 0.5f)), new ModuleOption((T)BedBomberTranslator.INSTANCE.delay, "delay", OptionCompletions.FLOAT, value -> {
                BedBomberTranslator.INSTANCE.delay = Math.max(value, 0);
                return Boolean.valueOf(true);
            }, () -> BedBomberTranslator.INSTANCE.delay, "Delay", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 5000, 50)), new ModuleOption((T)BedBomberTranslator.INSTANCE.resupply, "resupply", OptionCompletions.BOOLEAN, value -> {
                BedBomberTranslator.INSTANCE.resupply = value;
                return Boolean.valueOf(true);
            }, () -> BedBomberTranslator.INSTANCE.resupply, "Resupply") };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
    
    public void replaceBed(int inventoryIndex) {
        if (inventoryIndex == -1) {
            inventoryIndex = this.bedSlot;
        }
        else {
            this.itemMoveTick = 0;
            this.bedSlot = inventoryIndex;
        }
        if (inventoryIndex == -1) {
            return;
        }
        switch (this.itemMoveTick) {
            case 0: {
                BedBomberMod.mc.playerController.windowClick(0, (inventoryIndex < 9) ? (inventoryIndex + 36) : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)BedBomberMod.mc.player);
                break;
            }
            case 1: {
                BedBomberMod.mc.playerController.windowClick(0, 36 + BedBomberMod.mc.player.inventory.currentItem, 0, ClickType.PICKUP, (EntityPlayer)BedBomberMod.mc.player);
                break;
            }
            case 2: {
                BedBomberMod.mc.playerController.windowClick(0, (inventoryIndex < 9) ? (inventoryIndex + 36) : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)BedBomberMod.mc.player);
                this.bedSlot = -1;
                break;
            }
        }
        ++this.itemMoveTick;
    }
    
    public void onPlaceBed() {
        if (this.state.enabled && BedBomberTranslator.INSTANCE.resupply) {
            this.shouldRestock = true;
            this.itemTimer = 3;
        }
    }
    
    static {
        final IBlockState state;
        BedBomberMod.validator = (pos -> {
            state = BedBomberMod.mc.world.getBlockState(pos);
            return state.getBlock() instanceof BlockBed;
        });
    }
}
