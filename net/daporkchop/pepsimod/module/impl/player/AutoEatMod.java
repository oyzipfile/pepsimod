// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.minecraft.entity.Entity;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.util.FoodStats;
import net.daporkchop.pepsimod.util.config.impl.AutoEatTranslator;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoEatMod extends Module
{
    public static AutoEatMod INSTANCE;
    public boolean doneEating;
    
    public AutoEatMod() {
        super("AutoEat");
        this.doneEating = true;
        AutoEatMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        if (AutoEatMod.mc.world != null) {
            ReflectionStuff.setPressed(AutoEatMod.mc.gameSettings.keyBindUseItem, false);
        }
        this.doneEating = true;
    }
    
    @Override
    public void tick() {
        if (!this.shouldEat()) {
            ReflectionStuff.setPressed(AutoEatMod.mc.gameSettings.keyBindUseItem, false);
            this.doneEating = true;
            return;
        }
        final FoodStats foodStats = AutoEatMod.mc.player.getFoodStats();
        if (foodStats.getFoodLevel() <= AutoEatTranslator.INSTANCE.threshold && this.shouldEat()) {
            this.doneEating = false;
            this.eatFood();
        }
    }
    
    @Override
    public void init() {
        AutoEatMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)AutoEatTranslator.INSTANCE.threshold, "threshold", OptionCompletions.FLOAT, value -> {
                AutoEatTranslator.INSTANCE.threshold = Math.max(0.0f, value);
                return Boolean.valueOf(true);
            }, () -> AutoEatTranslator.INSTANCE.threshold, "Threshold", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 19.0f, 1.0f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
    
    private void eatFood() {
        for (int slot = 44; slot >= 9; --slot) {
            final ItemStack stack = AutoEatMod.mc.player.inventoryContainer.getSlot(slot).getStack();
            if (stack != null) {
                if (slot >= 36 && slot <= 44) {
                    if (stack.getItem() instanceof ItemFood && !(stack.getItem() instanceof ItemAppleGold)) {
                        AutoEatMod.mc.player.inventory.currentItem = slot - 36;
                        ReflectionStuff.setPressed(AutoEatMod.mc.gameSettings.keyBindUseItem, true);
                        return;
                    }
                }
                else if (stack.getItem() instanceof ItemFood && !(stack.getItem() instanceof ItemAppleGold)) {
                    final int itemSlot = slot;
                    final int currentSlot = AutoEatMod.mc.player.inventory.currentItem + 36;
                    AutoEatMod.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoEatMod.mc.player);
                    AutoEatMod.mc.playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoEatMod.mc.player);
                    AutoEatMod.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoEatMod.mc.player);
                    return;
                }
            }
        }
    }
    
    private boolean shouldEat() {
        if (!AutoEatMod.mc.player.canEat(false)) {
            return false;
        }
        if (AutoEatMod.mc.currentScreen != null) {
            return false;
        }
        if (AutoEatMod.mc.currentScreen == null && AutoEatMod.mc.objectMouseOver != null) {
            final Entity entity = AutoEatMod.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
                return false;
            }
            if (AutoEatMod.mc.objectMouseOver.getBlockPos() != null && AutoEatMod.mc.world.getBlockState(AutoEatMod.mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer) {
                return false;
            }
        }
        return true;
    }
}
