// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.item.ItemStack;
import net.daporkchop.pepsimod.the.wurst.pkg.name.WPlayerController;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.item.ItemArmor;
import net.daporkchop.pepsimod.module.api.TimeModule;

public class AutoArmorMod extends TimeModule
{
    public static AutoArmorMod INSTANCE;
    
    public AutoArmorMod() {
        super("AutoArmor");
        AutoArmorMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (AutoArmorMod.mc.player.capabilities.isCreativeMode) {
            return;
        }
        this.updateMS();
        if (this.hasTimePassedM(500L)) {
            this.updateLastMS();
            final int[] bestArmorValues = new int[4];
            for (int type = 0; type < 4; ++type) {
                final ItemStack oldArmor = AutoArmorMod.mc.player.inventory.armorItemInSlot(type);
                if (oldArmor.getItem() instanceof ItemArmor) {
                    bestArmorValues[type] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
                }
            }
            final int[] bestArmorSlots = { -1, -1, -1, -1 };
            for (int slot = 0; slot < 36; ++slot) {
                final ItemStack stack = AutoArmorMod.mc.player.inventory.getStackInSlot(slot);
                if (stack.getItem() instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    final int type2 = PepsiUtils.getArmorType(armor);
                    if (armor.damageReduceAmount > bestArmorValues[type2]) {
                        bestArmorValues[type2] = armor.damageReduceAmount;
                        bestArmorSlots[type2] = slot;
                    }
                }
            }
            for (int type3 = 0; type3 < 4; ++type3) {
                final int slot2 = bestArmorSlots[type3];
                if (slot2 != -1) {
                    WPlayerController.windowClick_PICKUP((slot2 < 9) ? (36 + slot2) : slot2);
                    WPlayerController.windowClick_PICKUP(8 - type3);
                    WPlayerController.windowClick_PICKUP((slot2 < 9) ? (36 + slot2) : slot2);
                }
            }
        }
    }
    
    @Override
    public void init() {
        (AutoArmorMod.INSTANCE = this).updateLastMS();
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
}
