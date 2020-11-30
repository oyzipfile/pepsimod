// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.command.CommandRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.TabCompleter;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ TabCompleter.class })
public abstract class MixinTabCompleter
{
    @Shadow
    @Final
    protected GuiTextField textField;
    
    @Inject(method = { "complete" }, at = { @At("HEAD") }, cancellable = true)
    public void preComplete(final CallbackInfo callbackInfo) {
        if (this.textField.getText().startsWith(".")) {
            final String completed = CommandRegistry.getSuggestionFor(this.textField.getText());
            if (completed == null || completed.isEmpty() || completed.length() <= this.textField.getText().length()) {
                return;
            }
            this.textField.setText(completed);
            callbackInfo.cancel();
        }
    }
}
