// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.minecraft.network.Packet;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.item.ItemStack;
import net.daporkchop.pepsimod.the.wurst.pkg.name.WPlayerController;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.daporkchop.pepsimod.module.api.Module;

public class AutoFishMod extends Module
{
    public static AutoFishMod INSTANCE;
    public int timer;
    
    public static boolean isBobberSplash(final SPacketSoundEffect soundEffect) {
        return SoundEvents.ENTITY_BOBBER_SPLASH.equals(soundEffect.getSound());
    }
    
    public AutoFishMod() {
        super("AutoFish");
        AutoFishMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        this.timer = 0;
    }
    
    @Override
    public void onDisable() {
        this.timer = 0;
    }
    
    @Override
    public void tick() {
        int rodInHotbar = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoFishMod.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemFishingRod) {
                rodInHotbar = i;
                break;
            }
        }
        if (rodInHotbar != -1) {
            if (AutoFishMod.mc.player.inventory.currentItem != rodInHotbar) {
                AutoFishMod.mc.player.inventory.currentItem = rodInHotbar;
                return;
            }
            if (this.timer > 0) {
                --this.timer;
                return;
            }
            if (AutoFishMod.mc.player.fishEntity != null) {
                return;
            }
            this.rightClick();
        }
        else {
            int rodInInventory = -1;
            for (int j = 9; j < 36; ++j) {
                final ItemStack stack2 = AutoFishMod.mc.player.inventory.getStackInSlot(j);
                if (!stack2.isEmpty() && stack2.getItem() instanceof ItemFishingRod) {
                    rodInInventory = j;
                    break;
                }
            }
            if (rodInInventory == -1) {
                return;
            }
            int hotbarSlot = -1;
            for (int k = 0; k < 9; ++k) {
                if (AutoFishMod.mc.player.inventory.getStackInSlot(k).isEmpty()) {
                    hotbarSlot = k;
                    break;
                }
            }
            boolean swap = false;
            if (hotbarSlot == -1) {
                hotbarSlot = AutoFishMod.mc.player.inventory.currentItem;
                swap = true;
            }
            WPlayerController.windowClick_PICKUP(rodInInventory);
            WPlayerController.windowClick_PICKUP(36 + hotbarSlot);
            if (swap) {
                WPlayerController.windowClick_PICKUP(rodInInventory);
            }
        }
    }
    
    @Override
    public void init() {
        AutoFishMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    private void rightClick() {
        final ItemStack stack = AutoFishMod.mc.player.inventory.getCurrentItem();
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemFishingRod)) {
            return;
        }
        ReflectionStuff.rightClickMouse();
        this.timer = 15;
    }
    
    @Override
    public void postRecievePacket(final Packet<?> packetIn) {
        if (packetIn instanceof SPacketSoundEffect && isBobberSplash((SPacketSoundEffect)packetIn) && AutoFishMod.mc.player.fishEntity != null) {
            this.rightClick();
        }
    }
}
