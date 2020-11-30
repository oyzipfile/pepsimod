// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.gui.GuiChat;
import net.daporkchop.pepsimod.clickgui.ClickGUI;
import net.minecraft.client.gui.GuiIngameMenu;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.daporkchop.pepsimod.util.PepsiConstants;
import net.daporkchop.pepsimod.module.impl.movement.InventoryMoveMod;
import net.daporkchop.pepsimod.optimization.OverrideCounter;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.MovementInput;

@Mixin({ MovementInputFromOptions.class })
public abstract class MixinMovementInputFromOptions extends MovementInput
{
    @Redirect(method = { "Lnet/minecraft/util/MovementInputFromOptions;updatePlayerMoveState()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean redirectIsKeyDown(final KeyBinding binding) {
        if (((OverrideCounter)binding).isOverriden()) {
            return true;
        }
        if (InventoryMoveMod.INSTANCE.state.enabled && PepsiConstants.mc.currentScreen != null) {
            if (PepsiConstants.mc.currentScreen instanceof InventoryEffectRenderer) {
                return Keyboard.isKeyDown(binding.getKeyCode()) || ReflectionStuff.getPressed(binding);
            }
            if (PepsiConstants.mc.world.isRemote && PepsiConstants.mc.currentScreen instanceof GuiIngameMenu) {
                return Keyboard.isKeyDown(binding.getKeyCode()) || ReflectionStuff.getPressed(binding);
            }
            if (PepsiConstants.mc.currentScreen instanceof ClickGUI) {
                return Keyboard.isKeyDown(binding.getKeyCode()) || ReflectionStuff.getPressed(binding);
            }
            if (PepsiConstants.mc.currentScreen instanceof GuiChat) {
                return ReflectionStuff.getPressed(binding);
            }
        }
        return binding.isKeyDown();
    }
}
