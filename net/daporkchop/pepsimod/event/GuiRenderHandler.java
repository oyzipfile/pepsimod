// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.event;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.client.gui.GuiIngame;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.daporkchop.pepsimod.util.BetterScaledResolution;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class GuiRenderHandler extends PepsiConstants
{
    public static GuiRenderHandler INSTANCE;
    
    public GuiRenderHandler() {
        GuiRenderHandler.INSTANCE = this;
        new BetterScaledResolution();
    }
    
    @SubscribeEvent
    public void onRenderGui(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        final GuiIngame gui = GuiRenderHandler.mc.ingameGUI;
        BetterScaledResolution.INSTANCE.update();
        final int width = BetterScaledResolution.INSTANCE.scaledWidth;
        final int height = BetterScaledResolution.INSTANCE.scaledHeight;
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.onRenderGUI(event.getPartialTicks(), width, height, gui);
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.onRender(event.getPartialTicks());
        }
    }
}
