// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.util.NonNullList;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoTotemMod extends Module
{
    public static AutoTotemMod INSTANCE;
    private int timer;
    
    public AutoTotemMod() {
        super("AutoTotem");
        AutoTotemMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        this.timer = 0;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        final EntityPlayerSP player = AutoTotemMod.mc.player;
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        final ItemStack offhand = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        final NonNullList<ItemStack> inv = (NonNullList<ItemStack>)player.inventory.mainInventory;
        if (offhand == null || offhand.getItem() != Items.TOTEM_OF_UNDYING) {
            for (int inventoryIndex = 0; inventoryIndex < inv.size(); ++inventoryIndex) {
                if (inv.get(inventoryIndex) != ItemStack.EMPTY && ((ItemStack)inv.get(inventoryIndex)).getItem() == Items.TOTEM_OF_UNDYING) {
                    this.replaceTotem(inventoryIndex);
                    break;
                }
            }
            this.timer = 3;
        }
    }
    
    @Override
    public void init() {
        AutoTotemMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
    
    public void replaceTotem(final int inventoryIndex) {
        if (AutoTotemMod.mc.player.openContainer instanceof ContainerPlayer) {
            AutoTotemMod.mc.playerController.windowClick(0, (inventoryIndex < 9) ? (inventoryIndex + 36) : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemMod.mc.player);
            AutoTotemMod.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemMod.mc.player);
            AutoTotemMod.mc.playerController.windowClick(0, (inventoryIndex < 9) ? (inventoryIndex + 36) : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemMod.mc.player);
        }
    }
}
