// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.key;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyRegistry
{
    @SubscribeEvent
    public void onKeyPress(final InputEvent.KeyInputEvent event) {
        for (final Module module : ModuleManager.AVALIBLE_MODULES) {
            if (module.keybind != null && module.keybind.isPressed()) {
                ModuleManager.toggleModule(module);
            }
        }
    }
}
